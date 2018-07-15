package com.padoli.windfall.vojtech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.padoli.windfall.vojtech.calculator.ExpressionManager;
import com.padoli.windfall.vojtech.exceptions.SpreadSheetRuntimeException;
import com.padoli.windfall.vojtech.models.Cell;
import com.padoli.windfall.vojtech.models.NumericToken;
import com.padoli.windfall.vojtech.models.Token;
import com.padoli.windfall.vojtech.models.VariableToken;
import com.padoli.windfall.vojtech.utils.ExpressionHelper;
import com.padoli.windfall.vojtech.utils.TokenBuilder;

/**
 * 
 * @author vojtech
 *
 */

public class Spreadsheet {

	private List<Cell[]> cells;

	public int getWidth() {

		// null check not required since this is checked within the constructor itself
		// and when adding rows
		return this.cells.get(0).length;
	}

	public int getHeight() {

		// null check not required since this is checked within the constructor itself
		return this.cells.size();
	}

	public Cell getCell(String name) {

		if (this.cells == null)
			throw new SpreadSheetRuntimeException("Spreadsheet and its cells have not been initialized yet!");

		// we know that the max number columns is 26 (A-Z) per requirement
		// so I can consider only the first character of the name

		name = name.toUpperCase().trim();
		char col = name.charAt(0);

		// make it 0-based
		int row = Integer.valueOf(name.substring(1)) - 1;

		if (col < 'A' || col > 'Z' || row < 0 || row >= this.getHeight() || col - 'A' > this.getWidth()) {

			throw new SpreadSheetRuntimeException("Invalid cell address! > " + name);
		}

		return this.cells.get(row)[col - 'A'];
	}

	private void resolveCell(String cellname, Map<String, Float> cellMap) {

		Set<String> openedCells = new HashSet<>();
		resolveCell(cellname, cellMap, openedCells);
	}

	private void resolveCell(String cellname, Map<String, Float> cellMap, Set<String> openedCells) {

		if (cellMap.containsKey(cellname))
			return;

		// cycle detection
		if (openedCells.contains(cellname))
			throw new SpreadSheetRuntimeException(
					"Cycle detected cannot evaluate the spreadsheet! Problem found at > " + cellname);

		openedCells.add(cellname);

		Cell cell = getCell(cellname);
		List<Token> unresolvedVarTokens = ExpressionHelper.tokenize(cell.getPayload());
		List<Token> resolvedVarTokens = new ArrayList<>();

		for (Token ut : unresolvedVarTokens) {

			if (ut instanceof VariableToken) {

				String varCellName = ut.getStringValue();
				if (!cellMap.containsKey(varCellName)) {

					resolveCell(varCellName, cellMap, openedCells);
				}

				NumericToken nToken = TokenBuilder.numeric(cellMap.get(varCellName));
				resolvedVarTokens.add(nToken);
			} else {

				resolvedVarTokens.add(ut);
			}
		}

		Float value = ExpressionManager.getInstance().evaluate(resolvedVarTokens);
		cellMap.put(cellname, value);
	}

	public float[][] evaluateSpreadsheet() {

		float[][] res = new float[this.getHeight()][this.getWidth()];
		Map<String, Float> cellMap = new HashMap<>();

		for (int row = 0; row < this.getHeight(); row++) {
			for (int col = 0; col < this.getWidth(); col++) {

				String cellName = String.valueOf((char) ('A' + col)) + String.valueOf(1 + row);
				resolveCell(cellName, cellMap);
				res[row][col] = cellMap.get(cellName);
			}
		}

		return res;
	}

	public Spreadsheet(String filename) {

		SpreadsheetFileReader sfr = new SpreadsheetFileReader();
		this.cells = sfr.buildFromFile(filename);

		if (this.cells == null)
			throw new SpreadSheetRuntimeException("The spreadsheet has not been initialized!");
	}

	public static void main(String[] args) {

		if (args.length < 1) {

			System.out.println("Specify filename as a first parameter");
			return;
		}

		Spreadsheet spreadsheet = new Spreadsheet(args[0]);

		StringBuffer outBuffer = new StringBuffer();

		float[][] evaluatedMatrix = spreadsheet.evaluateSpreadsheet();
		for (int r = 0; r < spreadsheet.getHeight(); r++) {
			for (int c = 0; c < spreadsheet.getWidth(); c++) {

				outBuffer.append(String.format("%.2f", evaluatedMatrix[r][c]));

				if (c + 1 < spreadsheet.getWidth())
					outBuffer.append(',');
			}

			outBuffer.append("\n");
		}

		System.out.print(outBuffer.toString());
	}
}

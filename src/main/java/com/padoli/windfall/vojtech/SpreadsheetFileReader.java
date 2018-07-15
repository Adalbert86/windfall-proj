package com.padoli.windfall.vojtech;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.padoli.windfall.vojtech.exceptions.SpreadSheetRuntimeException;
import com.padoli.windfall.vojtech.models.Cell;

/**
 * 
 * @author vojtech
 * 
 *         Helper class to help break down the boilerplate
 *
 */
public class SpreadsheetFileReader {

	private List<Cell[]> cells = null;

	private void addRowCells(Cell[] row) {

		if (row == null) {
			throw new SpreadSheetRuntimeException("Attempt to insert null row!");
		} else if (row.length < 1) {
			throw new SpreadSheetRuntimeException("Attempt to insert empty row!");
		}

		if (cells == null) {
			cells = new ArrayList<Cell[]>();
		}

		if (cells.size() > 0 && cells.get(0).length != row.length) {

			throw new SpreadSheetRuntimeException("Inconsistent row length!");
		}

		cells.add(row);
	}

	private Cell[] parseInputLineToCells(String line, int row) {

		String words[] = line.split(",");

		if (words.length < 1 || words.length > 26 )
			throw new SpreadSheetRuntimeException("Unsupported number of columns, maximum is 26");

		Cell[] cells = new Cell[words.length];

		for (char ch = 'A'; ch < 'A' + words.length; ch++) {

			String cellName = ch + String.valueOf(row);
			int index = ch - 'A';
			cells[index] = new Cell(cellName, words[index]);
		}

		return cells;
	}

	private void readFile(String filename) {

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));

			String line;
			int rowIndex = 0;

			while (true) {

				line = reader.readLine();

				if (line == null)
					break;

				Cell[] row = parseInputLineToCells(line, ++rowIndex);
				addRowCells(row);
			}

			reader.close();

		} catch (IOException e) {

			e.printStackTrace();
			final String message = "Error while reading or processing the input file";
			throw new SpreadSheetRuntimeException(message, e);
		}
	}

	public List<Cell[]> buildFromFile(String filename) {

		this.cells = null;
		readFile(filename);
		return this.cells;
	}

}

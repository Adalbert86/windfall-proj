# Windfall Programming Assignment

A spreadsheet consists of a two-dimensional array of cells. Columns are identified using letters and rows by
numbers (C2 references a cell in column 3, row 2). Each cell contains either an integer or an expression.
Expressions contain integers, cell references, and operators ('+', '-', '*', '/') and are evaluated with the
usual rules of evaluation.

Write a program in Java to read in a spreadsheet, evaluate the value of each cell, and output the values to a
file.

**Input Format:**
- A csv file with m rows and n columns
- The input file will have no headers
- Cells will not be surrounded in double quotes

**Output Format:**
- A csv file (to stdout is fine) with the same dimensions as the input file
- Each cell should be output as a floating point value. Round output values to two decimal places.

**Example:**

_input.csv_
```
B2+2,A1+A2
B2-3,7+5
```

_output.csv_
```
14.00,23.00
9.00,12.00
```


**Requirements**
- The spreadsheet should be able to evaluate expressions containing cell references, integers, floating point
  numbers, and the addition and subtraction operators. It is not required to support multiplication or
  division
- Support for up to 26 columns (A-Z)
- The spreadsheet should detect circular references and exit appropriately
- Solutions should define a main method in a class named `Spreadsheet.java`
- Solutions should only use the standard java libraries

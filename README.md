# Solution by Vojtech

I have implemented this solution using basic standard tools available in Java. You need Maven to compile and run the project as follows:
`mvn clean install`
After that a JAR will file will be created and you can run this program by executing :
`java -jar windfall-proj-1.0-SNAPSHOT.jar input_file.csv`

The program will read the input file, parse it and populate `List<Cell[]>` object which will contain the entire spreadsheet. Tokenizer will read each and every cell and tokenize its content. If a cell contains `VariableToken` it means a reference to an another cell whose value has to be determined before we are able to get its value. Method `resolveCell(...)` is called in recursion along with helpers **cellMap** and **openedCells** set which help to keep track of already visited cells so that we can detect a cycle. 

Take a look at `ExpressionManager.java`. As I was not allowed to use non-standard java libraries I used [Shunting-yard_algorithm](https://en.wikipedia.org/wiki/Shunting-yard_algorithm) to evaluate basic arithmetic expressions which is described at Wikipedia and other sources. My solution can read infix string expression and get its value. Note that variables and cell dependecies have to be resolved beforehand.


These are a few tests I created. The first input1.csv is the same one provided in the assignment.


## Ordinary input

_input4.csv_
```
10,20,30,A1
A1+1,B1+1,1+C1,D1
90,91,92,93
```

_output4.csv_
```
10.00,20.00,30.00,10.00
11.00,21.00,31.00,10.00
90.00,91.00,92.00,93.00
```

### next one

_input9.csv_
```
1,B5*G2,C7,3,4,5,A7
A1,B1,A2+B2,1,1,10,(A1+2)
1-7,A1+B2,3,D1-E1,G2+A1,0,0
11.5,1,1,1,A1,1,1
17- A2,2,2,2,7*2,2.0,2.1
0.5,0.5,4,G7 - 2*( G2+10*C3 ) + A6,D6-B7,D2,E6*C2
0,0,A1+B1,11,11,11,10
```

_output9.csv_
```
1.00,6.00,7.00,3.00,4.00,5.00,0.00
1.00,6.00,7.00,1.00,1.00,10.00,3.00
-6.00,7.00,3.00,-1.00,4.00,0.00,0.00
11.50,1.00,1.00,1.00,1.00,1.00,1.00
16.00,2.00,2.00,2.00,14.00,2.00,2.10
0.50,0.50,4.00,-55.50,-55.50,1.00,-388.50
0.00,0.00,7.00,11.00,11.00,11.00,10.00
```

## Cycle detection

_input2.csv_
```
B2+2,A1+A2
B2-3,7+5-A1
```

_input3.csv_
```
1+B1,A1
```

_input7.csv_
```
A1
```

_output2.csv_ and _output3.csv_ and _output7.csv_
will throw exception as it detects a cycle

## Support for float numbers

_input10.csv_
```
0.5,2*A1
```

_output10.csv_
```
0.50,1.00
```

## Referencing bad cell address

_input5.csv_
```
10,20,30,A1
A1+1,B1+1,1+C1,D1
A10+A1+1,91,92,93
```

Exception will be raised as **A10** does not exist.

_input8.csv_
```
B2
```

Cell B2 does not exist, exception is raised as well.


## Only one cell

_input6.csv_
```
10
```

_output6.csv_
```
10.00
```

## Input with more than 26 columns

_input11.csv_ has more than 26 columns and `SpreadSheetRuntimeException`

## Division by zero

_input12.csv_
```
0,1,2
3,4,5
A1/2,B2-B1,10/A1
```

Exception `DivisionByZeroException` is raised because cell **C3** is dividing by zero.


## Final thought

I was trying to break down the code into a few logical pieces and wrote just a few tests as I like to quickly troubleshoot problems using TDD. If I could spend more time I would wrote more unit tests and support functions, and unary operators as well.

More tests could be created. Very important would be to see a test testing very long input files and its performance when the `evaluateSpreadsheet()` method is called.




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

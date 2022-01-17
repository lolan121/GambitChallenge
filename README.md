## Gambit Challenge

The solution is made in Java and simply parses the data from the text feed containing the register values. The results are printed into the console, showing each variable and its value. Admittedly, I have not worked much with problems such as this before, and thus the solution and code might not be optimal. A executable jar file is included in the repository and can be run using the command line: `java -exe Parser.jar`.

#Observations and comments
Certain discrepancies are found in the registers table documentation, e.g. register 62 is listed twice, each time with a different variable name. Variable name "Current input at AI3" is listed three times. Assumptions regarding such instances are mentioned along with the value correlating to the variable in question when the result is printed.
Strange register values are also found in the provided text feed. For example, register 93 holds an integer in the range of 0-2047 according to the documentation, however, in the text feed the value is 3475. Various other oddities are also encountered among the register values of the text feed.

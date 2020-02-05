# Smart-Calculator

English Version :
In this project we will create a Smart Calculator step-by-step.
We will use the Strategy Design Pattern throughout this project.
This file will be filled in as the project progresses.

 * Description - Step 1:
 Write a program that reads two integer numbers from the same line and prints their sum in the standard output. Numbers can be positive, negative, or zero.(Look at the step1 package (Main.java) for more details...)

 * Description - Step 2:
 Write a program that reads two numbers in a loop and prints the sum in the standard output.
 If a user enters only a single number, the program should print the same number.
 If a user enters an empty line, the program should ignore it.(Look at the step2 package (Main.java) for more details...)

 * Description - Step 3:
 At this stage, the program should read an unlimited sequence of numbers from the standard input and calculate their sum. Also, add a /help command to print some information about the program.(Look at the step3 package (Main.java) for more details...)

 * Description - Step 4:
 At this stage, the calculator should support the addition + and subtraction - operators.
 The program must calculate expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on.
 It must support both unary and binary minus operators.
 If the user has entered several operators following each other, the program still should work (like Java or Python REPL).
 Consider that the even number of minuses gives a plus, and the odd number of minuses gives a minus! Look at it this way: 2 -- 2 equals 2 - (-2) equals 2 + 2.(Look at the step4 package (Main.java) for more details...)

 * Description - Step 5:
 The program has been modified to handle different cases when the given expression has an invalid format.
 The program should print "Invalid expression" in such cases.
 The program must never throw the NumberFormatException or any other exception.
 If a user enters an invalid command, the program must print "Unknown command".
 All messages must be printed without quotes. (Look at the step5 package (Main.java) for more details...)

 * Description - Step #6: Variables.
 At this stage, your program should support variables.
 We suppose that the name of a variable (identifier) can contain only Latin letters.
 The case is also important; for example, n is not the same as N.
 The value can be an integer number or a value of another variable.(Look at the step6 package (Main.java) for more details...)

 * Description - Step #7: Use of parentheses for the predominance of calculations.
 The management of parentheses is done in postfix with stack.
At this stage you now have the possibility of using parentheses for the predominance of calculations.
Multiplications and divisions take precedence over addition and subtraction, while parentheses have a higher prevalence than other signs.(Look at the step7 package (Main.java) for more details...)

 * Description - Step #8: Big Integer.
 At this stage, your program must support arithmetic operations (+, -, *, /) with very large numbers as well as parentheses to change the priority within an expression. (Look at the step8 package (Main.java) for more details...)
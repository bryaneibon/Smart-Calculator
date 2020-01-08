package smartCalculator.step6;

import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this Project, we'll use the Strategy Design Pattern because it will be
 * easier to add new features for the next steps, without having to change the logic.
 *
 * Description - Step #6: Variables.
 * At this stage, your program should support variables.
 * We suppose that the name of a variable (identifier) can contain only Latin letters.
 * The case is also important; for example, n is not the same as N.
 * The value can be an integer number or a value of another variable.
 *
 * Use Map to support variables.
 * The assignment statement may look like the following:
 * n = 3
 * m=4
 * a  =   5
 * b = a
 *
 * A variable can have a name consisting of more than one letter.
 * count = 10
 *
 * To print the value of a variable you should just type its name.
 * N = 5
 * N
 * 5
 *
 * It should be possible to set a new value to an existing variable.
 * a = 1
 * a = 2
 * a = 3
 * a
 * 3
 *
 * If an identifier or value of a variable is invalid, the program must print a message like the one below.
 * a1 = 8
 * Invalid identifier
 * n = a2a
 * Invalid assignment
 * a = 7 = 8
 * Invalid assignment
 *
 * If a variable is not declared yet, the program should print "Unknown variable".
 *
 * a = 8
 * b = c
 * Unknown variable
 * e
 * Unknown variable
 *
 * Handle as many incorrect inputs as possible. The program must never throw the NumberFormatException or any other exception.
 * It is important to note, all variables must store their values between calculations of different expressions.
 * 
 * Input/Output example
 * a = 3
 * b = 4
 * c = 5
 * a + b - c
 * 2
 * b - c + 4 - a
 * 0
 * a = 800
 * a + b + c
 * 809
 * BIG = 9000
 * BIG
 * 9000
 * big
 * Unknown variable
 * /exit
 * Bye!
 * The program should not stop until the user enters the /exit command.
 */

interface SmartCalculator {
    void calculationMethod(String[] array);
}

class CalculationMethod implements SmartCalculator{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void calculationMethod(String [] array) {
        int sum = 0;
        boolean isAurevoir = false;
        boolean haveVariables = false; // The calculation method will depends on this boolean.
        SortedMap<String, Integer> variablesMap = new TreeMap<>(); // Will be used only if the user input contains variables.

        do {
            String userInput = scanner.nextLine();
            String invalidExpression = GeneralInfos.expressionValidator(userInput);

            switch (invalidExpression){
                case "/exit":
                    GeneralInfos.bye();
                    isAurevoir = true;
                    return; // The program will stop at this point.
                case "/help":
                    GeneralInfos.help();
                    continue;
                case "Invalid Expression":
                    System.out.println("Invalid Expression");
                    continue;
                case "Unknown command":
                    System.out.println("Unknown command");
                    continue;
                default:
                    break;
            }

            // This section will check for a specific pattern calling the checkVariables method.
            String variablesValidator = GeneralInfos.checkVariables(userInput);
            Pattern checkCommand = Pattern.compile("([a-z]* = [0-9]+|[a-z]* = [a-z]*)", Pattern.CASE_INSENSITIVE);
            Matcher matchCommand = checkCommand.matcher(variablesValidator);

            // Invalid identifier
            Pattern checkIdentifier = Pattern.compile("([a-z]+[0-9]+ = \\w+|[0-9]+[a-z]+ = \\w+)", Pattern.CASE_INSENSITIVE);
            Matcher matchIdentifier = checkIdentifier.matcher(variablesValidator);

            // Invalid assignment
            Pattern checkAssignment = Pattern.compile("([a-z]* = [a-z]+[0-9]+|[a-z]* = [0-9]+[a-z]+)|[a-z]* = [a-z]+[0-9]+[a-z]+|[a-z]* = [0-9]+[a-z]+[0-9]+|[a-z]* = [0-9]+ = [0-9]+", Pattern.CASE_INSENSITIVE);
            Matcher matchAssignment = checkAssignment.matcher(variablesValidator);

            // Single input.
            Pattern checksingleVariable = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
            Matcher matchsingleVariable = checksingleVariable.matcher(variablesValidator);

            if(matchCommand.matches()){
                haveVariables = true;
                String [] arrayOfVariables = variablesValidator.split(" ");

                if (variablesMap.containsKey(arrayOfVariables[arrayOfVariables.length-1])) {
                    variablesMap.put(arrayOfVariables[0], variablesMap.get(arrayOfVariables[arrayOfVariables.length - 1]));
                } else {
                    try {
                        variablesMap.put(arrayOfVariables[0], Integer.parseInt(arrayOfVariables[arrayOfVariables.length-1]));
                    } catch (NumberFormatException ignored) {
                        System.out.println("Unknown variable");
                    }
                }
            } else if (matchIdentifier.matches()){
                System.out.println("Invalid identifier");
            } else if (matchAssignment.matches()){
                System.out.println("Invalid assignment");
            } else if(matchsingleVariable.matches()){
                if(variablesMap.containsKey(userInput)) {
                    System.out.println(variablesMap.get(userInput));
                } else{
                    System.out.println("Unknown variable");
                }
            }
            else if (haveVariables){ //Is used only when the Treemap contains variables...
                try {
                    String [] finalExpression = variablesValidator.split(" ");
                    //Sum calculation...
                    for (Map.Entry<String, Integer> entry : variablesMap.entrySet()) {
                        for (int i = 0; i < finalExpression.length; i++) {
                            if (finalExpression[i].equals(entry.getKey())){
                                finalExpression[i] = String.valueOf(entry.getValue());
                            }
                        }
                    }

                    sum = Integer.parseInt(finalExpression[0]);
                    for (int i = 1; i < finalExpression.length; i+=2){
                        if(finalExpression[i].equals("+")){
                            sum += Integer.parseInt(finalExpression[i+1]);
                        }else if (finalExpression[i].equals("-")){
                            sum -= Integer.parseInt(finalExpression[i+1]);
                        }
                    }
                    System.out.println(sum);
                } catch (NumberFormatException ignored) {
                } finally {
                    sum = 0; // after printing the sum, the value is re-initialize at 0.
                    //haveVariables = false; // Turned to false after calculation.
                }
            } else {
                String userInputcleaner = GeneralInfos.regexCleaner(userInput);
                array = userInputcleaner.split(" ");

                try {
                    //Sum calculation...
                    sum = Integer.parseInt(array[0]);
                    for (int i = 1; i < array.length; i+=2){
                        if(array[i].equals("+")){
                            sum += Integer.parseInt(array[i+1]);
                        }else if (array[i].equals("-")){
                            sum -= Integer.parseInt(array[i+1]);
                        }
                    }
                    System.out.println(sum);
                } catch (NumberFormatException ignored) {
                } finally {
                    sum = 0; // after printing the sum, the value is re-initialize at 0.
                }
            }
        } while (!isAurevoir);
    }
}

class SmartCalculatorContext {
    private SmartCalculator smartCalculator;
    void setCalculationMethod(SmartCalculator smartCalculator){
        this.smartCalculator = smartCalculator;
    }

    void calculationMethod(String [] array) {
        this.smartCalculator.calculationMethod(array);
    }
}

class GeneralInfos {
    static void bye(){
        System.out.println("Bye!");
    }

    static void help(){
        System.out.println("The program support the:" +
                "\nAddition + and Subtraction - operators:" +
                "\nConsider that the even number of minuses gives a plus," +
                "\nand the odd number of minuses gives a minus! Look at it this way:" +
                "\n2 -- 2 equals 2 - (-2) equals 2 + 2." +
                "\nAt this stage we also support variables.");
    }

    static String regexCleaner(String userInput) {
        Pattern extraSpace = Pattern.compile("\\s+");
        Matcher matchSpace = extraSpace.matcher(userInput);
        //Will remove all spaces between the expression.
        String spaceCleaner = matchSpace.replaceAll(" ");

        Pattern patternPLUS = Pattern.compile("\\+{2,}");
        Matcher matcherPLUS = patternPLUS.matcher(spaceCleaner);
        //Will merge all the Extra +
        String mergePLUS = matcherPLUS.replaceAll("+");

        Pattern patternMINUS = Pattern.compile("-{3}");
        Matcher matcherMINUS = patternMINUS.matcher(mergePLUS);
        //Will merge all the Extra -
        String mergeMINUS = matcherMINUS.replaceAll("-");

        Pattern patternDOUBLEMINUS = Pattern.compile("-{2}");
        Matcher matcherDOUBLEMINUS = patternDOUBLEMINUS.matcher(mergeMINUS);
        //Will replace - by + if we've 2 minus next to each one.
        String mergeDOUBLEMINUS = matcherDOUBLEMINUS.replaceAll("+");

        Pattern patternStartByMINUS = Pattern.compile("\\b-.*?");
        Matcher matcherStartByMINUS = patternStartByMINUS.matcher(mergeDOUBLEMINUS);
        //Will replace - by 0 -.
        String refactoringZERO = matcherStartByMINUS.replaceFirst("0 - ");

        Pattern finalCheck = Pattern.compile("(-\\+|\\+-)");
        Matcher finalMatch = finalCheck.matcher(refactoringZERO);
        //Will replace -+ by -.
        String result = finalMatch.replaceAll("-");
        return result;
    }

    public static String checkVariables(String userInput) {
        // Example: a=5 will be replaced by a = 5...
        Pattern checkPastedVariables = Pattern.compile("([a-z]*=[0-9]+|[a-z]*=[a-z]*)", Pattern.CASE_INSENSITIVE);
        Matcher matchPastedVariables = checkPastedVariables.matcher(userInput);
        if (matchPastedVariables.matches()){
            return userInput.replace("=", " = ").trim();
        } else {
            Pattern extraSpace = Pattern.compile("\\s+");
            Matcher matchSpace = extraSpace.matcher(userInput);
            //Will remove all spaces between the expression.
            return matchSpace.replaceAll(" ");
        }
    }

    static String expressionValidator(String userInput) {
        /*
         * IN: 123+
         * OUT: Invalid expression
         */
        Pattern endWithASign = Pattern.compile("(.*?\\+\\s*?|.*?-\\s*?)", Pattern.CASE_INSENSITIVE);
        Matcher matchSign = endWithASign.matcher(userInput);

        /*
         *  * IN: /go OR go OR test OR /test OR any other unknow command.
         *  * OUT: Unknown command
         */
        Pattern wrongCommand = Pattern.compile("/[a-z]*", Pattern.CASE_INSENSITIVE);
        Matcher matchCommand = wrongCommand.matcher(userInput);

        /*
         * IN: 18 22
         * OUT: Invalid expression
         */
        boolean containSign = userInput.contains("+") || userInput.contains("-") || userInput.contains("=");
        boolean knownCommands = !(userInput.equals("/exit") || userInput.equals("/help") || userInput.equals(""));
        boolean isHigherThan = userInput.length() > 5;

        if(matchSign.matches() && knownCommands){
            return "Invalid Expression";
        } else if (matchCommand.matches() && knownCommands){
            return "Unknown command";
        } else if (!containSign && knownCommands && isHigherThan){
            return "Invalid Expression";
        }
        else {
            return userInput;
        }
    }
}

class Main{
    public static void main(String[] args) {
        SmartCalculatorContext calculMethod = new SmartCalculatorContext();
        // set a concrete calculation method
        calculMethod.setCalculationMethod(new CalculationMethod());
        calculMethod.calculationMethod(null);
    }
}

package smartCalculator.step5;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this Project, we'll use the Strategy Design Pattern because it will be
 * easier to add new features for the next steps, without having to change the logic.
 *
 * Description - Step 5:
 * The program has been modified to handle different cases when the given expression has an invalid format.
 * The program should print "Invalid expression" in such cases.
 * The program must never throw the NumberFormatException or any other exception.
 * If a user enters an invalid command, the program must print "Unknown command".
 * All messages must be printed without quotes.
 *
 * IN: 8 + 7 - 4
 * OUT : 11
 *
 * IN: abc
 * OUT: Invalid expression
 *
 * IN: 123+
 * OUT: Invalid expression
 *
 * IN: +15
 * OUT: 15
 *
 * IN: 18 22
 * OUT: Invalid expression
 *
 * IN: -22
 * OUT: -22
 *
 * IN: 22-
 * OUT: Invalid expression
 *
 * IN: /go
 * OUT: Unknown command
 *
 * IN: /exit
 * OUT: Bye!
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

        do {
            String userInput = scanner.nextLine();
            String invalidExpression = GeneralInfos.expressionValidator(userInput);

            switch (invalidExpression){
                case "/exit":
                    GeneralInfos.bye();
                    isAurevoir = true;
                    return;
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
                "\n2 -- 2 equals 2 - (-2) equals 2 + 2.");
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
        boolean containSign = userInput.contains("+") || userInput.contains("-");
        boolean knownCommands = !(userInput.equals("/exit") || userInput.equals("/help") || userInput.equals(""));

        if(matchSign.matches() && knownCommands){
            return "Invalid Expression";
        } else if (matchCommand.matches() && knownCommands){
            return "Unknown command";
        } else if (!containSign && knownCommands){
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

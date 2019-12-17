package smartCalculator.step4;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In this Project, we'll use the Strategy Design Pattern because it will be
 * easier to add new features for the next steps, without having to change the logic.
 *
 * Description - Step 4:
 * At this stage, the calculator should support the addition + and subtraction - operators.
 * The program must calculate expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on.
 * It must support both unary and binary minus operators.
 * If the user has entered several operators following each other, the program still should work (like Java or Python REPL).

 * Consider that the even number of minuses gives a plus, and the odd number of minuses gives a minus!
 * Look at it this way: 2 -- 2 equals 2 - (-2) equals 2 + 2.
 *
 * Input/Output example
 * The example below shows input and the corresponding output.
 *
 * In: 8
 * Out: 8
 *
 * In: -2 + 4 - 5 + 6
 * Out: 3
 *
 * In : 9 +++ 10 -- 8
 * Out : 27
 *
 * In : 3 --- 5
 * Out : -2
 *
 * In : 14  -   12
 * Out : 2
 *
 * /exit
 * Bye!
 */

interface SmartCalculator {
    void addition(String [] array);
}

class Addition implements SmartCalculator{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void addition(String [] array) {
        int sum = 0;
        boolean isAurevoir = false;

        do {
            String userInput = scanner.nextLine();

            if (userInput.equals("/exit")){
                GeneralInfos.bye();
                isAurevoir = true;
                break;
            } else if (userInput.equals("/help")){
                GeneralInfos.help();
                continue;
            } else if (userInput.equals("")) {
                System.out.println(userInput);
                continue;
            }

            String userInputcleaner = GeneralInfos.regexCleaner(userInput);
            array = userInputcleaner.split(" ");
            sum = Integer.parseInt(array[0]);
            try {
                //Sum calculation...
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

    void addition(String [] array) {
        this.smartCalculator.addition(array);
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
}

class Main{
    public static void main(String[] args) {
        SmartCalculatorContext calculMethod = new SmartCalculatorContext();
        // set a concrete calculation method
        calculMethod.setCalculationMethod(new Addition());
        calculMethod.addition(null);
    }
}

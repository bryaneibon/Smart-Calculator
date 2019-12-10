package smartCalculator.step3;
import java.util.Scanner;

/**
 * In this Project, we'll use the Strategy Design Pattern because it will be
 * easier to add new features for the next steps, without having to change the logic.
 *
 * Description - Step 3:
 * At this stage, the program should read an unlimited sequence of numbers from
 * the standard input and calculate their sum. Also, add a /help command
 * to print some information about the program.
 *
 * Input/Output example
 * The example below shows input and the corresponding output.
 * Your program should work in the same way.
 *
 * 4 5 -2 3
 * 10
 * 4 7
 * 11
 * 6
 * 6
 * /help
 * The program calculates the sum of numbers
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
                GeneralTasks.bye();
                isAurevoir = true;
                break;
            } else if (userInput.equals("/help")){
                GeneralTasks.help();
                continue;
            }
            array = userInput.split(" ");

            try {
                //Sum calculation...
                for (String value: array) {
                    sum = sum + Integer.parseInt(value);
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

class GeneralTasks {
    static void bye(){
        System.out.println("Bye!");
    }

    static void help(){
        System.out.println("The program calculates the sum of numbers");
    }
}

class Main{
    public static void main(String[] args) {
        SmartCalculatorContext calculMethod = new SmartCalculatorContext();
        String [] myArray = null;
        // set a concrete calculation method
        calculMethod.setCalculationMethod(new Addition());
        calculMethod.addition(myArray);
    }
}

package smartCalculator.step2;

import java.util.Scanner;

/**
 * In this Project, we'll use the Strategy Design Pattern because it will be
 * easier to add new features for the next steps, without having to change the logic.
 *
 * Description - Step 2:
 * Write a program that reads two numbers in a loop and prints the sum in the standard output.
 * If a user enters only a single number, the program should print the same number.
 * If a user enters an empty line, the program should ignore it.

 * When the command /exit is entered, the program must print "Bye!" (without quotes), and then stop.

 * Input/Output example
 * The example below shows input and the corresponding output. Your program should work in the same way.
 * 17 9
 * 26
 * -2 5
 * 3
 * 7
 * 7
 * /exit
 * Bye!
 */

interface SmartCalculator {
    void addition(int[] array);
}

class Addition implements SmartCalculator{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void addition(int[] array) {
        int i = 0;
        boolean isAurevoir = false;
        while(!isAurevoir){
            do {
                String userInput = scanner.next();
                if (userInput.equals("/exit")){
                    GeneralTasks.bye();
                    isAurevoir = true;
                    break;
                }

                int arrayValues = Integer.parseInt(userInput);
                array[i] = arrayValues;
                i+=1;
            }while (i < array.length);
            i = 0;
            //Sum calculation...
            if (!isAurevoir){
                int sum = 0;
                for (int value: array) {
                    sum = sum + value;
                }
                System.out.println(sum);
            }
        }
    }
}

class SmartCalculatorContext {
    private SmartCalculator smartCalculator;
    void setCalculationMethod(SmartCalculator smartCalculator){
        this.smartCalculator = smartCalculator;
    }

    void addition(int[] array) {
        this.smartCalculator.addition(array);
    }
}
class GeneralTasks {
    static void bye(){
        System.out.println("Bye!");
    }
}

class Main{
    public static void main(String[] args) {
        int [] myArray = new int[2];
        SmartCalculatorContext calculMethod = new SmartCalculatorContext();
        // set a concrete calculation method
        calculMethod.setCalculationMethod(new Addition());
        calculMethod.addition(myArray);
    }
}
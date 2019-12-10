package smartCalculator.step3;

import java.util.Scanner;

/**
 * In this Project, we'll use the Strategy Design Pattern because it will be
 * easier to add new features for the next steps, without having to change the logic.
 *
 * Description - Step 1:
 * Write a program that reads two integer numbers from the
 * same line and prints their sum in the standard output.
 * Numbers can be positive, negative, or zero.
 *
 * Input/Output example
 * The example below shows input and the corresponding output.
 * Your program should work in the same way.
 *
 * 5 8
 * 13
 */

interface SmartCalculator {
    void addition();
}

class Addition implements SmartCalculator{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void addition() {
        int i = 0;
        int sum = 0;

        boolean isAurevoir = false;
        boolean askForHelp = false;
        int [] myArray = null;

        while(!isAurevoir){
            do {

                String userInput = scanner.next();
                if (userInput.equals("/exit")){
                    GeneralTasks.bye();
                    isAurevoir = true;
                    break;
                }

                if (userInput.equals("/help")){
                    GeneralTasks.help();
                    askForHelp = true;
                    break;
                }
                myArray = new int[userInput.length()];
                int arrayValues = Integer.parseInt(userInput);
                myArray[i] = arrayValues;
                i+=1;

            } while (!isAurevoir);

            //Sum calculation...
            for (int value: myArray) {
                sum = sum + value;
            }
            System.out.println(sum);
        }
    }
}

class SmartCalculatorContext {
    private SmartCalculator smartCalculator;
    void setCalculationMethod(SmartCalculator smartCalculator){
        this.smartCalculator = smartCalculator;
    }

    void addition() {
        this.smartCalculator.addition();
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
        // set a concrete calculation method
        calculMethod.setCalculationMethod(new Addition());
        calculMethod.addition();
    }
}
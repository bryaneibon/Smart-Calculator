package smartCalculator.step1;

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
    void addition(int [] array);
}

class Addition implements SmartCalculator{
    @Override
    public void addition(int[] array) {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        do {
            int arrayValues = scanner.nextInt();
            array[i] = arrayValues;
            i+=1;
        }while (i < array.length);

        //Sum calculation...
        int sum = 0;

        for (int value: array) {
            sum = sum + value;
        }
        System.out.println(sum);
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

class Main{
    public static void main(String[] args) {
        int [] myArray = new int[2];
        SmartCalculatorContext calculMethod = new SmartCalculatorContext();
        // set a concrete calculation method
        calculMethod.setCalculationMethod(new Addition());
        calculMethod.addition(myArray);
    }
}
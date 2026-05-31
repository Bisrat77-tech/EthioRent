package utils;

import java.util.*;

public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt){
        System.out.print(prompt);
        while (true){
            try{
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e){
                System.out.print("Invalid input! Please enter a number:");

                scanner.nextLine();
            }
        }
    }
    public static double getDoubleInput(String prompt){
        System.out.print(prompt);
        while (true){
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            }catch (InputMismatchException e){
                System.out.print("Invalid input! Please enter a number:");
                scanner.nextLine();
            }
        }
    }
    public static String getStringInput(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
    public static Scanner getScanner(){
        return scanner;
    }
}

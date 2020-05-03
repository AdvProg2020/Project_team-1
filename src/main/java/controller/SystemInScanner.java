package controller;

import java.util.Scanner;

public class SystemInScanner {
    private static final Scanner scanner = new Scanner(System.in);
    private SystemInScanner(){}
    public static Scanner getScanner() {
        return scanner;
    }
}

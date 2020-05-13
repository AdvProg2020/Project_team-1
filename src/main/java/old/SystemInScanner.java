package old;

import java.util.Scanner;

public class SystemInScanner {
    CommandProcess commandProcess;

    private static final Scanner scanner = new Scanner(System.in);
    private SystemInScanner(){}
    public static Scanner getScanner() {
        return scanner;
    }
}

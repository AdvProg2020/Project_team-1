package bank;

import common.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BankDirectConnection {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(Constants.BANK_SERVER_IP, Constants.BANK_SERVER_PORT);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("exit")) {
            outputStream.writeUTF(input);
            System.out.println(inputStream.readUTF());
        }
        inputStream.close();
        outputStream.close();
        socket.close();
        scanner.close();
    }
}

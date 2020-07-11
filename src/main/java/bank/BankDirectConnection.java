package bank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BankDirectConnection {
    public static void main(String[] args) throws IOException {
        int port = 12346;
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket("127.0.0.1", port);
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

package com.linkin.netty.july.day05.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BioClient {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.print("请输入：");
            String s = in.nextLine();
            Socket socket = new Socket("127.0.0.1", 6666);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(s.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        }
    }
}

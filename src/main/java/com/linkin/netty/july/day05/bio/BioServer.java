package com.linkin.netty.july.day05.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws IOException {


        // 创建一个线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        System.out.println("Server started");

        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            final Socket socket = serverSocket.accept();
            cachedThreadPool.execute(() -> {
                byte[] bytes = new byte[1024];
                try {
                    InputStream inputStream = socket.getInputStream();
                    while (true) {
                        System.out.printf("current thread name is: %s \n", Thread.currentThread().getName());
                        int read = inputStream.read(bytes);
                        if (read != -1) {
                            System.out.println(new String(bytes, 0, read));
                        } else {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                        System.out.println("---socket shutdown---");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

        }
    }
}

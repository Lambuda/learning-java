package com.haier.rrswl.itms.netty.jdk.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class IoClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 8000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();//获得输出流
        //包装输入流，输出流

        BufferedReader inRead = new BufferedReader(new InputStreamReader(in));

        PrintWriter outWriter = new PrintWriter(out);


        while (true) {

            String str = new Scanner(System.in).nextLine();
            System.out.println("键盘输入" +str);

            outWriter.println(str);//输出到服务端

            outWriter.flush();//刷新缓冲区

            if (str.equals("end")) {

                break;

            }//退出

           // System.out.println(inRead.readLine());//读取服务端输出

        }

        socket.close();
    }
}

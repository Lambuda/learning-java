package com.haier.rrswl.itms.netty.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {

    public static void main(String[] args)throws IOException {
        ServerSocket socket = new ServerSocket(8080);

        Socket accept = socket.accept();

        BufferedReader br = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        PrintWriter printWriter = new PrintWriter(accept.getOutputStream());
        while (true){
            String s = br.readLine();
            System.out.println("获取客户端输入：" +s);
            if("end".equals(s)) break;
        }

        accept.close();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.copel.distribuidos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Jessica
 */
public class TCPServer implements Runnable{

    @Override
    public void run() {
            try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				ConnectionTCP c = new ConnectionTCP(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}

    }
    
}

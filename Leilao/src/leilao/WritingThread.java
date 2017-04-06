/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.copel.distribuidos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1562711
 */
public class WritingThread implements Runnable {

    String leitura1 = " ";
    Scanner scan = new Scanner(System.in);

    public void setLeitura1(String leitura1) {
        this.leitura1 = leitura1;
    }
    
    @Override
    public void run() {

        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);

            while (true) {

                if (!(leitura1.equals(" ")) && !(leitura1.equals("sair"))){
                    

                    if (!"sair".equals(leitura1)) {
                        byte[] m = leitura1.getBytes();
                        DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                        try {
                            s.send(messageOut);
                        } catch (IOException ex) {
                            Logger.getLogger(WritingThread.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } else if(leitura1.equals("sair")) {
                    s.leaveGroup(group);
                    break;
                }
                
               //      Thread.yield();
              
            }

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) {
                s.close();
            }
        }

    }

}

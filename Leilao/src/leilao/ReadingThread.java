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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1562711
 */
public class ReadingThread implements Runnable {

    String leitura1 = " ";
    Scanner scan = new Scanner(System.in);
    MulticastSocket s;
    InetAddress group;
    String menssagemSaida;

    
    
    @Override
    public void run() {

        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);

           

            while (true) {
                     byte[] buffer = new byte[1000];
                if (!(leitura1.equals("sair"))) {
                            
                    //le a mensagem
                    
                    DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                    try {
                        s.receive(messageIn);
                       this.menssagemSaida = new String(messageIn.getData());
                       
                    } catch (IOException ex) {
                        Logger.getLogger(ReadingThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                  //  System.out.println("Received:" + menssagemSaida);
                    
                    
                } else {

                    s.leaveGroup(group);
                    break;

                }
              // Thread.yield();
               
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
    
    public String getMenssagemSaida() {
        
        if(menssagemSaida!=null){
        return menssagemSaida;
        }else{
        return "-1";
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoins;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.*; 

/**
 *
 * @author samuel
 */
public class MultiCastServer extends Thread {
    MulticastSocket s       = null;
    InetAddress group       = null;
    Process process         = null;
    DatagramSocket socket   = null;
    String MULT_IP          = null;
    int MULT_PORT           = 0;
    
    
    
    /**
     *
     * @param p Process
     * @param MULT_IP Multicast IP
     * @param MULT_PORT Multicast Port
     */
    public MultiCastServer (Process p, String MULT_IP, int MULT_PORT) throws SocketException {
        this.socket = new DatagramSocket();
        this.process = p;
        this.MULT_IP = MULT_IP;
        this.MULT_PORT = MULT_PORT;
        
        // ********************************************
        // Inserts this thread into the Multicast Group.
        try {
            group = InetAddress.getByName(MULT_IP);
            s = new MulticastSocket(MULT_PORT);
            s.joinGroup(group);
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
        // ********************************************
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                // ********************************************
                // Receives the multicast message and
                // deserializing it
                byte[] buffer = new byte[1024];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                byte[] m = messageIn.getData();
                ByteArrayInputStream bis = new ByteArrayInputStream(m);
                ObjectInputStream ois = new ObjectInputStream(bis);
                // ********************************************
                char type = ois.readChar();
                System.out.println("Type = " + type);
            } catch (IOException e) {
                System.out.println("run" + e.getMessage());
            }
        }
    }
}

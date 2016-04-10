/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoins;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuel
 */
public class UniCastServer extends Thread {
    DatagramSocket socket = null;
    MulticastSocket s     = null;
    InetAddress group     = null;
    Process process       = null;
    String MULT_IP        = null;
    int MULT_PORT         = 0;
    
    /*
    * @param p Process
    * @param MULT_IP Multicast IP
    * @param MULT_PORT Multicast Port
    */
    public UniCastServer(Process p, String MULT_IP, int MULT_PORT) {
        process = p;
        this.MULT_IP = MULT_IP;
        this.MULT_PORT = MULT_PORT;
        
        // ********************************************
        // Creates the UDP Socket in the port of the process.
        try {
            socket = new DatagramSocket(process.getPort());
        } catch (IOException ex) {
            System.out.println("Creation of socket: " + ex);
        }
        
        // ********************************************
        // Joining the multicast group.
        try {
            group = InetAddress.getByName(MULT_IP);
            s = new MulticastSocket(MULT_PORT);
            s.joinGroup(group);
        } catch (IOException e) {
            System.out.println("Joining Multicast:" + e.getMessage());
        }
    }
    
    @Override
    public void run() {
        byte[] buffer;
        char type; // type of message
        DatagramPacket messageIn;
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        
        while (true) {
            try {
                // ********************************************
                // Receiving an UDP message
                buffer = new byte[1024];
                messageIn = new DatagramPacket(buffer, buffer.length);
                socket.receive(messageIn);
                
                bis = new ByteArrayInputStream(buffer);
                ois = new ObjectInputStream(bis);
                type = ois.readChar();
                
                switch (type) {
                    // ********************************************
                    // types supported:
                    // N --> Information of others process.
                    
                    case ('N'): 
                        // *********************************************
                        // Unpacking rest of the message
                        int id = ois.readInt();
                        int port = ois.readInt();
                        PublicKey pubKey = (PublicKey) ois.readObject();
                        int coinAmount = ois.readInt();
                        int coinPrice = ois.readInt();
                        
                        // *********************************************
                        // Creating new process and add in the list of process
                        Process newProcess = new Process(id, port, pubKey, coinAmount, coinPrice);
                        BitCoins.processList.add(newProcess);
                        
                        System.out.println("\n[UNICAST - RECEIVE] ID: " + id +
                                " Porta: "+ port + " | Public Key: -Intern- | " +
                                "Coin Amount: " + coinAmount +
                                " | Coin Price: "+ coinPrice);
                }
            } catch (IOException ex) {
                System.out.println("Unicast Exception");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UniCastServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

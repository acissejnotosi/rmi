/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoins;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        byte[] buffer;
        char type; // type of message
        DatagramPacket messageIn;
        byte[] msg;
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        
        while (true) {
            try {
                // ********************************************
                // Receives the multicast message and deserializing it
                buffer = new byte[1024];
                messageIn = new DatagramPacket(buffer, buffer.length);
                s.receive(messageIn);
                
                msg = messageIn.getData();
                bis = new ByteArrayInputStream(msg);
                ois = new ObjectInputStream(bis);
                type = ois.readChar();
                
                switch (type) {
                    // ********************************************
                    // types supported:
                    // N --> New processes (get its Public Key)
                    
                    case 'N':
                        int pid = ois.readInt();
                        
                        // if msg id is of the this process, ignores it.
                        if (pid == process.getId()) {
                            break;
                        } else {
                            // *********************************************
                            // Unpacking rest of the message
                            int port = ois.readInt();
                            PublicKey pubKey = (PublicKey) ois.readObject();
                            int coinAmount = ois.readInt();
                            int coinPrice = ois.readInt();
                            
                            // *********************************************
                            // Creating new process and add in the list of process
                            Process newProcess = new Process(pid, port, pubKey, coinAmount, coinPrice);
                            BitCoins.processList.add(newProcess);
                            
                            // *********************************************
                            // Sending to new process its infos.
                            // Packing the message.
                            ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
                            ObjectOutputStream oos = new ObjectOutputStream(bos);
                            oos.writeChar('N');
                            oos.writeInt(process.getId());
                            oos.writeInt(process.getPort());
                            oos.writeObject(process.getPubKey());
                            oos.writeInt(process.getCoinAmount());
                            oos.writeInt(process.getCoinPrice());
                            oos.flush();
                            
                            byte[] output = bos.toByteArray();
                            DatagramPacket messageOut = new DatagramPacket(output, output.length, messageIn.getAddress(), port);
                            System.out.println("");    
                            System.out.print("[MULTICAST - RECEIVE]");
                            System.out.print(" ID: " + pid);
                            System.out.print(", Port: " + port);
                            System.out.print(", Public Key: -Intern-");
                            System.out.print(", Coin Amount: " + coinAmount);
                            System.out.println(", Coin Price: " + coinPrice);
                            
                            System.out.println("");    
                            System.out.print("[UNICAST - SEND]");
                            System.out.print(" Sending to ID " + pid + " these infos:");
                            System.out.print(" ID: " + process.getId());
                            System.out.print(", Port: " + process.getId());
                            System.out.print(", Public Key: -Intern-");
                            System.out.print(", Coin Amount: " + process.getCoinAmount());
                            System.out.println(", Coin Price: " + process.getCoinPrice());
                            
                            socket.send(messageOut);
                            
                            //System.out.println("\n[MULTICAST - RECEIVE] ID: " + pid + 
                            //        " Port: " + port +
                            //        " | Public Key: -Intern- | Coin Amount: " + 
                            //        coinAmount + " | Coin Price: " + coinPrice);
                            //System.out.println("\n[UNICAST - SEND] Sending to ID " + pid + 
//                                    " these infos : ID: " + 
//                                    process.getId() +" Port: " + process.getId() +
//                                    " | Public Key: -Intern- | Coin Amount:" + 
//                                    process.getCoinAmount() + " | Coin Price: " + 
//                                    process.getCoinPrice());
                            
                            
                            
                        }
                        
                }
            } catch (IOException e) {
                System.out.println("run" + e.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MultiCastServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

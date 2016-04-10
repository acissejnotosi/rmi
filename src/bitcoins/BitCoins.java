package bitcoins;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/*  PLANNING
    Comunicação Broadcast.
    Comunicação Monocast.
*/

/**
 *
 * @author Samuel Pelegrinello Caipers
 * Sistemas Distribuidos - Tarefa 01
 * 
 */
public class BitCoins {
    static ArrayList<Process> processList = new ArrayList<>();
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        
        
        int MULT_PORT   = 6789;
        String MULT_IP  = "228.5.6.7";
        
        MulticastSocket s       = null;
        DatagramSocket socket   = null;
        Process process         = null;
        Keys keys               = null;
        PrivateKey privKey      = null;
        PublicKey pubKey        = null;
        
        try {
            // ********************************************
            // Initialization of the Multicast's group
            InetAddress group = InetAddress.getByName(MULT_IP);
            s = new MulticastSocket(MULT_PORT);
            s.joinGroup(group);
            

            // ********************************************
            // Receiving data from user
            int id;
            int port;
            int coinPrice;
            int coinAmount = 100;
            
            Scanner in = new Scanner(System.in);
            System.out.println("Inform the ID of this process:");
            id = Integer.parseInt(in.nextLine().trim());
            
            System.out.println("Inform the port of communication:");
            port = Integer.parseInt(in.nextLine().trim());
           
            System.out.println("Inform the price of coins:");
            coinPrice = Integer.parseInt(in.nextLine().trim());
            
            // ********************************************
            // Generating keys for this process
            keys = new Keys();
            privKey = keys.getPriv();
            pubKey = keys.getPub();
             
            // ********************************************
            // Packeting all information in a process' object
            // Serializing the process' object
            // N --> New process
            process = new Process(id, port, pubKey, coinAmount, coinPrice);
            BitCoins.processList.add(process);
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            
            oos.writeChar('N');
            oos.writeInt(id);
            oos.writeInt(port);
            oos.writeObject(pubKey);
            oos.writeInt(coinAmount);
            oos.writeInt(coinPrice);
            oos.flush();
            
            
            // *********************************************
            // Initializing multicast and unicast communication
            MultiCastServer multCastComm = new MultiCastServer(process, MULT_IP, MULT_PORT);
            multCastComm.start();
            UniCastServer uniCastComm = new UniCastServer(process, MULT_IP, MULT_PORT);
            uniCastComm.start();
            
            
            // *********************************************
            // Sending multicast notification of its presence.
            byte[] m = bos.toByteArray();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, MULT_PORT);
 
            //Timestamp date = new Timestamp(System.currentTimeMillis());
            //long timestamp = date.getTime();
 
            System.out.println("\n[MULTICAST SEND] Sending information about this new process:");
            System.out.print("[MULTICAST SEND]");
            System.out.print(" ID: " + id);
            System.out.print(", Port: " + port);
            System.out.print(", Public Key: -Intern-");
            System.out.print(", Coin Amount: " + coinAmount);
            System.out.println(", Coin Price: " + coinPrice);
            s.send(messageOut);
            
            
            // *********************************************
            // After its annunciation, some commands may be performed, as follows:
            // B --> to [B]uy coins
            // V --> to [V]erify internal BD
            // T --> log of [T]ransactions
            // E --> to [E]xit
            while (true) {
                String cmd;
                
                System.out.println("");
                System.out.print("Press: ");
                System.out.print("B to buy coins; ");
                System.out.print("V to verify intenal DB; ");
                System.out.print("T to see log of Transactions; ");
                System.out.println("E to exit");
                
                cmd = in.nextLine().trim().toUpperCase();
                
                Iterator it;
                switch (cmd) {
                    
                    case "B":
                        int cmdBId;
                        //System.out.println("Case B");
                        
                        System.out.println("Do buy from which process ID?");
                        cmdBId = Integer.parseInt(in.nextLine().trim());
                        
                        //check if this process ID exists
                        it = BitCoins.processList.iterator();
                        while (it.hasNext()) {
                            Process p = (Process) it.next();
                            System.out.println(p.getId());
                            System.out.println(cmdBId);
                            if (p.getId() == cmdBId) {
                                System.out.println("Process has been found");
                            } else {
                                System.out.println("Process has not been found, try again");
                            }
                        }
                        break;
                        
                    case "V":
                        System.out.println("Print DB");

                        //Print all DBs
                        it = BitCoins.processList.iterator();
                        while (it.hasNext()) {
                            Process p = (Process) it.next();
                            System.out.println(p.printProcess());
                        }
                        break;
                    
                    case "T":
                        System.out.println("Case T");
                        break;
                        
                    case "S":
                        System.out.println("Case S");
                        s.leaveGroup(group);
                        s.close();
                        System.exit(0);
                    
                    
                }
                
                
            
            }
            

        } catch (SocketException e) {
            System.out.println("Socket error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } finally {
            
            if (s != null) {
                s.close();
            }
        }                    
    }
}

class Process implements Serializable {
    private final int id;
    private final int port;
    private int coinAmount;
    private final int coinPrice;
    private final PublicKey pubKey;
    //boolean wait;
    //boolean mineflag;
    
    public Process(int id, int port, PublicKey pubKey, int coinAmount, int coinPrice) {
        this.id = id;
        this.port = port;
        this.pubKey = pubKey;
        this.coinAmount = coinAmount;
        this.coinPrice = coinPrice;
    }
    
    public void setCoinAmount(int coinAmount) {
        this.coinAmount = coinAmount;
    }
    
    public String printProcess() {
        return "Process{ID=" + id + ", port=" + port + ", coinAmount=" + coinAmount + ", coinPrice=" + coinPrice + '}';
    }
    
    public int getId() {
        return id;
    }
    
    public int getPort() {
        return port;
    }
    
    public int getCoinAmount() {
        return coinAmount;
    }
    
    public int getCoinPrice() {
        return coinPrice;
    }
    
    public PublicKey getPubKey() {
        return pubKey;
    }
}

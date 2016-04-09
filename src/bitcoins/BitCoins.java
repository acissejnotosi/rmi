package bitcoins;
import java.net.*;
import java.io.*;
import java.security.PublicKey;
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
    public static void main(String[] args) throws UnknownHostException {
        int MULT_PORT   = 6789;
        String MULT_IP  = "228.5.6.7";
        
        MulticastSocket s       = null;
        DatagramSocket socket   = null;
        Process process         = null;
        
        try {
            // ********************************************
            // Initialization of the Multicast's group
            InetAddress group = InetAddress.getByName(MULT_IP);
            s = new MulticastSocket(MULT_PORT);
            s.joinGroup(group);
            // ********************************************

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
            // **********************************************
            
            // ********************************************
            // Packeting all information in a process' object
            // Serializing the process' object
            // N --> New process
            process = new Process(id, port, coinAmount, coinPrice);
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            
            oos.writeChar('N');
            oos.writeInt(id);
            oos.writeInt(port);
            //oos.writeObject(pubKey);
            oos.writeInt(coinAmount);
            oos.writeInt(coinPrice);
            oos.flush();
            
            // *********************************************
            // Sending multicast notification of its presence.
            byte[] m = bos.toByteArray();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, group, MULT_PORT);
            s.send(messageOut);
            // *********************************************
            
            while (true) {}
            

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
    
    
//    // method that send multicast information
//    public static void sendMulticast(MulticastSocket s, InetAddress group, byte[] msg) {
//        try {
//            
//        } catch (SocketException e) {
//            System.out.println("Socket error: " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("IO error: " + e.getMessage());
//        } finally {
//            if (s != null) {
//                s.close();
//            }
//        }
//    }
//    
//    // method that receives unicast information
//    public DatagramPacket receiveMulticast(MulticastSocket s, byte[] buf) {
//        try {
//            DatagramPacket messageIn = new DatagramPacket(buf, buf.length);
//            s.receive(messageIn);
//            System.out.println("MessageIn :" + messageIn);
//            return messageIn;
//        } catch (SocketException e) {
//            System.out.println("Socket error: " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("IO error: " + e.getMessage());
//        } finally {
//            if (s != null) {
//                s.close();
//            }
//        }
//        return null;
}

class Process implements Serializable {
    int id;
    int port;
    int coinAmount;
    int coinPrice;
    //PublicKey pub;
    //boolean wait;
    //boolean mineflag;
    
    public Process(int id, int port, int coinAmount, int coinPrice) {
        this.id = id;
        this.port = port;
        //this.pub = pub;
        this.coinAmount = coinAmount;
        this.coinPrice = coinPrice;
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
}

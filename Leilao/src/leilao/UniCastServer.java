package leilao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import static leilao.InitSystem.procesosInteresados;
import static leilao.InitSystem.processList;


/**
 *
 * @author Jessica
 */
public class UniCastServer extends Thread {

    DatagramSocket socket = null;
    MulticastSocket s = null;
    InetAddress group = null;
    Process process = null;
    String MULT_IP = null;
    int MULT_PORT = 0;

    /*
    * @param p Process
    * @param MULT_IP Multicast IP
    * @param MULT_PORT Multicast Port
     */
    public UniCastServer(Process p, String MULT_IP, int MULT_PORT) {
        this.process = p;
        this.MULT_IP = MULT_IP;
        this.MULT_PORT = MULT_PORT;

        // ********************************************
        // Creates the UDP Socket in the port of the process.
        try {
        socket = new DatagramSocket(Integer.parseInt(process.getPort()));
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
                String pid;
                String port;
                PublicKey pubKey;
                String nomeProduto;
                String idProduto;
                String descProduto;
                String precoProduto;

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
                    // B --> Buying request

                    case ('N'):
                        // *********************************************
                        // Descompactando messagem
                            pid = ois.readUTF();
                            port = ois.readUTF();
                            PublicKey chavePublica = (PublicKey) ois.readObject();
                            nomeProduto = ois.readUTF();
                            idProduto = ois.readUTF();
                            descProduto = ois.readUTF();
                            precoProduto = ois.readUTF();
          
                        // *********************************************
                        // Creating new process and add in the list of process
                        Process novoProcesso = new Process(pid, port, chavePublica, nomeProduto, idProduto, descProduto, precoProduto);
                        InitSystem.processList.add(novoProcesso);
                        
                        System.out.println("");
                        System.out.print("[UNICAST - RECEIVE]");
                        System.out.print(" ID do participante: " + pid);
                        System.out.print(", Porta: " + port);
                        System.out.print(", Chave publica: - ");
                        System.out.print(", Nome produto: " + nomeProduto);
                        System.out.println(",ID Produto: " + idProduto);
                        System.out.print(",Descricao do produto: " + descProduto);
                        System.out.println(",Preco do produto: " + precoProduto);
                           
                        break;

                    case ('B'):
                        // Unpacking rest of the message
                        pid = ois.readUTF();
                        port = ois.readUTF();
                        String lance = ois.readUTF();
                        idProduto = ois.readUTF();
                   
                        System.out.println("");    
                        System.out.print("[UNICAST - RECEIVE]");
                        System.out.println("Requisicao de lance de processo: " + pid);
                        
                        long start = System.currentTimeMillis();
//                        long finish = System.currentTimeMillis();
//                        long total = finish - start;
                       
                        for(Process p: processList){
                            if (p.getId().equals(pid)) {
                                p.setPrecoProduto(lance);
                                 break;
                            }
                        }
                        //setar controlador de lances
                        Controle caux = null;
                        for(Controle c:  procesosInteresados){
                            if (c.getProdutoId().equals(idProduto)) {
                                   c.setLancadorId(pid);
                                   c.setTempo(start);
                                   break;
                            }
                        }
                        Conometro cono = new Conometro(pid,port,idProduto);
                        cono.start();
                         break;
                }
            } catch (IOException ex) {
                System.out.println("Unicast Exception");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UniCastServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

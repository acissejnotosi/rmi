/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.PublicKey;
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
    Process process = null;
    DatagramSocket socket = null;
    String ipMulticast = null;
    int portMulticast = 0;

    public ReadingThread(Process process, String ipMulticast, int portMulticast) throws SocketException, UnknownHostException, IOException {
        this.socket = new DatagramSocket();
        this.process = process;
        this.ipMulticast = ipMulticast;
        this.portMulticast = portMulticast;

        // ********************************************
        // Inserts this thread into the Multicast Group.
        group = InetAddress.getByName(ipMulticast);
        s = new MulticastSocket(portMulticast);
        s.joinGroup(group);

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

            buffer = new byte[1024];

            //le a mensagem
            messageIn = new DatagramPacket(buffer, buffer.length);
            try {
                s.receive(messageIn);
            } catch (IOException ex) {
                Logger.getLogger(ReadingThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Received:" + new String(messageIn.getData()));

            msg = messageIn.getData();
            bis = new ByteArrayInputStream(msg);
            try {
                ois = new ObjectInputStream(bis);
                type = ois.readChar();
                // ********************************************
                // types supported:
                // N --> Novo participante(pega a chave public dele e distribui)
                // M --> For mining and key validation
                // V --> Transaction confirmation

                switch (type) {

                    case 'N':
                        int pid = ois.readInt();

                        // if msg id is of the this process, ignores it.
                        if (pid == process.getId()) {
                            break;
                        } else {
                            // *********************************************
                            // Unpacking rest of the message
                            int port = ois.readInt();
                            String nomeProduto = ois.readUTF();
                            int idProduto = ois.readInt();
                            String descProduto = ois.readUTF();
                            int precoProduto = ois.readInt();
                            PublicKey chavePublica = (PublicKey) ois.readObject();

                            // *********************************************
                            // Creating new process and add in the list of process
                            Process novoProcesso = new Process(pid, port, chavePublica, nomeProduto, idProduto, descProduto, precoProduto);
                            InitSystem.processList.add(novoProcesso);

                            // *********************************************
                            // Sending to new process its infos.
                            // Packing the message.
                            ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
                            ObjectOutputStream oos = new ObjectOutputStream(bos);
                            oos.writeChar('N');
                            oos.writeInt(novoProcesso.getId());
                            oos.writeInt(novoProcesso.getPort());
                            oos.writeObject(novoProcesso.getPubKey());
                            oos.writeObject(novoProcesso.getNomeProduto());
                            oos.writeObject(novoProcesso.getIdProduto());
                            oos.writeObject(novoProcesso.getDescProduto());
                            oos.writeObject(novoProcesso.getPrecoProduto());

                            oos.flush();

                            byte[] output = bos.toByteArray();
                            DatagramPacket messageOut = new DatagramPacket(output, output.length, messageIn.getAddress(), port);
                            System.out.println("");
                            System.out.print("[MULTICAST - RECEIVE]");
                            System.out.print(" ID do participante: " + pid);
                            System.out.print(", Porta: " + port);
                            System.out.print(", Chave publica: - ");
                            System.out.print(", Nome produto: " + nomeProduto);
                            System.out.println(",ID Produto: " + idProduto);
                            System.out.print(",Descricao do produto: " + descProduto);
                            System.out.println(",Preco do produto: " + precoProduto);
                            

                          
                            socket.send(messageOut);
                            break;
                        }
                    case 'M':
                        
                        
                        break;

                }

            } catch (IOException ex) {
                Logger.getLogger(ReadingThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ReadingThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}

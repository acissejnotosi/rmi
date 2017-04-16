package leilaoversao2;

import leilaoversao2.Produto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static leilaoversao2.LeilaoVersao2.listaProdutos;
import static leilaoversao2.LeilaoVersao2.procesosInteresados;


/**
 *Classe para o recebimento das mensagens Unicast.
 * @author Jessica
 */
public class ServidorUniCast extends Thread {

    static boolean teste = false;
    DatagramSocket socket = null;
    MulticastSocket s = null;
    InetAddress group = null;
    Processo process = null;
    String MULT_IP = null;
    int MULT_PORT = 0;

    /**
     *Contrutor da Classe ServidorUnicast
     * @param p Processo
     * @param MULT_IP IP para o Multicast
     * @param MULT_PORT Port para o Multicast
     */
    public ServidorUniCast(Processo p, String MULT_IP, int MULT_PORT) {
        this.process = p;
        this.MULT_IP = MULT_IP;
        this.MULT_PORT = MULT_PORT;


        try {
            socket = new DatagramSocket(Integer.parseInt(process.getPort()));
        } catch (IOException ex) {
            System.out.println("Creation of socket: " + ex);
        }


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
        DatagramPacket messageIn;
        ByteArrayInputStream bis;
        ObjectInputStream ois;
        Chaves gera_chave = null;

        while (true) {

            try {
                String pid;
                String port;
                char type;
                PublicKey pubKey;
                String nomeProduto;
                String idProduto;
                String descProduto;
                String precoProduto;
                  
                // ********************************************
                // Recebendo mensagem UDP
                buffer = new byte[1024];
                messageIn = new DatagramPacket(buffer, buffer.length);
                socket.receive(messageIn);
                bis = new ByteArrayInputStream(buffer);
                ois = new ObjectInputStream(bis);
                type = ois.readChar();

                System.out.println();
                switch (type) {
                   

                    case ('N'):
                        // *********************************************
                        // Descompactando messagem
                        pid = ois.readUTF();
                        port = ois.readUTF();
                        PublicKey chavePublica = (PublicKey) ois.readObject();
                        List<Produto> listaProduto = (ArrayList<Produto>) ois.readObject();
                        List<Produto> listaProdutosleiloando = (ArrayList<Produto>) ois.readObject();

                        // *********************************************
                        // Criando um novo processo
                        Processo novoProcesso = new Processo(pid, port, chavePublica, listaProduto, listaProdutosleiloando);
                        LeilaoVersao2.processList.add(novoProcesso);
                        // *********************************************
                        // Adicionando aminha Lista de Produtos produto recebido
                        adicionaListaDeProdutos(process.getId(), listaProduto);

                        // *********************************************
                        // Gerando Hash Map Encripta
                        Autenticacao auto = new Autenticacao();
                        auto.setPublic_chave(chavePublica);
                        gera_chave = new Chaves();
                      //  auto.setCriptografado(gera_chave.criptografa(pid,myChavePrivada));
                    //    assinatura.put(pid, auto);

                       // System.out.println("Lista size novo unicas " + listaProdutos.size());
                       // System.out.println("Lista size novo recebida " + listaProduto.size());
                        System.out.println("");
                        System.out.println("[UNICAST - Recebe]");
                        System.out.println(" ID do participante: " + pid);
                        System.out.println(", Porta: " + port);
                        System.out.println(", Chave publica: - ");

                        for (Produto p : listaProduto) {
                            System.out.println("Informações sobre o " + p.getName() + " produto da lista do processo " + pid);
                            System.out.println(", ID Produto " + p.getName() + ": " + p.getId());
                            System.out.println(", Descrição Produto " + p.getName() + ": " + p.getDescricao());
                            System.out.println(", Preço Produto " + p.getName() + ": " + p.getPrecoInicial());
                        }
                        System.out.println("Lista size novo unicast " + listaProduto.size());

                        break;

                    case ('B'):
                        //***************************************************
                        //desenpacota mensagem de lance
                        pid = ois.readUTF();
                        port = ois.readUTF();
                        String lance = ois.readUTF();
 
                        idProduto = ois.readUTF(); //Id produto do processo atual(leiloero)
//                        int tamanho = ois.readInt();//Id produto do processo atual(leiloero)
//      
//                        byte[] mensagemCripto = new byte[tamanho];
//                        for (int i = 0; i < tamanho; i++) {
//                            mensagemCripto[i] = ois.readByte();
//                        }
//                        gera_chave = new GeraChave();
//                        
//                        String mensagemDescripta = gera_chave.decriptografa(mensagemCripto,assinatura.get(pid).getPublic_chave());
//                        
//                        //Compara mensagem  
//                        if(mensagemDescripta.equals(pid)){
//                            System.out.println("-----Autentica-------------");
//                        }
                        
                        
                        

                        System.out.println("");
                        System.out.print("[UNICAST - Recebe]");
                        System.out.println("Requisicao de lance do processo: " + pid);
                        System.out.print(", valor do lance: " + lance);

                        // verifica valor do lance maior de que valor do produto
                        Produto produto = buscaUmProdutoPorId(idProduto);
                        int to = Integer.parseInt(produto.getPrecoInicial());
                        System.out.println(to);
                        System.out.println(lance);
                        if (to > Integer.parseInt(lance)) {
                            System.out.println("Valor do Lance não é suficiente!");
                            break;
                        }

                        //alguem ja deu um lance nesse produto
                        boolean lancar = true;
                        if (LeilaoVersao2.produtosLancados.isEmpty()) {
                            LeilaoVersao2.produtosLancados.add(idProduto);
                            lancar = true;
                        } else {
                            for (String c : LeilaoVersao2.produtosLancados) {
                                if (c.equals(idProduto)) {
                                    lancar = false;
                                    break;
                                }
                            }
                        }
                        //atualiza valor do proiduto local
                        for (Produto p : process.getListaProduto()) {
                            if (p.getId().equals(pid)) {
                                p.setPrecoInicial(lance);
                                break;
                            }
                        }

                        // Enviar multcast atualizando valor do produto 
                        if (lancar) {
                            //setar controlador de lances
                            adiconaProcessoInteresado(pid, idProduto);
                            Cronometro cro = new Cronometro(socket, idProduto, process.getId(), s, group, MULT_PORT);
                            cro.start();
                            System.out.println("Leilao Inicializado produtoID:" + idProduto);
                            /// Enviando atualizacao de preco para todo multicast 
                            atualizaValorCliente(pid, idProduto, lance);
                        } else {
                            /// Enviando atualizacao de preco para todo multicast 
                            System.out.println("Lancar Notificaçao para outro interesado");
//                            notificacaParaCliente(pid,port,idProduto,lance);
                            for (Controle c : LeilaoVersao2.procesosInteresados) {
                                if (c.getProdutoId().equals(idProduto)) {
                                    for (String ids : c.getLancadorId()) {
                                        if (!ids.equals(pid)) {
                                            Processo p = procuraUmprocesso(ids);
                                            notificacaParaCliente(pid, p.getPort(), idProduto, lance);
                                        }
                                    }
                                }
                            }
                            //setar controlador de lances
                            adiconaProcessoInteresado(pid, idProduto);
                            atualizaValorCliente(pid, idProduto, lance);
                        }

                        break;
                    case ('F'):
                        //Finaliza Leilao tempo estorado

                        String leiloeiroID = ois.readUTF();
                        pid = ois.readUTF();
                        port = ois.readUTF();
                        Produto meuProduto = (Produto) ois.readObject();

                        System.out.println("depois");

                        System.out.print("[UNICAST - Recebe]");
                        System.out.print(" ID do participante: " + pid);
                        System.out.print(", Porta: " + port);
                        System.out.println("\nProduto arrematado com sucesso: " + meuProduto.getName());

                        break;

                    case ('U'):
                        ////Notificaçao de lance maior 

                        pid = ois.readUTF();
                        idProduto = ois.readUTF();
                        String novoValor = ois.readUTF();
                        System.out.println("     Notificaço recebido com sucesso!!!!!");
                        System.out.println("[UNICAST - Recebe]");
                        System.out.println(" ID do participante deu lance maior: " + pid);
                        System.out.print(", Valor do ultimo lance:  " + novoValor);

                        break;

                }

            } catch (IOException ex) {
                System.out.println("Unicast Exception");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorUniCast.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

  

    /**
     * Método que atualiza os valores do produto de um processo e envia uma mensagem Multicast
     * @param id
     * @param idProduto
     * @param novoValor
     * @throws IOException
     */
    public void atualizaValorCliente(String id, String idProduto, String novoValor) throws IOException {

        System.out.println("");
        System.out.print("[MULTICAST - Enviando]");
        System.out.print("Atualiza valores de produto");

        // *********************************************
        // Packing transaction validation.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeChar('A');
        oos.writeUTF(id);
        oos.writeUTF(idProduto);
        oos.writeUTF(novoValor);

        oos.flush();

        byte[] m1 = bos.toByteArray();
        DatagramPacket messageOut = new DatagramPacket(m1, m1.length, group, MULT_PORT);
        s.send(messageOut);

    }

    /**
     * Método que notifica o cliente que um novo lance foi realizado sobre o produto dele
     * @param id
     * @param port
     * @param idProduto
     * @param novoValor
     * @throws IOException
     */
    public void notificacaParaCliente(String id, String port, String idProduto, String novoValor) throws IOException {

        System.out.println("");
        System.out.print("[UNIACAST - Enviando]");
        System.out.print("Outro participante deu um lance valor:" + novoValor);
        System.out.println("Porta:" + port);
        // *********************************************
        // Packing transaction validation.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeChar('U');
        oos.writeUTF(id);
        oos.writeUTF(idProduto);
        oos.writeUTF(novoValor);
        oos.flush();

        byte[] output = bos.toByteArray();
        DatagramPacket request = new DatagramPacket(output, output.length, InetAddress.getLocalHost(), Integer.parseInt(port));
        socket.send(request);

    }

    /**
     * Método que procura um processo na lista de processos e retorna um processo.
     * @param id
     * @return process Processo
     */
    public static Processo procuraUmprocesso(String id) {

        Processo process = null;

        for (Processo p : LeilaoVersao2.processList) {
            if (p.getId().equals(id)) {
                process = p;
            }
        }
        return process;

    }

    /**
     * Método que adiciona um processo interessado no produto em leilão e adiciona na lista de processos lançados ou processos que realizaram um lançe naquele produto leiloado 
     * @param idProcesso
     * @param idProduto
     */
    public static void adiconaProcessoInteresado(String idProcesso, String idProduto) {

        for (Controle c : procesosInteresados) {
            if (c.getProdutoId().equals(idProduto)) {
                c.setUltimo(idProcesso);
                if (c.getLancadorId() != null) {
                    for (String ids : c.getLancadorId()) {
                        if (!ids.equals(idProcesso)) {
                            c.getLancadorId().add(idProcesso);
                            break;
                        }
                    }
                } else {
                    c.setLancadorId(new ArrayList<>());
                }
                c.getLancadorId().add(idProcesso);
            }
        }
    }

    /**
     * Método que busca um produto numa lista de produtos a partir do id do produto
     * @param id
     * @return
     */
    public static Produto buscaUmProdutoPorId(String id) {
        for (Produto p : listaProdutos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Método que adiciona um produto na lista de produtos
     * @param id
     * @param listaProduto
     */
    public void adicionaListaDeProdutos(String id, List<Produto> listaProduto) {

        for (Produto p : listaProduto) {
            listaProdutos.add(p);
            Controle controle = new Controle(p.getId(), p.getPrecoInicial());
            procesosInteresados.add(controle);
        }

    }
}

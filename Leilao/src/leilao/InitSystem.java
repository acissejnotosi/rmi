/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilao;


import java.awt.AWTException;
import java.awt.Robot;

import java.lang.management.ManagementFactory;

import java.nio.charset.StandardCharsets;



import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.rmi.transport.tcp.TCPChannel;

/**
 *
 * @author allan
 */
public class InitSystem {
    


        public static void main (String[] args) throws InterruptedException, AWTException {

        //   Inicio inicio = new Inicio();
         
           String name = ManagementFactory.getRuntimeMXBean().getName();
         //  System.out.println("Name =" + name);
           GeraChave gchave = new GeraChave();
           gchave.geraChave();   
           byte[] chavePrivada = gchave.getChavePrivada();
           byte[] chavePublica = gchave.getChavePublica();
            
           Leiloero leiloero = new Leiloero(name, chavePrivada, chavePublica);  
           Comprador comprador = new Comprador(name, chavePrivada, chavePublica);
           
           
           
           WritingThread wt = new WritingThread();        

           ReadingThread rt = new ReadingThread();         

           new Thread(wt).start();
           new Thread(rt).start();

           

            wt.setLeitura1(name + " " + new String(chavePublica, StandardCharsets.UTF_8 ));
            
  
             
            StringToOtherType strToOType = new StringToOtherType();
            while("-1".equals(rt.getMenssagemSaida()))
            {
                System.out.println("Delay para recebimento da mensagem em readingThread");
            }
            
            System.out.println("Resultado : " + rt.getMenssagemSaida());
            strToOType.stringToNomeChave(rt.getMenssagemSaida());
            
           
            if(leiloero.verificaSeExisteChave(strToOType.getNomeGuest()) == false){
            leiloero.setHashNomeChavePublica(strToOType.getNomeGuest(),strToOType.getChavePublicaGuest());
            }
       
            for(String nome : leiloero.getHashNomeChavePublica().keySet()){
            System.out.println("["+ nome + "] = " + StringToOtherType.bytesToHex(leiloero.getHashNomeChavePublica().get(nome)));
                    }
           
 Robot robot =  new Robot();
          
        System.out.println(name  );
         
         robot.delay(10);
      robot.keyPress(13); 
           
         //   wt.setLeitura1(" ");
           
//        TCPServer TCPS = new TCPServer();
//       
//        new Thread(TCPS).start();
//        
//          TCPClient TCPC =  new TCPClient();
//          TCPC.enviarMensagem("oláaaaaaaaaaaaaaaaaaaaaa");
//        
         

        }
}

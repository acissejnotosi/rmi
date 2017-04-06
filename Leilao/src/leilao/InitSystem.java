/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilao;

import java.awt.AWTException;
import java.awt.Robot;
import java.lang.management.ManagementFactory;
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
                 
          
           String name = ManagementFactory.getRuntimeMXBean().getName();
           System.out.println("Name =" + name);
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

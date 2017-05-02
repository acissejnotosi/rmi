/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Jessica
 */
public class Cliente {
 
    
     public static void main(String[] args) throws RemoteException, NotBoundException {
        // TODO code application logic here
        //Cria o registro de nomes
        Registry referenciaServicoNomes = LocateRegistry.getRegistry(1111);        
        InterfaceServ referenciaServidor = (InterfaceServ) referenciaServicoNomes.lookup("Hello World");
        new CliImpl(referenciaServidor);       
        
        
    }
}

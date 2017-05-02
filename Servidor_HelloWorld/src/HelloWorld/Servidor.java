/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Jessica
 */
public class Servidor {

    /**
     * @param args the command line arguments
     * @throws java.rmi.RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        // TODO code application logic here
        //Cria o registro de nomes
        Registry referenciaServicoNomes = LocateRegistry.createRegistry(1111);
        referenciaServicoNomes.rebind("Hello World", new ServImpl());
        
        
    }
    
}

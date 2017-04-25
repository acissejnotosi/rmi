/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_HelloWord;

import cliente_HelloWord.InterfaceCli;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author allan
 */
public class ServImpl extends UnicastRemoteObject  implements InterfaceServ{

    public ServImpl() throws RemoteException {
    }

 
    
 
    @Override
    public String chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException{
        return refCliente.echo(nomeCliente);
    }
   
}

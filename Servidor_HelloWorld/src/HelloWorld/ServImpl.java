/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;


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
    public void chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException{
       
       refCliente.echo(nomeCliente);
        
       
    }
   
}

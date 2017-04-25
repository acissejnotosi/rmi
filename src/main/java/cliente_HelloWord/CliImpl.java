/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente_HelloWord;

import java.rmi.RemoteException;

/**
 *
 * @author allan
 */
public class CliImpl implements InterfaceCli {

     public CliImpl() throws RemoteException {
    }

    
    @Override
    public String echo(InterfaceServ refServidor) throws RemoteException{
     return null;
    }
    
}

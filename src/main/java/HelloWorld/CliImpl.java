/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.RemoteException;

/**
 *
 * @author allan
 */
public class CliImpl implements InterfaceCli{

public CliImpl(InterfaceServ refServidor) throws RemoteException{
 refServidor.chamar("Ola", this);    
}
        

    @Override
    public void echo(String qualquer) throws RemoteException {
        
       System.out.println("Mensagem para o Servente Cliente: " + qualquer);
       
       
       
      }

   
    
    
}

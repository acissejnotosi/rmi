package cliente_HelloWord;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cliente_HelloWord.InterfaceCli;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author allan
 */
public interface InterfaceServ extends Remote  {
    
    public String chamar(String nomeCliente, InterfaceCli refCliente) throws RemoteException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilao;

/**
 *
 * @author Jessica
 */
public class Comprador {
    
     public String nome;
     public Byte[] message;
    private byte[] keyPrivateA;
    private byte[] keyPublicA;

    public Comprador(String nome, byte[] keyPrivateA, byte[] keyPublicA) {
        this.nome = nome;
        this.keyPrivateA = keyPrivateA;
        this.keyPublicA = keyPublicA;
    }
    
    
    
     public byte[] getKeyPrivateA() {
        return keyPrivateA;
    }

    public byte[] getKeyPublicA() {
        return keyPublicA;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setnotifica(Byte[] message){
    
        this.message = message;
    }
    
    public Byte[] getnotifica(Byte[] message){
    
        return message;
    }
    
}

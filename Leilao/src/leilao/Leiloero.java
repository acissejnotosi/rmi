/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilao;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author allan
 */
public class Leiloero {
    
    public String nome;
 //   public Multcast mult = new Multcast();
 //   public  Unicast unicast = new Unicast();
    private byte[] keyPrivateA;
    private byte[] keyPublicA;
    
    public HashMap<String,byte[]> hashNomeChavePublica;
    private List<Product> products;

    public Leiloero(String nome, byte[] keyPrivateA, byte[] keyPublicA) {
        this.nome = nome;
        this.keyPrivateA = keyPrivateA;
        this.keyPublicA = keyPublicA;
    }

   
    public String getNome() {
        return nome;
    }

    public byte[] getKeyPrivateA() {
        return keyPrivateA;
    }

    public byte[] getKeyPublicA() {
        return keyPublicA;
    }

    public List<Product> getProducts() {
        return products;
    }
    
    
}

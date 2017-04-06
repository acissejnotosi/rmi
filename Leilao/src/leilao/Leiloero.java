/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.copel.distribuidos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author allan
 */
public class Leiloero {
    
    public String nome;
 //   public Multcast mult = new Multcast();
 //   public  Unicast unicast = new Unicast();
    public byte[] keyPrivateA;
    public byte[] keyPublicA;
    public byte[] chavePublicaGuest;
    public String nomeGuest;
    
    public Map<String,byte[]> hashNomeChavePublica;
    private List<Product> products;

    public Leiloero(String nome, byte[] keyPrivateA, byte[] keyPublicA) {
        this.nome = nome;
        this.keyPrivateA = keyPrivateA;
        this.keyPublicA = keyPublicA;
       this.hashNomeChavePublica = new HashMap<>();
    }

    
    public boolean verificaSeExisteChave(String nome){
    
        return hashNomeChavePublica.containsKey(nome);
        
    }

    public Map<String,byte[]> getHashNomeChavePublica() {
        return hashNomeChavePublica;
    }

    public void setHashNomeChavePublica(String nomeGuest, byte[] chavePublicaGuest) {
        this.hashNomeChavePublica.put(nomeGuest,chavePublicaGuest);
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

    public byte[] getChavePublicaGuest() {
        return chavePublicaGuest;
    }

    public String getNomeGuest() {
        return nomeGuest;
    }

    public void setChavePublicaGuest(byte[] chavePublicaGuest) {
        this.chavePublicaGuest = chavePublicaGuest;
    }

    public void setNomeGuest(String nomeGuest) {
        this.nomeGuest = nomeGuest;
    }
    
    
    
}

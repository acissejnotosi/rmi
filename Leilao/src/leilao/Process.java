/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilao;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
class Process implements Serializable {

    private final int id;
    private final int port;
    private final String nomeProduto;
    private final int idProduto;
    private final String descProduto;
    private final int precoProduto;
    private final PublicKey chavePublica;
    static ArrayList<leilao> listaLeilao = new ArrayList<>();

    public Process(int id, int port, PublicKey chavePublica, String nomeProduto, int idProduto, String descProduto, int precoProduto) {
        this.id = id;
        this.port = port;
        this.chavePublica = chavePublica;
        this.nomeProduto = nomeProduto;
        this.idProduto = idProduto;
        this.descProduto = descProduto;
        this.precoProduto = precoProduto;

    }
    
    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public int getPrecoProduto() {
        return precoProduto;
    }

    public static ArrayList<leilao> getListaLeilao() {
        return listaLeilao;
    }
  
    public String imprimaProcessos() {
        return "Participante: " + id + ", Porta: " + port;
    }

    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public PublicKey getPubKey() {
        return chavePublica;
    }
}

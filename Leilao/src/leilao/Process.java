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

    private final String id;
    private final String port;
    private final String nomeProduto;
    private final String idProduto;
    private final String descProduto;
    private final String precoProduto;
    private final PublicKey chavePublica;
    static ArrayList<leilao> listaLeilao = new ArrayList<>();

    public Process(String id, String port, PublicKey chavePublica, String nomeProduto, String idProduto, String descProduto, String precoProduto) {
        this.id = id;
        this.port = port;
        this.chavePublica = chavePublica;
        this.nomeProduto = nomeProduto;
        this.idProduto = idProduto;
        this.descProduto = descProduto;
        this.precoProduto = precoProduto;

    }

    public static void setListaLeilao(ArrayList<leilao> listaLeilao) {
        Process.listaLeilao = listaLeilao;
    }


    public String getId() {
        return id;
    }

    public String getPort() {
        return port;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public String getPrecoProduto() {
        return precoProduto;
    }

    public PublicKey getChavePublica() {
        return chavePublica;
    }
    public static ArrayList<leilao> getListaLeilao() {
        return listaLeilao;
    }
  
    public String imprimaProcessos() {
        return "Participante: " + id + ", Porta: " + port;
    }

  

    
}

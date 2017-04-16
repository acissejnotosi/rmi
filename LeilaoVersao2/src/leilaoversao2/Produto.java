/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilaoversao2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Classe que contém os métodos de um produto
 * @author allan
 */
public class Produto implements Serializable {
    

    private String id;
    private String name;
    private String descricao;
    private String precoInicial;
    private String tempoFinal;
    private String idProcesso;

     /**
     * Construtor da classe Produto
     * @param id String 
     * @param name String
     * @param descricao String
     * @param precoInicial String
     * @param tempoFinal String
     * @param idProcesso String
     */
    public Produto(String id, String name, String descricao, String precoInicial, String tempoFinal,String idProcesso) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.precoInicial = precoInicial;
        this.tempoFinal = tempoFinal;
        this.idProcesso = idProcesso;
    }

    
     /**
     * Método retorna o id do produto
     * @return id String   
     */
    public String getId() {
        return id;
    }

    
     /**
     * Método retorna o nome do produto
     * @return name String   
     */
    public String getName() {
        return name;
    }

    /**
     * Método retorna a descrição do produto
     * @return descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Método retorna o preco do produto
     * @return precoInicial
     */
    public String getPrecoInicial() {
        return precoInicial;
    }

    /**
     * Método retorna o tempo Final do produto
     * @return tempoFinal
     */
    public String getTempoFinal() {
        return tempoFinal;
    }

    /**
     * Método seta o Id do produto
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Método seta o nome do produto
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método seta a descricao do produto
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Método seta o precoInicial do produto
     * @param precoInicial
     */
    public void setPrecoInicial(String precoInicial) {
        this.precoInicial = precoInicial;
    }

    /**
     * Método seta o tempo de duração do leilão para este produto
     * @param tempoFinal
     */
    public void setTempoFinal(String tempoFinal) {
        this.tempoFinal = tempoFinal;
    }

    /**
     * Método retorna o Id do processo que possui o produto
     * @return
     */
    public String getIdProcesso() {
        return idProcesso;
    }

    /**
     * Método que seta o id do processo que possui o produto
     * @param idProcesso
     */
    public void setIdProcesso(String idProcesso) {
        this.idProcesso = idProcesso;
    }
    
    
    

   
    
}

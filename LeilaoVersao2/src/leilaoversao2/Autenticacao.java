/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilaoversao2;

import java.security.PublicKey;

/**
 *Classe utilizada para auxiliar a troca de mensagens e autenticação dos processos
 * @author allan e jessica
 */
public class Autenticacao {
    private PublicKey public_chave= null;
    private byte[] criptografado = null;

 

    /**
     *Método que rertorna a chave pública do processo
     * @return public_chave PublicKey
     */
    public PublicKey getPublic_chave() {
        return public_chave;
    }

    /**
     *Método que seta a chave Pública do processo
     * @param public_chave 
     */
    public void setPublic_chave(PublicKey public_chave) {
        this.public_chave = public_chave;
    }

    /**
     *  Método que retorna o texto criptografado
     * @return criptografado byte[]
     */
    public byte[] getCriptografado() {
        return criptografado;
    }

    /**
     *Método que seta o texto criptografado
     * @param criptografado
     */
    public void setCriptografado(byte[] criptografado) {
        this.criptografado = criptografado;
    }

 
    
    


    
    
    
}

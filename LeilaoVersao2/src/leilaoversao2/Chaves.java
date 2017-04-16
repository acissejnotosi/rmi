/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leilaoversao2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * Classe que contém os métodos que geram as chaves publica e privada bem como método que encriptam e descriptam  mensagem.
 * @author Jessica
 */
public class Chaves {
    
    /**
     * chave privada
     */
    public PrivateKey chavePrivada;

    /**
     * chave pública
     */
    public PublicKey chavePublica;
    
    /**
     * Algoritmo RSA utilizado para gerar as chaves
     */
    public static final String ALGORITHM = "RSA";
   
    /**
     * Gera a chave que contém um par de chave Privada e Pública usando 1025
     * bytes. 
     */
    public  void geraChave() {
        try {
            
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();

            this.chavePrivada = key.getPrivate();//.getEncoded();
            this.chavePublica = key.getPublic();//.getEncoded();
            
           
           
        } catch (NoSuchAlgorithmException e) {
        }

    }

  
    /**
     * Criptografa o texto puro usando chave pública.
     * @param texto
     * @param chave
     * @return cipherText byte[]
     */
    public  byte[] criptografa(String texto, PrivateKey chave) {
        byte[] cipherText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            cipherText = cipher.doFinal(texto.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    /**
     * Decriptografa o texto puro usando chave privada.
     * @param texto
     * @param chave
     * @return dectyptedText String
     */
    public  String decriptografa(byte[] texto, PublicKey chave) {
        byte[] dectyptedText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Decriptografa o texto puro usando a chave Privada
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    /**
     *Método que retorna a chave privada
     * @return chavePrivada PrivateKey
     */
    public PrivateKey getChavePrivada() {
        return chavePrivada;
    }

    /**
     *Método que retorna uma chave publica
     * @return chavePublica PublicKey
     */
    public PublicKey getChavePublica() {
        return chavePublica;
    }

    /**
     * Método que gera uma Chave Publica
     * @param bytes
     * @return publicKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PublicKey carregaChavePublica(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException{
        PublicKey publicKey = 
        KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        return publicKey;
    }
    
    /**
     *  Método que gera uma chave privada
     * @param bytes
     * @return privateKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public PrivateKey carregaChavePrivate(byte[] bytes) throws NoSuchAlgorithmException, InvalidKeySpecException{
        PrivateKey privateKey = 
        KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(bytes));
        return  privateKey;
    }
}

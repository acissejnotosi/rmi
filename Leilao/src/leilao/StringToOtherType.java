/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.copel.distribuidos;

/**
 *
 * @author Jessica
 */
public class StringToOtherType {
    
    public byte[] chavePublicaGuest;
    public String nomeGuest;

    public byte[] getChavePublicaGuest() {
        return chavePublicaGuest;
    }

    public String getNomeGuest() {
        return nomeGuest;
    }
    
    
    public void stringToNomeChave(String mensagemIn)
    {
      //System.out.println(mensagemIn.indexOf(" ") );
      
      nomeGuest = mensagemIn.substring(0,mensagemIn.indexOf(" "));
      
      System.out.println("nome = "+ nomeGuest);
      
      chavePublicaGuest = (mensagemIn.substring((mensagemIn.indexOf(" "))+1)).getBytes();
      System.out.println("bytes =" + new String(chavePublicaGuest));
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = hexArray[v >>> 4];
            hexChars[j * 3 + 1] = hexArray[v & 0x0F];
            hexChars[j * 3 + 2] = ':';
        }
        return new String(hexChars);
    }
}

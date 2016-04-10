/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitcoins;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author samuel
 */
public class Keys {
    private PrivateKey priv;
    private PublicKey pub;
    
    public Keys () {
        try {
            // ********************************************
            // Initializing private and public RSA Keys
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);
            KeyPair pair = keyGen.generateKeyPair();
            priv = pair.getPrivate();
            pub = pair.getPublic();

        } catch (Exception e) {
            System.err.println("Caught exception: " + e.toString());
        }
    }
    
    // ********************************************
    // Return an encrypted text using the private key.
    public static byte[] encrypt(String text, PrivateKey priv) {
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA"); 
            cipher.init(Cipher.ENCRYPT_MODE, priv);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Encrypt problem " + e.getMessage());
        }
        
        return cipherText;
    }
    
    // ********************************************
    // Return an decrypted text using the private key.
    public static String decrypt(byte[] text, PublicKey pub) {
        byte[] dectyptedText = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pub);
            dectyptedText = cipher.doFinal(text);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Decrypt problem " + e.getMessage());
        }
        
        return new String(dectyptedText);
    }
    
    public PrivateKey getPriv() {
        return priv;
    }

    public PublicKey getPub() {
        return pub;
    }
}

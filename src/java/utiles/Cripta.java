/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author hyperaxolotl
 */
public class Cripta {
    private MessageDigest md;
    
    public Cripta(){
        inicio();
    }
    
    private void inicio() {
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException nisael) {
            nisael.printStackTrace();
        }
        
    }
    public String encripta(String contrasenia) {
        md.update(contrasenia.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}

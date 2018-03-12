package com.thor.tech.encryptiteen.utils;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {

    public static void main(String[] args) {

        try {
            //decrypt();
            Main.decryptFile("/home/skynet/mis_cosas/testDirEncryptiteen/outputEncripted/inputExamples/insopor.txt","/home/skynet/mis_cosas/testDirEncryptiteen/outPutDecripted/insoporAA.txt");
            System.out.println("Hello World!"); // Display the string
        } catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void decryptFile(String file, String dest) throws Exception {

        final DESKeySpec desKeySpec = new DESKeySpec("Thor!556".getBytes());
        final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey desKey = secretKeyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance ( "DES" );

        cipher.init(Cipher. DECRYPT_MODE , desKey );
        InputStream is = new FileInputStream(file);


        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte [] buffer = new byte [1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        try{
            cos.close();
        }catch(Exception e){
            cos = null;
            e.printStackTrace();
        }

        out.close();
        is.close();
    }





}



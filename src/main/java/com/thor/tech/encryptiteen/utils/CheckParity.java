package com.thor.tech.encryptiteen.utils;


import java.security.InvalidKeyException;
import java.util.Arrays;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.*;

public class CheckParity {

    static byte [] testKey = "Thor!555".getBytes();

    static byte [] expectedKey = "Thor!555".getBytes();

    static private void check(String alg, byte [] key,
                              byte [] expected, KeySpec ks) throws Exception {

        SecretKeyFactory skf = SecretKeyFactory.getInstance(alg, "SunJCE");
        SecretKey sk = skf.generateSecret(ks);

        if (DESKeySpec.isParityAdjusted(key, 0)) {
            throw new Exception("Initial Key is somehow parity adjusted!");
        }

        byte [] encoded = sk.getEncoded();
        if (!Arrays.equals(expected, encoded)) {
            throw new Exception("encoded key is not the expected key");
        }

        if (!DESKeySpec.isParityAdjusted(encoded, 0)) {
            throw new Exception("Generated Key is not parity adjusted");
        }
    }

    static private void checkDESKey() throws Exception {
        check("DES", testKey, expectedKey, new DESKeySpec(testKey));
    }

    static private void checkDESedeKey() throws Exception {

        byte [] key3 = new byte [testKey.length * 3];
        byte [] expectedKey3 = new byte [expectedKey.length * 3];

        for (int i = 0; i < 3; i++) {
            System.arraycopy(testKey, 0, key3,
                    i * testKey.length, testKey.length);
            System.arraycopy(expectedKey, 0, expectedKey3,
                    i * testKey.length, testKey.length);
        }

        check("DESede", key3, expectedKey3, new DESedeKeySpec(key3));
    }

    public static void main(String[] args) throws Exception {
        checkDESKey();
        checkDESedeKey();
        System.out.println("Test Passed!");
    }
}
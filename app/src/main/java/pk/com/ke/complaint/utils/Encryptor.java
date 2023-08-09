package pk.com.ke.complaint.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Hp on 5/16/2017.
 */

public class Encryptor {
    public static String KEY_PC = "7831293847120498";
    public static String IV_PC = "7831293847120498";

    public static String KEY_ITHD = "9090909090909090";
    public static String IV_ITHD = "9090909090909090";

    public static String encrypt(String value, String Key, String IV) {
        try {
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(Key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            String encryptedString = android.util.Base64.encodeToString(encrypted, android.util.Base64.DEFAULT);

//            Log.e("ES", "" + encryptedString);

            if (encryptedString.contains("\n")) {
                encryptedString = encryptedString.replaceAll("\n", "");
            }

            return encryptedString;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(IV_PC.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(KEY_PC.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(android.util.Base64.decode(encrypted.getBytes(), android.util.Base64.DEFAULT));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String encrypted, String keyPc, String ivPc) {
        try {
            IvParameterSpec iv = new IvParameterSpec(keyPc.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(ivPc.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(android.util.Base64.decode(encrypted.getBytes(), android.util.Base64.DEFAULT));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

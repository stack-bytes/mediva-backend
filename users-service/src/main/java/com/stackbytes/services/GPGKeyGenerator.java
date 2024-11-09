package com.stackbytes.services;

import jakarta.annotation.PostConstruct;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Configuration
public class GPGKeyGenerator {
    @Autowired
    private GetProperties getProperties;
    @PostConstruct
    public void init() {
        BouncyCastleSetup.setup();
    }

    private SecretKey generateAESKeyFromInput(String input) throws Exception {
        // Convert the input string to a byte array (use it as salt)
        byte[] salt = Base64.getDecoder().decode(input);
        String secret = getProperties.getProperties("jwt.secret");
        // Define parameters for PBKDF2
        String password = secret; // Use a fixed password or other secret input
        int iterationCount = 65536;  // Number of PBKDF2 iterations
        int keyLength = 256;  // AES-256

        // Derive the AES key using PBKDF2
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = keyFactory.generateSecret(spec).getEncoded();

        // Convert derived key bytes to AES SecretKey
        return new SecretKeySpec(keyBytes, "AES");
    }

    public HashMap<String, String> encryptPrivateKeyWithAES(String privateKey, PublicKey rsaPublicKey) throws Exception {
        // Generate a random AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);  // AES-256
        SecretKey aesKey = generateAESKeyFromInput(getProperties.getProperties("aes_key"));

        // Encrypt the private key with AES
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedPrivateKey = aesCipher.doFinal(privateKey.getBytes(StandardCharsets.UTF_8));

//        // Encrypt the AES key with RSA
//        Cipher rsaCipher = Cipher.getInstance("RSA");
//        rsaCipher.init(Cipher.WRAP_MODE, rsaPublicKey);
//        byte[] encryptedAESKey = rsaCipher.wrap(aesKey);

        // Encode both encrypted AES key and private key as Base64 strings
        HashMap<String, String> encryptedKeys = new HashMap<>();
        encryptedKeys.put("encryptedPrivateKey", Base64.getEncoder().encodeToString(encryptedPrivateKey));
        encryptedKeys.put("AESKey", Base64.getEncoder().encodeToString(aesKey.getEncoded()));
        return encryptedKeys;
    }

//    private String encryptBase64String(String base64String, PublicKey publicKey) throws Exception {
//        byte[] dataToEncrypt = Base64.getDecoder().decode(base64String);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] encryptedData = cipher.doFinal(dataToEncrypt);
//        return Base64.getEncoder().encodeToString(encryptedData);
//    }
    public HashMap<String, String> generateKey(String identity, String passphrase) {
        try {
            HashMap<String, String> keyPair = new HashMap<>();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, new SecureRandom());
            KeyPair keyPairRSA = keyPairGenerator.generateKeyPair();
            PGPKeyPair pgpKeyPair = new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, keyPairRSA, new Date());
            PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);
            PBESecretKeyEncryptor secretKeyEncryptor = new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256, sha1Calc).setProvider("BC").build(passphrase.toCharArray());
            PGPKeyRingGenerator keyRingGen = new PGPKeyRingGenerator(PGPSignature.POSITIVE_CERTIFICATION, pgpKeyPair, identity, sha1Calc, null, null, new JcaPGPContentSignerBuilder(pgpKeyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1), secretKeyEncryptor);
            String initialPublicKey = keyRingGen.generatePublicKeyRing().getEncoded().toString();
            String publicKey = Base64.getEncoder().encodeToString(initialPublicKey.getBytes());
            String privateKey = Base64.getEncoder().encodeToString(keyRingGen.generateSecretKeyRing().getEncoded());
            HashMap<String,String> encryptedPrivateKey = encryptPrivateKeyWithAES(privateKey, keyPairRSA.getPublic());
            keyPair.put("privateKey", encryptedPrivateKey.get("encryptedPrivateKey"));
            keyPair.put("AESKey", encryptedPrivateKey.get("AESKey"));
            keyPair.put("publicKey", publicKey);
            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

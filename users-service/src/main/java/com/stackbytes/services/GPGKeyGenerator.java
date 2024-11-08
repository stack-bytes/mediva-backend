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
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Configuration
public class GPGKeyGenerator {
    @PostConstruct
    public void init() {
        BouncyCastleSetup.setup();
    }
    private String encryptBase64String(String base64String, PublicKey publicKey) throws Exception {
        byte[] dataToEncrypt = Base64.getDecoder().decode(base64String);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(dataToEncrypt);
        return Base64.getEncoder().encodeToString(encryptedData);
    }
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
            String encryptedPrivateKey = encryptBase64String(privateKey, keyPairRSA.getPublic());
            keyPair.put("privateKey", encryptedPrivateKey);
            keyPair.put("publicKey", publicKey);
            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.stackbytes.services;

import jakarta.annotation.PostConstruct;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;

import java.io.ByteArrayOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Date;

@Component
public class GPGKeyGenerator2 {
    @Autowired
    private  GetProperties getProperties;
    @PostConstruct
    public void init() {
        BouncyCastleSetup.setup();
    }

    public PGPKeyRingGenerator generateKey(String id) throws Exception{
        String passphrase = getProperties.getProperties("gpg.passphrase");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair rsaKeyPair = keyPairGenerator.generateKeyPair();
        PGPKeyPair pgpKeyPair = new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, rsaKeyPair, new Date());
        PGPDigestCalculator sha256Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(PGPUtil.SHA1);
        PBESecretKeyEncryptor keyEncryptor = new JcePBESecretKeyEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256, sha256Calc)
                .setProvider("BC")
                .build(passphrase.toCharArray());
        PGPContentSignerBuilder contentSignerBuilder = new JcaPGPContentSignerBuilder(pgpKeyPair.getPublicKey().getAlgorithm(), PGPUtil.SHA1)
                .setProvider("BC");
        return new PGPKeyRingGenerator(
                PGPSignature.POSITIVE_CERTIFICATION,
                pgpKeyPair,
                id,
                sha256Calc,
                null,
                null,
                contentSignerBuilder,
                keyEncryptor
        );
    }
    public byte[] getPublicKeyBytes(PGPKeyRingGenerator keyRingGenerator) throws Exception {
        PGPPublicKeyRing publicKeyRing = keyRingGenerator.generatePublicKeyRing();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        publicKeyRing.encode(out);
        return out.toByteArray();
    }
    public byte[] getPrivateKeyBytes(PGPKeyRingGenerator keyRingGenerator) throws Exception {
        PGPSecretKeyRing secretKeyRing = keyRingGenerator.generateSecretKeyRing();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        secretKeyRing.encode(out);
        return out.toByteArray();
    }
}

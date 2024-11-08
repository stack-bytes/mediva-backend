package com.stackbytes.services;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class BouncyCastleSetup {
    public static void setup() {
        Security.addProvider(new BouncyCastleProvider());
    }
}

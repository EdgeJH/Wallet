package com.edge.wallet;

import java.math.BigInteger;

public interface WalletGenerateListener{
        void  generate(String address, BigInteger privateKey, BigInteger publicKey);
    }
package com.edge.wallet;

import android.os.Environment;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;

/**
 * Created by user1 on 2018-06-15.
 */

public class EthWalletModel {

    WalletGenerateListener generateListener;

    public EthWalletModel(WalletGenerateListener generateListener) {
        this.generateListener = generateListener;
    }

    public void generateWallet(String password){
        String fileName;
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!path.exists()) {
                path.mkdir();
            }

            fileName = WalletUtils.generateLightNewWalletFile(password, new File(String.valueOf(path)));
            
            Credentials credentials =
                    WalletUtils.loadCredentials(
                            password,
                            path + "/" + fileName);
            generateListener.generate(credentials.getAddress(),credentials.getEcKeyPair().getPrivateKey(),credentials.getEcKeyPair().getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

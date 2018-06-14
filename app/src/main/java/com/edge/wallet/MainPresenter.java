package com.edge.wallet;

import java.math.BigInteger;

/**
 * Created by user1 on 2018-06-15.
 */

public class MainPresenter implements MainTask.PresenterBridge,WalletGenerateListener{

    MainTask.View view;
    EthWalletModel ethWalletModel;

    public MainPresenter(MainTask.View view) {
        this.view = view;
        view.setPresenterBridge(this);
    }

    @Override
    public void generate(String address, BigInteger privateKey, BigInteger publicKey) {
        view.completeListener(address,privateKey,publicKey);
    }

    @Override
    public void start() {

    }

    @Override
    public void generateWallet(String password) {
        ethWalletModel = new EthWalletModel(this);
        ethWalletModel.generateWallet(password);
    }
}

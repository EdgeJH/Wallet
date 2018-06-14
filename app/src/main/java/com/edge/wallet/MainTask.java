package com.edge.wallet;

import java.math.BigInteger;

/**
 * Created by user1 on 2018-06-15.
 */

public interface MainTask {
    interface PresenterBridge extends BasePresenter{
        void generateWallet(String password);
    }
    interface View extends BaseView<PresenterBridge>{
        void completeListener(String address, BigInteger privateKey,BigInteger publicKey);
    }
}

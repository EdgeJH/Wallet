package com.edge.wallet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edge.wallet.Utils.LoadingProgress;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity implements MainTask.View, View.OnClickListener {

    TextView myEthAddress,myPrivateKey;
    EditText password;
    Button generateBt;
    MainTask.PresenterBridge presenterBridge;
    MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        initView();
    }

    public void initView(){
        myEthAddress = findViewById(R.id.address);
        password = findViewById(R.id.password);
        generateBt = findViewById(R.id.generate_bt);
        myPrivateKey = findViewById(R.id.private_key);
        generateBt.setOnClickListener(this);
    }

    @Override
    public void setPresenterBridge(MainTask.PresenterBridge presenterBridge) {
        this.presenterBridge= presenterBridge;
    }

    @Override
    public void completeListener(String address, BigInteger privateKey, BigInteger publicKey) {
        LoadingProgress.dismissDialog();
        myEthAddress.setText(address);
        String priKey = privateKey.toString();
        myPrivateKey.setText(priKey);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.generate_bt:
               if (checkPermission()){
                   generateWallet();
               }

        }
    }
    private void generateWallet(){
        LoadingProgress.showDialog(this,true);
        String pwd = password.getText().toString();
        if (!TextUtils.isEmpty(pwd)){
            presenterBridge.generateWallet(pwd);
        } else {
            Toast.makeText(this,"비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>1&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
            generateWallet();
        }
    }
}

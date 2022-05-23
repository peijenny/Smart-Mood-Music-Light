package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class LoginActivity extends Activity {

    //device var
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private InputStream inStream = null;

    //這是藍芽串口通用的UUID，不要更改
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "3C:71:BF:C7:FA:66"; // <== 要連接的目標藍芽設備MAC地址
    private ReceiveThread rThread=null;  //數據接收線程

    //接收到的字符串
    String ReceiveData="";
    MyHandler handler;

    //宣告元件
    private EditText edtAcc,edtPass;
    private Button btnLogin, btnRegister, btnForgotpassword;
    private CheckBox login_check1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //首先調用初始化函數(藍芽)
        InitBluetooth();

        handler = new MyHandler();

        if(!mBluetoothAdapter.isEnabled())
        {
            mBluetoothAdapter.enable();
        }
        mBluetoothAdapter.startDiscovery();




        //元件初始化
        edtAcc = findViewById(R.id.editText);
        edtPass = findViewById(R.id.editText2);

        btnLogin = findViewById(R.id.button1);
        btnRegister = findViewById(R.id.button2);
        btnForgotpassword = findViewById(R.id.button3);

        btnLogin.setOnClickListener(myListener);
        btnRegister.setOnClickListener(myListener);
        btnForgotpassword.setOnClickListener(myListener);

        login_check1 = findViewById(R.id.checkBox);  //紀錄使用帳密的checkButton

        //SharedPreferences將name 和 pass 記錄起來 每次進去軟體時 開始從中讀取資料 放入login_name，login_password中
        SharedPreferences remdname=getPreferences(MainActivity.MODE_PRIVATE);
        String name_str=remdname.getString("name", "");
        String pass_str=remdname.getString("pass", "");
        edtAcc.setText(name_str);
        edtPass.setText(pass_str);

        login_check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){    //當紀錄帳密的checkButton被勾選時，則紀錄使用者的帳號密碼
                    SharedPreferences remdname=getPreferences(MainActivity.MODE_PRIVATE);
                    SharedPreferences.Editor edit=remdname.edit();
                    edit.putString("name", edtAcc.getText().toString());
                    edit.putString("pass", edtPass.getText().toString());
                    edit.commit();
                }
                if(!isChecked){   //當紀錄帳密的checkButton被取消勾選時，則紀錄使用者的帳號密碼
                    SharedPreferences remdname=getPreferences(MainActivity.MODE_PRIVATE);
                    SharedPreferences.Editor edit=remdname.edit();
                    edit.putString("name", "");
                    edit.putString("pass", "");
                    edit.commit();
                }
            }
        });

    }
    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final ProgressDialog dialog2 = ProgressDialog.show(LoginActivity.this,
                    "載入中", "請稍等...", true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        dialog2.dismiss();
                    }
                }
            }).start();

            Intent intent = new Intent();
            switch(v.getId()) {
                case R.id.button1:   //btn登入
                    //在這寫登錄後的事件內容
                    if(login_check1.isChecked()){  //檢測使用者名密碼
                        SharedPreferences remdname=getPreferences(MainActivity.MODE_PRIVATE);
                        SharedPreferences.Editor edit=remdname.edit();
                        edit.putString("name", edtAcc.getText().toString());
                        edit.putString("pass", edtPass.getText().toString());
                        edit.commit();
                    }

                    //判斷使用者是否輸入正確的帳密
                    if(!edtAcc.getText().toString().equals("") && !edtPass.getText().toString().equals("")) {
                        String page = "Login";    //登入頁
                        String account = edtAcc.getText().toString();
                        String password = edtPass.getText().toString();

                        sqlConnect sc = new sqlConnect(LoginActivity.this);  //sql連線
                        sc.execute(page, account, password);  //將page、account、password，帶到sqlConnect
                    } else {
                        Toast.makeText(LoginActivity.this,"請輸入帳號與密碼!!!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.button2:  //btn註冊
                    intent.setClass(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;

                case R.id.button3:  //btn忘記密碼
                    intent.setClass(LoginActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
                    break;


            }
        }
    };




    public void InitBluetooth() {    //初始化藍芽

        //得到一個藍芽適配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
            Toast.makeText(this, "你的手機不支援藍芽", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {    //藍芽連接
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //從藍芽接收信息的線程
    class ReceiveThread extends Thread
    {
        String buffer="";
        @Override
        public void run() {
            while(btSocket!=null ) {
                //定義一個儲存空間buff
                byte[] buff=new byte[1024];
                try {
                    inStream = btSocket.getInputStream();
                    System.out.println("waitting for instream");
                    inStream.read(buff); //讀取數據儲存在buff數組中
                    //System.out.println("buff receive :"+buff.length);
                    processBuffer(buff,1024);
                    //System.out.println("receive content:"+ReceiveData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processBuffer(byte[] buff,int size) {
            int length=0;
            for(int i=0;i<size;i++) {
                if(buff[i]>'\0') {
                    length++;
                }
                else {
                    break;
                }
            }

            //System.out.println("receive fragment size:"+length);

            byte[] newbuff=new byte[length];  //newbuff字節數據組，用於存放真正接收到的數據

            for(int j=0;j<length;j++) {
                newbuff[j]=buff[j];
            }

            ReceiveData = ReceiveData + new String(newbuff);
            Log.e("Data",ReceiveData);
            //System.out.println("result :"+ReceiveData);
            Message msg=Message.obtain();
            msg.what=1;
            handler.sendMessage(msg);  //發送消息:系統會自動調用handleMessage( )方法來處理消息

        }

    }


    //更新介面的Handler類
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    //etReceived.setText(ReceiveData);
                    break;
            }
        }
    }

    //切斷藍芽連接
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        try {
            if(rThread!=null) {
                btSocket.close();
                btSocket=null;
                rThread.join();
            }
            this.finish();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class SelectActivity extends Activity {

    int i = 0;
    private TextView tv1;
    private ImageButton btnGolife,btnManual,btnModify, btnQAMessager, btnHeartAnalysis, btnUserInfo;
    //private ImageButton btnHrSetting;
    private ImageButton btnRevision;
    String account;
    String actText = "";
    int voiceText = 20, lightText = 10;

    //藍芽元件
    TextView statusLabel;
    ImageButton btnConnect;
    ImageButton btnStop, btnPlay, btnCancel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //元件初始化
        btnGolife = findViewById(R.id.imageButton7);
        btnManual = findViewById(R.id.imageButton8);
        btnModify = findViewById(R.id.imageButton9);
        btnUserInfo = findViewById(R.id.imageButton13);
        //btnHrSetting = findViewById(R.id.imageButton13);
        btnHeartAnalysis = findViewById(R.id.imageButton11);
        btnQAMessager = findViewById(R.id.imageButton12);
        //btnUserInfo = findViewById(R.id.imageButton18);

        btnGolife.setOnClickListener(myListener);
        btnManual.setOnClickListener(myListener);
        btnModify.setOnClickListener(myListener);
        //btnHrSetting.setOnClickListener(myListener);
        btnHeartAnalysis.setOnClickListener(myListener);
        btnQAMessager.setOnClickListener(myListener);
        btnUserInfo.setOnClickListener(myListener);

        btnRevision = findViewById(R.id.imageButton27);
        btnRevision.setOnClickListener(myMusicListener);

        //從登入帶 user --> account 到主選單
        tv1 = findViewById(R.id.textView14);
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");
        //tv1.setText(account + " ,歡迎您!");
        //tv1.setText(account + ", 歡迎您!");



        //首先調用初始化函數(藍芽)
        Init();
        InitBluetooth();

        handler=new MyHandler();

        if(!mBluetoothAdapter.isEnabled())
        {
            mBluetoothAdapter.enable();
        }
        mBluetoothAdapter.startDiscovery();

        //創建連接
        new ConnectTask().execute(address);

        //statusLabel.setText("連線狀況");


        //---藍芽連接---
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitConnect();

                //判斷藍芽是否打開
                if(!mBluetoothAdapter.isEnabled())
                {
                    mBluetoothAdapter.enable();
                }
                mBluetoothAdapter.startDiscovery();
                //創建連接
                new ConnectTask().execute(address);
                actText = "";
            }
        });


    }

    Button.OnClickListener myMusicListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if(statusLabel.getText().toString().equals("音樂燈連接成功!")) {
                switch (v.getId())
                {
                    case R.id.imageButton4:  //Cancel
                            //傳送字串到檯燈
                            // TODO Auto-generated method stub
                            new SendInfoTask().execute("cancel");
                        break;
                    case R.id.imageButton5:  //Stop
                            //傳送字串到檯燈
                            // TODO Auto-generated method stub
                            new SendInfoTask().execute("stop");
                        break;
                    case R.id.imageButton6:  //Play
                            //傳送字串到檯燈
                            // TODO Auto-generated method stub
                            new SendInfoTask().execute("play");
                        break;
                    case R.id.imageButton27:
                        ShowDialog();
                        break;
                }

                quitConnect();

                //判斷藍芽是否打開
                if(!mBluetoothAdapter.isEnabled())
                {
                    mBluetoothAdapter.enable();
                }
                mBluetoothAdapter.startDiscovery();
                //創建連接
                new ConnectTask().execute(address);
                actText = "";
            }
            else{
                //new android.support.v7.app.AlertDialog.Builder(SelectActivity.this).setMessage("請確認音樂燈是否在附近!").show();

                quitConnect();

                //判斷藍芽是否打開
                if(!mBluetoothAdapter.isEnabled())
                {
                    mBluetoothAdapter.enable();
                }
                mBluetoothAdapter.startDiscovery();
                //創建連接
                new ConnectTask().execute(address);
                actText = "";


                /*
                final ProgressDialog dialog2 = ProgressDialog.show(SelectActivity.this,
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
                */

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(statusLabel.getText().toString().equals("音樂燈連接成功!")) {
                            switch (v.getId()) {
                                case R.id.imageButton4:  //Cancel
                                    //傳送字串到檯燈
                                    // TODO Auto-generated method stub
                                    new SendInfoTask().execute("cancel");
                                    break;
                                case R.id.imageButton5:  //Stop
                                    //傳送字串到檯燈
                                    // TODO Auto-generated method stub
                                    new SendInfoTask().execute("stop");
                                    break;
                                case R.id.imageButton6:  //Play
                                    //傳送字串到檯燈
                                    // TODO Auto-generated method stub
                                    new SendInfoTask().execute("play");
                                    break;
                                case R.id.imageButton27:
                                    ShowDialog();
                                    break;
                            }
                        }
                        else{
                            new android.support.v7.app.AlertDialog.Builder(SelectActivity.this).setMessage("請確認音樂燈是否在附近!").show();
                        }

                    }
                },3000); // 延时5秒


            }




        }
    };

    public void quitConnect() {
        if(btSocket!=null) {
            try {
                btSocket.close();
                btSocket=null;
                if(rThread!=null) {
                    rThread.join();
                }
                statusLabel.setText("重新進行連接...");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    ImageButton.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ProgressDialog dialog2 = ProgressDialog.show(SelectActivity.this,
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
            Bundle bundle = new Bundle();


            switch(v.getId()) {
                case R.id.imageButton7:
                    sqlGetValue sv = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv.execute("MainWatch", account);  //連到sqlGetValue，select standard
                    break;
                case R.id.imageButton8:
                    sqlGetValue sv2 = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv2.execute("Manual", account);  //連到sqlGetValue，select standard
                    break;
                case R.id.imageButton9:
                    sqlGetValue sv3 = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv3.execute("HrSetting", account);  //連到sqlGetValue，select standard && setting
                    break;
                case R.id.imageButton13:
                    sqlGetValue sv4 = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv4.execute("UserInfo", account);  //連到sqlGetValue，select standard && setting
                    //sv4.execute("Modify", account);  //連到sqlGetValue，select standard && setting
                    break;
                case R.id.imageButton11:
                    sqlGetValue sv5 = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv5.execute("Knowledge", account);  //連到sqlGetValue，select knowledge
                    break;
                case R.id.imageButton12:
                    sqlGetValue sv6 = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv6.execute("HeartAnalysis", account);  //連到sqlGetValue，select heartAnalysis && setting
                    break;
                /*case R.id.imageButton18:
                    sqlGetValue sv7 = new sqlGetValue(SelectActivity.this);  //sql連線
                    sv7.execute("UserInfo", account);  //連到sqlGetValue，select user
                    break;*/
            }
        }
    };

    public void ShowDialog()
    {

        final String userid1 = getPreferences(MainActivity.MODE_PRIVATE).getString("voice", "");
        final String userid2 = getPreferences(MainActivity.MODE_PRIVATE).getString("light", "");

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View Viewlayout = inflater.inflate(R.layout.activity_dialog,
                (ViewGroup) findViewById(R.id.layout_dialog));

        final TextView item1 = (TextView)Viewlayout.findViewById(R.id.txtItem1); // txtItem1
        final TextView item2 = (TextView)Viewlayout.findViewById(R.id.txtItem2); // txtItem2


        item1.setText(userid1);
        item2.setText(userid2);


        //voice
        //light
        popDialog.setIcon(android.R.drawable.ic_menu_mylocation);
        popDialog.setTitle("調整音量與燈光大小");
        //popDialog.setMessage("1為音量/燈光最微弱!\n10為音量/燈光最強烈!" + "\n");
        popDialog.setView(Viewlayout);


        //  seekBar1
        final SeekBar seek1 = (SeekBar) Viewlayout.findViewById(R.id.seekBar1);
        //  seekBar2
        final SeekBar seek2 = (SeekBar) Viewlayout.findViewById(R.id.seekBar2);


        //Toast.makeText(SelectActivity.this, i + "", Toast.LENGTH_SHORT).show();
        if(i == 0){
            item1.setText("音量大小：" + 20);
            item2.setText("燈光強弱：" + 10);
            i = i + 1;
        }
        else if(i > 0){
            item1.setText(userid1);
            item2.setText(userid2);
            i = i + 1;
        }

        seek1.setProgress((voiceText-5) * 4);
        seek2.setProgress((lightText-1) * 11);


        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value

                voiceText = (progress/4) + 5;
                item1.setText("音量大小：" + voiceText);
                //item1.setText("音量大小 : " + progress);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });


        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value
                lightText = (progress/11) + 1;
                item2.setText("燈光強弱：" + lightText);
                //item2.setText("燈光強弱 : " + progress);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });


        // Button OK
        popDialog.setPositiveButton("確認",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                        SharedPreferences remdname=getPreferences(SelectActivity.MODE_PRIVATE);
                        SharedPreferences.Editor edit=remdname.edit();
                        edit.putString("voice", item1.getText().toString());
                        edit.putString("light", item2.getText().toString());
                        edit.commit();

                        /*
                        SharedPreferences remdname=getPreferences(MainActivity.MODE_PRIVATE);
                        SharedPreferences.Editor edit=remdname.edit();
                        edit.putString("voice", item1.getText().toString());
                        edit.putString("light", item2.getText().toString());
                        edit.commit();*/

                        //傳送字串到檯燈
                        // TODO Auto-generated method stub
                        new SendInfoTask().execute("voice " + voiceText + " " + "light " + lightText);

                        /*
                        if(statusLabel.getText().toString().equals("音樂燈連接成功!")) {
                            //傳送字串到檯燈
                            // TODO Auto-generated method stub
                            new SendInfoTask().execute("voice " + voiceText + " " + "light " + lightText);
                        }
                        else{
                            new android.support.v7.app.AlertDialog.Builder(SelectActivity.this).setMessage("請先連接檯燈!").show();
                        }*/
                    }

                });

        popDialog.create();
        popDialog.show();

    }



    public void Init() {    //初始化元件 --> 藍芽連接
        statusLabel=(TextView)this.findViewById(R.id.textView13);
        btnConnect=(ImageButton)this.findViewById(R.id.imageButton30);
        btnStop=(ImageButton)this.findViewById(R.id.imageButton5);   //2
        btnPlay=(ImageButton)this.findViewById(R.id.imageButton6);  //3
        btnCancel=(ImageButton)this.findViewById(R.id.imageButton4); //1
        //訊息發送
        btnStop.setOnClickListener(myMusicListener);
        btnPlay.setOnClickListener(myMusicListener);
        btnCancel.setOnClickListener(myMusicListener);
    }

    public void InitBluetooth(){    //初始化藍芽
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

    //連接藍芽設備的異步任務
    class ConnectTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(params[0]);

            try {
                btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                btSocket.connect();
                Log.e("error", "ON RESUME: BT connection established, data transfer link open.");

            } catch (IOException e) {
                try {
                    btSocket.close();
                    //return "Socket 創建失敗";
                    return "連接失敗，請重新連接!";
                } catch (IOException e2) {
                    Log .e("error","ON RESUME: Unable to close socket during connection failure", e2);
                    //return "Socket 關閉失敗";
                    return "連接失敗，請重新連接!";
                }
            }

            //取消搜索
            mBluetoothAdapter.cancelDiscovery();
            try {
                outStream = btSocket.getOutputStream();
            } catch (IOException e) {
                Log.e("error", "ON RESUME: Output stream creation failed.", e);
                //return "Socket 創建失敗";
                return "連接失敗，請重新連接!";
            }

            //return "藍芽連接正常,Socket 創建成功";
            return "音樂燈連接成功!";
        }

        @Override    //這個方法是在主線程中運行的，所以可以更新介面
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            //連接成功則啟動監聽
            rThread=new ReceiveThread();
            rThread.start();
            statusLabel.setText(result);
            super.onPostExecute(result);
        }

    }

    //發送數據到藍芽設備的異步任務
    class SendInfoTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            statusLabel.setText(result);
            //將發送框清空
            //etSend.setText("");
        }

        @Override
        protected String doInBackground(String... arg0) {   //發送訊息
            // TODO Auto-generated method stub

            if(btSocket==null) {
                return "還沒有建立連接";
            }

            if(arg0[0].length()>0){  //不是空白串
                //String target=arg0[0];
                byte[] msgBuffer = arg0[0].getBytes();
                try {
                    //  將msgBuffer中的數據寫到outStream對象中
                    outStream.write(msgBuffer);
                } catch (IOException e) {
                    Log.e("error", "ON RESUME: Exception during write.", e);
                    return "發送失敗";
                }
            }
            return "發送成功";
        }

    }

    //從藍芽接收信息的線程
    class ReceiveThread extends Thread {
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

            ReceiveData=ReceiveData+new String(newbuff);
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

    //關閉藍芽連接
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

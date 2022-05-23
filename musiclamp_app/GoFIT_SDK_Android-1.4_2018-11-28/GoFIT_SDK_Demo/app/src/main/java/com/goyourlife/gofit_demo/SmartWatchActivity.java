package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class SmartWatchActivity extends Activity {

    String lightColor = "暖橘燈光";
    private TextView tvHr, tvMode, tvType, tvMusicName, tvStartDate, tvStartTime;
    //private TextView tvlightName;
    private ImageView imgLight;
    private ImageButton btnGoBack;
    private Button btnCheck;
    String page = "SmartWatch";
    String hrString, account, value;
    String modeText, typeText, musicText, lightText, hrText;
    String startDateText, startTimeText;
    ArrayList<String> standardMode = new ArrayList<String>(), modeType = new ArrayList<String>(), musicChiName = new ArrayList<String>(), musicEngName = new ArrayList<String>(), musicFile = new ArrayList<String>();
    ArrayList<String> lightName = new ArrayList<String>(), lightRGB = new ArrayList<String>();

    //藍芽元件
    TextView statusLabel;
    ImageButton btnConnect;
    String sendText , sendDate, sendTime;

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
        setContentView(R.layout.activity_smartwatch);

        //元件初始化
        tvHr = findViewById(R.id.textView24);
        tvMode = findViewById(R.id.textView23);
        tvType = findViewById(R.id.textView26);
        tvMusicName = findViewById(R.id.textView28);
        //tvlightName = findViewById(R.id.textView30);
        tvStartDate = findViewById(R.id.textView32);
        tvStartTime = findViewById(R.id.textView33);

        btnGoBack = findViewById(R.id.imageButton2);
        btnCheck = findViewById(R.id.button);
        btnGoBack.setOnClickListener(myListener);
        btnCheck.setOnClickListener(myListener);

        imgLight = findViewById(R.id.imageView7);

        //從 智慧手環SDK 帶過來的 心跳值 & account
        Bundle bundle = getIntent().getExtras();
        hrString = bundle.getString("hrString");
        account = bundle.getString("account");
        value = bundle.getString("value");


        //standard 資料表
        try{
            //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                standardMode.add(jsonObject.getString("mode"));
                modeType.add(jsonObject.getString("mode_type"));
                musicChiName.add(jsonObject.getString("music_chiname"));
                musicEngName.add(jsonObject.getString("music_engname"));
                musicFile.add(jsonObject.getString("music_file"));

                lightName.add(jsonObject.getString("light_name"));
                lightRGB.add(jsonObject.getString("light_rgb"));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        String[] spt = hrString.split("　");

        int hrAvg = 0;
        for(int j = 0; j < spt.length; j++) {    //取心跳值
            String[] spt2 = spt[j].split(":");
            String[] spt3 = spt2[7].split(",");
            hrAvg = hrAvg + Integer.valueOf(spt3[0]);
        }
        hrAvg = hrAvg / spt.length;

        tvHr.setText("平均心跳 : " + hrAvg);
        hrText = String.valueOf(hrAvg);

        //顯示日期與時間
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //判斷日期與時間是否需要補0
        if(month < 10) {
            if(day < 10) {
                tvStartDate.setText(String.valueOf(year) + "/" + "0" + String.valueOf(month) + "/" + "0" + String.valueOf(day));
                startDateText = tvStartDate.getText().toString();
                sendDate = String.valueOf(year) +  "0" + String.valueOf(month) + "0" + String.valueOf(day);
            }
            else {
                tvStartDate.setText(String.valueOf(year) + "/" + "0" + String.valueOf(month) + "/" + String.valueOf(day));
                startDateText = tvStartDate.getText().toString();
                sendDate = String.valueOf(year) +  "0" + String.valueOf(month) + String.valueOf(day);
            }
        }
        else {
            if(day < 10) {
                tvStartDate.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + "0" + String.valueOf(day));
                startDateText = tvStartDate.getText().toString();
                sendDate = String.valueOf(year) + String.valueOf(month) + "0" + String.valueOf(day);
            }
            else {
                tvStartDate.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day));
                startDateText = tvStartDate.getText().toString();
                sendDate = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
            }
        }

        if(hour < 10) {
            if(minute < 10) {
                tvStartTime.setText("0" + String.valueOf(hour) + ":" + "0" + String.valueOf(minute));
                startTimeText = tvStartTime.getText().toString();
                sendTime = "0" + String.valueOf(hour) + "0" + String.valueOf(minute);
            }
            else {
                tvStartTime.setText("0" + String.valueOf(hour) + ":" + String.valueOf(minute));
                startTimeText = tvStartTime.getText().toString();
                sendTime = "0" + String.valueOf(hour) + String.valueOf(minute);
            }
        }
        else {
            if(minute < 10) {
                tvStartTime.setText(String.valueOf(hour) + ":" + "0" + String.valueOf(minute));
                startTimeText = tvStartTime.getText().toString();
                sendTime =  String.valueOf(hour) + "0" + String.valueOf(minute);
            }
            else {
                tvStartTime.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                startTimeText = tvStartTime.getText().toString();
                sendTime = String.valueOf(hour) + String.valueOf(minute);
            }
        }

        //設定心跳範圍值
        if(hrAvg > 60 && hrAvg < 100) {
            tvMode.setText("平靜");
            modeText = "calm";
            lightColor = "淡藍燈光";
        }
        else if(hrAvg <= 60) {
            tvMode.setText("低落");
            modeText = "low";
            lightColor = "暖橘燈光";

        }
        else if(hrAvg >= 100) {
            tvMode.setText("激動");
            modeText = "excitement";
            lightColor = "粉紅燈光";
        }


        //可以透過亂數，在每次傳送智慧手環設定時，所呈現的歌曲則會有所不同
        /*int random = (int)(Math.random()* 2);
        if(random == 0) {
            tvType.setText("預設");
            typeText = "preset";
        }
        if(random == 1) {
            tvType.setText("個人");
            typeText = "self";
        }*/

        tvType.setText("預設");
        typeText = "preset";

        ArrayList<String> NameList = new ArrayList<String>();  //取音樂的中文曲名
        ArrayList<String> FileList = new ArrayList<String>();  //取音樂的檔案編號

        for(int k = 0; k < standardMode.size(); k++) {
            if(standardMode.get(k).equals(modeText) && modeType.get(k).equals(typeText)) {
                NameList.add(musicChiName.get(k));
                FileList.add(musicFile.get(k));

                if(modeText == "calm")
                {
                    imgLight.setImageResource(R.drawable.light_icon3);
                    //圖片 light_icon3
                }
                if(modeText == "low")
                {
                    imgLight.setImageResource(R.drawable.light_icon2);
                    //圖片 light_icon2
                }
                if(modeText == "excitement")
                {
                    imgLight.setImageResource(R.drawable.light_icon4);
                    //圖片 light_icon4
                }


                //取燈光的RGB，顯示在畫面上
                String[] colorSplit = lightRGB.get(k).split(",");
                int x = Integer.parseInt(colorSplit[0]);
                int y = Integer.parseInt(colorSplit[1]);
                int z = Integer.parseInt(colorSplit[2]);
                //tvlightName.setBackgroundColor(Color.rgb(x, y, z));
                //tvlightName.setText(lightColor);
                lightText = lightRGB.get(k);


            }
        }
        tvMusicName.setText(NameList.get(0) + "");
        musicText = FileList.get(0);


        //首先調用初始化涵數
        Init();
        InitBluetooth();

        handler=new MyHandler();
        if(!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        mBluetoothAdapter.startDiscovery();
        //創建連接
        new ConnectTask().execute(address);


        //---藍芽連接---
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitConnect();

                //判斷藍芽是否打開
                if(!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                }
                mBluetoothAdapter.startDiscovery();
                //創建連接
                new ConnectTask().execute(address);
            }
        });

    }

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
            switch (v.getId())
            {
                case R.id.imageButton2:  //btn 回主選單
                    //quitConnect();

                    Intent intent = new Intent();
                    intent.setClass(SmartWatchActivity.this,SelectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("account",account);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.button:  //btn 確認設定
                    sendText = musicText + " {" + lightText + "} " + sendDate + " " + sendTime;  //傳送給檯燈的字串

                    new AlertDialog.Builder(SmartWatchActivity.this)
                            .setTitle("確認對話框")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("確認要新增設定嗎?")
                            .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(statusLabel.getText().toString().equals("音樂燈連接成功!")) {
                                        final ProgressDialog dialog2 = ProgressDialog.show(SmartWatchActivity.this,
                                                "傳送中", "請稍等...", true);
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


                                        //傳送字串到檯燈
                                        // TODO Auto-generated method stub
                                        new SendInfoTask().execute(sendText);

                                        sqlConnect sc = new sqlConnect(SmartWatchActivity.this);  //sql連線
                                        sc.execute(page, account, modeText, typeText, musicText, lightText, startDateText, startTimeText, hrText);  //將值帶到sqlConnect
                                    }
                                    else{
                                        //new android.support.v7.app.AlertDialog.Builder(SmartWatchActivity.this).setMessage("目前未連接上音樂燈!").show();

                                        quitConnect();

                                        //判斷藍芽是否打開
                                        if(!mBluetoothAdapter.isEnabled())
                                        {
                                            mBluetoothAdapter.enable();
                                        }
                                        mBluetoothAdapter.startDiscovery();
                                        //創建連接
                                        new ConnectTask().execute(address);


                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                if(statusLabel.getText().toString().equals("音樂燈連接成功!")) {
                                                    final ProgressDialog dialog2 = ProgressDialog.show(SmartWatchActivity.this,
                                                            "傳送中", "請稍等...", true);
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


                                                    //傳送字串到檯燈
                                                    // TODO Auto-generated method stub
                                                    new SendInfoTask().execute(sendText);

                                                    sqlConnect sc = new sqlConnect(SmartWatchActivity.this);  //sql連線
                                                    sc.execute(page, account, modeText, typeText, musicText, lightText, startDateText, startTimeText, hrText);  //將值帶到sqlConnect
                                                }

                                                else{
                                                    new android.support.v7.app.AlertDialog.Builder(SmartWatchActivity.this).setMessage("請確認音樂燈是否在附近!").show();
                                                }

                                            }
                                        },3000); // 延时5秒



                                    }
                                }

                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish();
                                }
                            }).show();
                    break;
            }
        }
    };


    public void Init() {    //初始化元件 --> 藍芽連接
        statusLabel=(TextView)this.findViewById(R.id.textView34);
        btnConnect=(ImageButton)this.findViewById(R.id.imageButton10);
    }

    public void InitBluetooth()  {   //初始化藍芽
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
    class SendInfoTask extends AsyncTask<String,String,String> {
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

            if(arg0[0].length()>0) {    //不是空白串
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

    //切斷藍芽連接
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if(rThread!=null)
            {
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

package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import static com.goyourlife.gofit_demo.R.drawable.example;

public class ManualActivity extends Activity {

    int startHour, startMinute;
    int hour1, minute1;
    String modeText = "office", typeText = "preset", musicText = "", lightText = "", startDateText, startTimeText;
    String page = "Manual";
    String lightColor = "日光燈色";
    private ImageView imgLight;
    private Button btnOffice, btnRelax, btnMorning, btnNight;
    private Button btnPreset, btnSelf;
    private Spinner spnMusic;
    //private TextView tvLight;
    private TextView tvStartDate, tvStartTime;
    //private ImageButton imgStartDate, imgStartTime;
    private ImageButton btnGoBack;
    private Button btnCheck;
    String account, value;
    ArrayList<String> standardMode = new ArrayList<String>(), modeType = new ArrayList<String>(), musicChiName = new ArrayList<String>(), musicEngName = new ArrayList<String>(), musicFile = new ArrayList<String>();
    ArrayList<String> lightName = new ArrayList<String>(), lightRGB = new ArrayList<String>();
    ArrayList<String> music = new ArrayList<String>();

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
        setContentView(R.layout.activity_manual);

        //初始化元件
        btnOffice = findViewById(R.id.button6);
        btnRelax = findViewById(R.id.button7);
        btnMorning  = findViewById(R.id.button8);
        btnNight = findViewById(R.id.button9);
        btnPreset = findViewById(R.id.button10);
        btnSelf = findViewById(R.id.button11);
        btnOffice.setOnClickListener(myModeListener);
        btnRelax.setOnClickListener(myModeListener);
        btnMorning.setOnClickListener(myModeListener);
        btnNight.setOnClickListener(myModeListener);
        btnPreset.setOnClickListener(myModeListener);
        btnSelf.setOnClickListener(myModeListener);

        spnMusic = findViewById(R.id.spinner);

        //tvLight = findViewById(R.id.textView43);
        imgLight = findViewById(R.id.imageView9);


        tvStartDate = findViewById(R.id.textView45);
        tvStartTime = findViewById(R.id.textView46);
        /*imgStartDate = findViewById(R.id.imageButton19);
        imgStartTime = findViewById(R.id.imageButton20);
        imgStartDate.setOnClickListener(myDateTimeListener);
        imgStartTime.setOnClickListener(myDateTimeListener);*/

        btnGoBack = findViewById(R.id.imageButton15);
        btnCheck = findViewById(R.id.button12);
        btnGoBack.setOnClickListener(myListener);
        btnCheck.setOnClickListener(myListener);

        //從 mysql 傳回來的 standard 資料表
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");
        value = bundle.getString("value");

        //歌曲對照 資料表
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

        //設定音樂預設值
        musicShow();

        //燈光預設值
        lightShow();

        //設定日期時間預設值
        datetimeShow();


        //首先調用初始化涵數
        Init();
        InitBluetooth();

        quitConnect();
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

    //模式
    Button.OnClickListener myModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button6:  //btn 辦公
                    btnOffice.setBackgroundResource(R.drawable.example2);
                    btnOffice.setTextColor(0XFFFFFFFF);
                    btnRelax.setBackgroundResource(R.drawable.example3);
                    btnRelax.setTextColor(0XFF000000);
                    btnMorning.setBackgroundResource(R.drawable.example3);
                    btnMorning.setTextColor(0XFF000000);
                    btnNight.setBackgroundResource(R.drawable.example3);
                    btnNight.setTextColor(0XFF000000);

                    modeText = "office";
                    lightColor = "日光燈色";
                    musicShow();
                    lightShow();
                    break;
                case R.id.button7:   //btn 放鬆
                    btnOffice.setBackgroundResource(R.drawable.example3);
                    btnOffice.setTextColor(0XFF000000);
                    btnRelax.setBackgroundResource(R.drawable.example2);
                    btnRelax.setTextColor(0XFFFFFFFF);
                    btnMorning.setBackgroundResource(R.drawable.example3);
                    btnMorning.setTextColor(0XFF000000);
                    btnNight.setBackgroundResource(R.drawable.example3);
                    btnNight.setTextColor(0XFF000000);

                    modeText = "relax";
                    lightColor = "青色燈光";
                    musicShow();
                    lightShow();
                    break;
                case R.id.button8:   //btn 早晨
                    btnOffice.setBackgroundResource(R.drawable.example3);
                    btnOffice.setTextColor(0XFF000000);
                    btnRelax.setBackgroundResource(R.drawable.example3);
                    btnRelax.setTextColor(0XFF000000);
                    btnMorning.setBackgroundResource(R.drawable.example2);
                    btnMorning.setTextColor(0XFFFFFFFF);
                    btnNight.setBackgroundResource(R.drawable.example3);
                    btnNight.setTextColor(0XFF000000);

                    modeText = "morning";
                    lightColor = "淡黃暖光";
                    musicShow();
                    lightShow();
                    break;
                case R.id.button9:   //btn 夜晚
                    btnOffice.setBackgroundResource(R.drawable.example3);
                    btnOffice.setTextColor(0XFF000000);
                    btnRelax.setBackgroundResource(R.drawable.example3);
                    btnRelax.setTextColor(0XFF000000);
                    btnMorning.setBackgroundResource(R.drawable.example3);
                    btnMorning.setTextColor(0XFF000000);
                    btnNight.setBackgroundResource(R.drawable.example2);
                    btnNight.setTextColor(0XFFFFFFFF);

                    modeText = "night";
                    lightColor = "橙黃夜燈";
                    musicShow();
                    lightShow();
                    break;
                case R.id.button10:   //btn 預設
                    btnPreset.setBackgroundResource(R.drawable.example2);
                    btnPreset.setTextColor(0XFFFFFFFF);
                    btnSelf.setBackgroundResource(R.drawable.example3);
                    btnSelf.setTextColor(0XFF000000);

                    typeText = "preset";
                    musicShow();
                    lightShow();
                    break;
                case R.id.button11:   //btn 個人
                    btnPreset.setBackgroundResource(R.drawable.example3);
                    btnPreset.setTextColor(0XFF000000);
                    btnSelf.setBackgroundResource(R.drawable.example2);
                    btnSelf.setTextColor(0XFFFFFFFF);

                    typeText = "self";
                    musicShow();
                    lightShow();
                    break;
            }
        }
    };

    //燈光顯示
    public void lightShow() {
        for(int k = 0; k < standardMode.size(); k++) {
            if(standardMode.get(k).equals(modeText) && modeType.get(k).equals(typeText)) {

                if(modeText == "office")
                {
                    imgLight.setImageResource(R.drawable.light_icon5);
                    //圖片 light_icon5
                }
                if(modeText == "relax")
                {
                    imgLight.setImageResource(R.drawable.light_icon6);
                    //圖片 light_icon6
                }
                if(modeText == "morning")
                {
                    imgLight.setImageResource(R.drawable.light_icon7);
                    //圖片 light_icon7
                }
                if(modeText == "night")
                {
                    imgLight.setImageResource(R.drawable.light_icon8);
                    //圖片 light_icon8
                }

                String[] colorSplit = lightRGB.get(k).split(",");
                int x = Integer.parseInt(colorSplit[0]);
                int y = Integer.parseInt(colorSplit[1]);
                int z = Integer.parseInt(colorSplit[2]);
                //tvLight.setBackgroundColor(Color.rgb(x, y, z));
                //tvLight.setTextColor(0XFF7C7C7C);
                //tvLight.setText(lightColor);
                lightText = lightRGB.get(k);


            }
        }
    }

    //歌曲顯示
    public void musicShow() {
        music = new ArrayList<String>();
        for(int i = 0; i < standardMode.size(); i++) {
            if(standardMode.get(i).equals(modeText) && modeType.get(i).equals(typeText)) {
                music.add(musicChiName.get(i));
            }
        }
        ArrayAdapter<String> adapterMusic = new ArrayAdapter<String>(ManualActivity.this, R.layout.myspinner, music);
        spnMusic.setAdapter(adapterMusic);
        spnMusic.setOnItemSelectedListener(mySpinnerListener);
    }

    //歌曲監聽
    Spinner.OnItemSelectedListener mySpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            for(int j = 0; j <standardMode.size(); j++) {
                if(musicChiName.get(j).equals(parent.getSelectedItem().toString())) {
                    musicText = musicFile.get(j);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    //顯示日期與時間
    public void datetimeShow() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //判斷日期與時間是否需要補0
        if((month+1) < 10 && (day) < 10) {
            tvStartDate.setText(year + "/0" + month + "/0" + day);  //month 的索引值為0~11，因此需要加1
        }
        else if((month+1) < 10) {
            tvStartDate.setText(year + "/0" + month + "/" + day);
        }
        else if(day < 10) {
            tvStartDate.setText(year + "/" + month + "/0" + day);
        }
        else {
            tvStartDate.setText(year + "/" + month + "/" + day);
        }
        startDateText = tvStartDate.getText().toString();

        if(hour < 10) {
            tvStartTime.setText("0" + hour + ":" + minute);
        }
        else if(minute < 10) {
            tvStartTime.setText("" + hour + ":0" + minute);
        }
        else {
            tvStartTime.setText("" + hour + ":" + minute);
        }
        startTimeText = tvStartTime.getText().toString();
    }

    //月曆和時鐘
    ImageButton.OnClickListener myDateTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            switch (v.getId()) {
                case R.id.imageButton19:  //btn 月曆
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog1 = new DatePickerDialog(ManualActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            if((month+1) < 10 && (day) < 10) {
                                tvStartDate.setText(year + "/0" + (month + 1) + "/0" + day);  //month 的索引值為0~11，因此需要加1
                            }
                            else if((month+1) < 10) {
                                tvStartDate.setText(year + "/0" + (month + 1) + "/" + day);
                            }
                            else if(day < 10) {
                                tvStartDate.setText(year + "/" + (month + 1) + "/0" + day);
                            }
                            else {
                                tvStartDate.setText(year + "/" + (month + 1) + "/" + day);
                            }
                            startDateText = tvStartDate.getText().toString();
                        }
                    }, year, month, dayOfMonth);
                    datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis());   //已當前系統時間為準，不可以選擇以前的時間
                    datePickerDialog1.show();
                    break;
                case R.id.imageButton20:  //btn 時間
                    // Use the current time as the default values for the picker
                    hour1 = calendar.get(Calendar.HOUR_OF_DAY);
                    minute1 = calendar.get(Calendar.MINUTE);
                    // Create a new instance of TimePickerDialog and return it
                    new TimePickerDialog(ManualActivity.this, new TimePickerDialog.OnTimeSetListener(){

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //tvStartTime.setText(hourOfDay + ":" + minute);
                            tvStartTime.setText(new StringBuilder().append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(":")
                                    .append(minute < 10 ? "0" + minute : minute));
                            startHour = hourOfDay;
                            startMinute = minute;
                            startTimeText = tvStartTime.getText().toString();
                        }
                    }, hour1, minute1, false).show();
                    break;
            }
        }
    };

    //確認
    ImageButton.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.imageButton15:  //btn 回前頁
                    //quitConnect();
                    finish();
                    break;
                case R.id.button12:  //btn 確認設定

                    //傳送給檯燈的時間與日期，去掉中間的"/" & ":"
                    String[] dateSp = startDateText.split("/");
                    sendDate = dateSp[0] + dateSp[1] + dateSp[2];
                    String[] timeSp = startTimeText.split(":");
                    sendTime = timeSp[0] + timeSp[1];

                    sendText = musicText + " {" + lightText + "} " + sendDate + " " + sendTime;   //傳送給檯燈的字串

                    if(music.size() == 0) {
                        musicText = "";
                        Toast.makeText(ManualActivity.this, "歌曲欄位不可為空", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        new AlertDialog.Builder(ManualActivity.this)
                                .setTitle("確認對話框")
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage("確認建立此筆設定嗎?")
                                .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(statusLabel.getText().toString().equals("音樂燈連接成功!")) {
                                            final ProgressDialog dialog2 = ProgressDialog.show(ManualActivity.this,
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

                                            sqlConnect sc = new sqlConnect(ManualActivity.this);  //sql連線
                                            sc.execute(page, account, modeText, typeText, musicText, lightText, startDateText, startTimeText);  //將值帶到sqlConnect
                                        }
                                        else{
                                            //new android.support.v7.app.AlertDialog.Builder(ManualActivity.this).setMessage("目前未連接上音樂燈!").show();

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
                                                        final ProgressDialog dialog2 = ProgressDialog.show(ManualActivity.this,
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

                                                        sqlConnect sc = new sqlConnect(ManualActivity.this);  //sql連線
                                                        sc.execute(page, account, modeText, typeText, musicText, lightText, startDateText, startTimeText);  //將值帶到sqlConnect
                                                    }

                                                    else{
                                                        new android.support.v7.app.AlertDialog.Builder(ManualActivity.this).setMessage("請確認音樂燈是否在附近!").show();
                                                    }

                                                }
                                            },3000); // 延时1秒
                                        }


                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //finish();
                                    }
                                }).show();
                    }


                    break;
            }
        }
    };


    public void Init() {    //初始化元件 --> 藍芽連接
        statusLabel=(TextView)this.findViewById(R.id.textView47);
        btnConnect=(ImageButton)this.findViewById(R.id.imageButton16);
    }

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

            if(arg0[0].length()>0) {   //不是空白串
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


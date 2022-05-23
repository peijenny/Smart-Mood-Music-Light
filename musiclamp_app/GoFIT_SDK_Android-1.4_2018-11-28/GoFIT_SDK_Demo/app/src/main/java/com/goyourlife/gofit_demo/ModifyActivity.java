package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class ModifyActivity extends Activity {

    int startHour, startMinute;
    int hour1, minute1;
    String selectDateText, selectTimeText;
    String modeText = "office", typeText = "preset", musicText = "", lightText = "", startDateText, startTimeText;
    String page = "Modify", modifyText = "update";
    String lightColor = "日光燈色";
    private Button btnOffice, btnRelax, btnMorning, btnNight;
    private Button btnPreset, btnSelf;
    private Spinner spnMusic;
    private ImageView imgLight;
    //private TextView tvLight, tvMusic;
    private TextView tvStartDate, tvStartTime;
    private ImageButton imgStartDate, imgStartTime;
    private ImageButton btnGoBack;
    private Button btnCheck;
    private Button btnUpdate, btnDelete;

    private Spinner spnSelectDate;
    ArrayList<String> selectDate = new ArrayList<String>();
    ArrayList<String> userAcc = new ArrayList<String>(), userMode = new ArrayList<String>(), userType = new ArrayList<String>(), userMusic = new ArrayList<String>(), userLight = new ArrayList<String>();
    ArrayList<String>  startDate = new ArrayList<String>(), startTime = new ArrayList<String>(), userHrRate = new ArrayList<String>();
    ArrayList<String> standardMode = new ArrayList<String>(), modeType = new ArrayList<String>(), musicChiName = new ArrayList<String>(), musicEngName = new ArrayList<String>(), musicFile = new ArrayList<String>();
    ArrayList<String> lightName = new ArrayList<String>(), lightRGB = new ArrayList<String>();
    ArrayList<String> music = new ArrayList<String>();
    String account, value;

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
        setContentView(R.layout.activity_modify);

        //初始化元件
        spnSelectDate = findViewById(R.id.spinner3);
        btnOffice = findViewById(R.id.button15);
        btnRelax = findViewById(R.id.button16);
        btnMorning  = findViewById(R.id.button17);
        btnNight = findViewById(R.id.button18);
        btnPreset = findViewById(R.id.button19);
        btnSelf = findViewById(R.id.button20);
        btnOffice.setOnClickListener(myModeListener);
        btnRelax.setOnClickListener(myModeListener);
        btnMorning.setOnClickListener(myModeListener);
        btnNight.setOnClickListener(myModeListener);
        btnPreset.setOnClickListener(myModeListener);
        btnSelf.setOnClickListener(myModeListener);

        spnMusic = findViewById(R.id.spinner4);
        imgLight = findViewById(R.id.imageView10);

        //tvLight = findViewById(R.id.textView55);
        //tvMusic = findViewById(R.id.textView52);

        tvStartDate = findViewById(R.id.textView70);
        tvStartTime = findViewById(R.id.textView71);
        imgStartDate = findViewById(R.id.imageButton25);
        imgStartTime = findViewById(R.id.imageButton26);
        imgStartDate.setOnClickListener(myDateTimeListener);
        imgStartTime.setOnClickListener(myDateTimeListener);

        btnGoBack = findViewById(R.id.imageButton23);
        btnCheck = findViewById(R.id.button27);
        btnGoBack.setOnClickListener(myListener);
        btnCheck.setOnClickListener(myListener);

        btnUpdate = findViewById(R.id.button13);
        btnDelete = findViewById(R.id.button14);
        btnUpdate.setOnClickListener(myModifyListener);
        btnDelete.setOnClickListener(myModifyListener);

        //從 mysql 傳回來的 standard & setting 資料表
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");

        value = bundle.getString("value");
        String[] split = value.split("good");

        //使用者設定查詢
        try{
            //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(split[0]);
            //for (int i = 0; i < array.length(); i++) {
            for (int i = array.length() - 1; i >= 0; i--) {
                JSONObject jsonObject = array.getJSONObject(i);

                //將資料庫中的日期與時間，進行處理，將 "/" & ":" 拿掉
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                String dateText, timeText;
                if(month < 10) {
                    if(day < 10) {
                        dateText =String.valueOf(year) + "0" + String.valueOf(month) + "0" + String.valueOf(day);
                    }
                    else {
                        dateText =String.valueOf(year) + "0" + String.valueOf(month) + String.valueOf(day);
                    }
                }
                else {
                    if(day < 10) {
                        dateText =String.valueOf(year) + String.valueOf(month) + "0" + String.valueOf(day);
                    }
                    else {
                        dateText =String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
                    }
                }

                if(hour < 10) {
                    if(minute < 10) {
                        timeText = "0" + String.valueOf(hour) + "0" + String.valueOf(minute);
                    }
                    else {
                        timeText = "0" + String.valueOf(hour) + String.valueOf(minute);
                    }
                }
                else {
                    if(minute < 10) {
                        timeText = String.valueOf(hour) + "0" + String.valueOf(minute);
                    }
                    else {
                        timeText = String.valueOf(hour) + String.valueOf(minute);
                    }
                }

                String[] dateSplit = jsonObject.getString("start_date").split("/");
                String[] timeSplit = jsonObject.getString("start_time").split(":");
                String dateSp = dateSplit[0] + dateSplit[1] + dateSplit[2];
                String timeSp = timeSplit[0] + timeSplit[1];


                //判斷能夠修改的設定有哪些，日期必須大於等於當前系統日期與時間，才能修改
                if(Integer.valueOf(dateSp) > Integer.valueOf(dateText)  && jsonObject.getString("user_hr").equals("")) {
                    userAcc.add(jsonObject.getString("user_acc"));
                    userMode.add(jsonObject.getString("user_mode"));
                    userType.add(jsonObject.getString("user_type"));
                    userMusic.add(jsonObject.getString("user_music"));
                    userLight.add(jsonObject.getString("user_light"));
                    startDate.add(jsonObject.getString("start_date"));
                    startTime.add(jsonObject.getString("start_time"));
                    userHrRate.add(jsonObject.getString("user_hr"));

                    String start_date = jsonObject.getString("start_date");
                    String start_time = jsonObject.getString("start_time");
                    selectDate.add(start_date + ", " + start_time);
                }
                if (Integer.valueOf(dateSp) == Integer.valueOf(dateText) && Integer.valueOf(timeSp) >= Integer.valueOf(timeText) && jsonObject.getString("user_hr").equals("")) {
                    userAcc.add(jsonObject.getString("user_acc"));
                    userMode.add(jsonObject.getString("user_mode"));
                    userType.add(jsonObject.getString("user_type"));
                    userMusic.add(jsonObject.getString("user_music"));
                    userLight.add(jsonObject.getString("user_light"));
                    startDate.add(jsonObject.getString("start_date"));
                    startTime.add(jsonObject.getString("start_time"));
                    userHrRate.add(jsonObject.getString("user_hr"));

                    String start_date = jsonObject.getString("start_date");
                    String start_time = jsonObject.getString("start_time");
                    selectDate.add(start_date + ", " + start_time);
                }
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        //模式對應資料表
        try{
            //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(split[1]);
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


        //選擇日期
        ArrayAdapter<String> adapterSelectDate = new ArrayAdapter<String>(ModifyActivity.this, R.layout.myspinner, selectDate);
        spnSelectDate.setAdapter(adapterSelectDate);
        spnSelectDate.setOnItemSelectedListener(mySpnListener);


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

    Button.OnClickListener myModifyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button13:  //btn 修改
                    btnUpdate.setBackgroundResource(R.drawable.example6);
                    btnUpdate.setTextColor(0XFFFFFFFF);
                    btnDelete.setBackgroundColor(0X00FFFFFF);
                    btnDelete.setTextColor(0XFF000000);
                    modifyText = "update";
                    break;
                case R.id.button14:  //btn 刪除
                    btnUpdate.setBackgroundColor(0X00FFFFFF);
                    btnUpdate.setTextColor(0XFF000000);
                    btnDelete.setBackgroundResource(R.drawable.example6);
                    btnDelete.setTextColor(0XFFFFFFFF);
                    modifyText = "delete";
                    break;
            }
        }
    };

    int num;    //紀錄當前的spinner index
    String aa;  //判斷是從spinner取的值 or btn取的值
    String musText = "";  //setting 中的 user_music(音樂編號)
    //選擇日期spinner
    Spinner.OnItemSelectedListener mySpnListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            aa = "sp";
            num=position;
            if(userMode.get(position).equals("office")) {
                //musText = userMusic.get(position);
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
            }
            else if(userMode.get(position).equals("relax")) {
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
            }
            else if(userMode.get(position).equals("morning")) {
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
            }
            else if(userMode.get(position).equals("night")) {
                btnOffice.setBackgroundResource(R.drawable.example3);
                btnOffice.setTextColor(0XFF000000);
                btnRelax.setBackgroundResource(R.drawable.example3);
                btnRelax.setTextColor(0XFF000000);
                btnMorning.setBackgroundResource(R.drawable.example3);
                btnMorning.setTextColor(0XFF000000);
                btnNight.setBackgroundResource(R.drawable.example2);
                btnNight.setTextColor(0XFFFFFFFF);

                modeText = "morning";
                lightColor = "淡黃暖光";
            }

            if(userType.get(position).equals("preset")) {
                btnPreset.setBackgroundResource(R.drawable.example2);
                btnPreset.setTextColor(0XFFFFFFFF);
                btnSelf.setBackgroundResource(R.drawable.example3);
                btnSelf.setTextColor(0XFF000000);

                typeText = "preset";
            }
            else if(userType.get(position).equals("self")) {
                btnPreset.setBackgroundResource(R.drawable.example3);
                btnPreset.setTextColor(0XFF000000);
                btnSelf.setBackgroundResource(R.drawable.example2);
                btnSelf.setTextColor(0XFFFFFFFF);

                typeText = "self";
            }

            musicShow();
            lightShow();

            tvStartDate.setText(startDate.get(position));  //顯示設定的日期
            tvStartTime.setText(startTime.get(position));  //顯示設定的時間
            startDateText = tvStartDate.getText().toString();
            startTimeText = tvStartTime.getText().toString();

            String[] dtSplit = selectDate.get(position).split(", ");  //mysql修改時的條件 --> WHERE start_date & start_time
            selectDateText = dtSplit[0];
            selectTimeText = dtSplit[1];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    //模式
    Button.OnClickListener myModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            aa = "btn";
            switch (v.getId()) {
                case R.id.button15:  //btn 辦公
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
                case R.id.button16:  //btn 放鬆
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
                case R.id.button17:  //btn 早晨
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
                case R.id.button18:  //btn 夜晚
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
                case R.id.button19:  //btn 預設
                    btnPreset.setBackgroundResource(R.drawable.example2);
                    btnPreset.setTextColor(0XFFFFFFFF);
                    btnSelf.setBackgroundResource(R.drawable.example3);
                    btnSelf.setTextColor(0XFF000000);

                    typeText = "preset";
                    musicShow();
                    lightShow();
                    break;
                case R.id.button20:  //btn 個人
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
        ArrayAdapter<String> adapterMusic = new ArrayAdapter<String>(ModifyActivity.this, R.layout.myspinner, music);
        spnMusic.setAdapter(adapterMusic);
        spnMusic.setOnItemSelectedListener(mySpinnerListener);
    }

    //歌曲
    Spinner.OnItemSelectedListener mySpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String musictt = "";

            if(aa == "sp") {
                for(int i = 0;i < standardMode.size(); i++) {
                    if(musicFile.get(i).equals(userMusic.get(num))) {
                        musictt = musicChiName.get(i);
                    }
                }
                for(int k = 0;k < music.size(); k++) {
                    if(music.get(k).equals(musictt)) {
                        spnMusic.setSelection(k);
                    }
                }
            }

            for(int j = 0; j <standardMode.size(); j++) {
                if(musicChiName.get(j).equals(parent.getSelectedItem().toString())) {
                    musicText = musicFile.get(j);
                }
            }
            aa = "btn";
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    //月曆和時鐘
    ImageButton.OnClickListener myDateTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            switch (v.getId()) {
                case R.id.imageButton25:  //btn 月曆
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog1 = new DatePickerDialog(ModifyActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis());
                    datePickerDialog1.show();
                    break;
                case R.id.imageButton26:  //btn 時間
                    // Use the current time as the default values for the picker
                    hour1 = calendar.get(Calendar.HOUR_OF_DAY);
                    minute1 = calendar.get(Calendar.MINUTE);
                    // Create a new instance of TimePickerDialog and return it
                    new TimePickerDialog(ModifyActivity.this, new TimePickerDialog.OnTimeSetListener(){

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

    String text;  //存放 dialog msg 字串
    //確認
    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton23:  //btn 回前頁
                    //quitConnect();
                    finish();
                    break;
                case R.id.button27:  //btn 修改設定
                    if(selectDate.size() == 0)
                    {
                        Toast.makeText(ModifyActivity.this, "當前沒有可進行修改的設定!!!", Toast.LENGTH_SHORT).show();
                    }
                    else if(selectDate.size() != 0 && music.size() == 0) {
                        musicText = "";
                        Toast.makeText(ModifyActivity.this, "歌曲欄位不可為空", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String[] dateSp = startDateText.split("/");  //傳送到檯燈的日期，將"/"拿掉
                        sendDate = dateSp[0] + dateSp[1] + dateSp[2];
                        String[] timeSp = startTimeText.split(":");  //傳送到檯燈的時間，將":"拿掉
                        sendTime = timeSp[0] + timeSp[1];

                        if(modifyText.equals("update")) {
                            text = "確認變更此筆設定嗎?";
                            sendText = musicText + " {" + lightText + "} " + sendDate + " " + sendTime;
                            //sendText = "update " + musicText + " {" + lightText + "} " + sendDate + " " + sendTime;
                        }
                        if(modifyText.equals("delete")) {
                            text = "確認取消(刪除)此筆設定嗎?";
                            sendText = musicText + " {" + lightText + "} " + sendDate + " " + sendTime;
                            //sendText = "delete " + musicText + " {" + lightText + "} " + sendDate + " " + sendTime;
                        }

                        new AlertDialog.Builder(ModifyActivity.this)
                                .setTitle("確認對話框")
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage(text)
                                .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(statusLabel.getText().toString().equals("檯燈連接成功!")) {
                                            final ProgressDialog dialog2 = ProgressDialog.show(ModifyActivity.this,
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

                                            sqlConnect sc = new sqlConnect(ModifyActivity.this);  //sql連線
                                            sc.execute(page, modifyText, account, selectDateText, selectTimeText, modeText, typeText, musicText, lightText, startDateText, startTimeText);  //將page、account，帶到sqlConnect
                                            //String modifyText, modifyAccount, modifySelectStartDate, modifySelectStartTime, modifyMode, modifyType, modifyMusic, modifyLight, modifyStartDate, modifyStartTime;
                                        }
                                        else {
                                            new AlertDialog.Builder(ModifyActivity.this).setMessage("請先連接檯燈!").show();
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


    public void Init() {   //初始化元件 --> 藍芽連接
        statusLabel=(TextView)this.findViewById(R.id.textView72);
        btnConnect=(ImageButton)this.findViewById(R.id.imageButton24);
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
            return "檯燈連接成功!";
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

            if(arg0[0].length()>0) {  //不是空白串
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



package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HrSettingActivity extends Activity {

    private Spinner spnSelectDate;
    private TextView tvMode, tvType, tvMusic, tvLight, tvStartDate, tvStartTime, tvHr;
    private ImageButton btnGoBack;
    ArrayList<String> selectDate = new ArrayList<String>();
    ArrayList<String> userAcc = new ArrayList<String>(), userMode = new ArrayList<String>(), userType = new ArrayList<String>(), userMusic = new ArrayList<String>(), userLight = new ArrayList<String>();
    ArrayList<String>  startDate = new ArrayList<String>(), startTime = new ArrayList<String>(), userHrRate = new ArrayList<String>();
    ArrayList<String> standardMode = new ArrayList<String>(), modeType = new ArrayList<String>(), musicChiName = new ArrayList<String>(), musicEngName = new ArrayList<String>(), musicFile = new ArrayList<String>();
    ArrayList<String> lightName = new ArrayList<String>(), lightRGB = new ArrayList<String>();
    String account, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrsetting);


        //元件初始化
        spnSelectDate = findViewById(R.id.spinner2);
        tvMode = findViewById(R.id.textView58);
        tvHr = findViewById(R.id.textView59);
        tvType = findViewById(R.id.textView61);
        tvMusic = findViewById(R.id.textView64);
        tvLight = findViewById(R.id.textView66);
        tvStartDate = findViewById(R.id.textView68);
        tvStartTime = findViewById(R.id.textView69);

        btnGoBack = findViewById(R.id.imageButton17);
        btnGoBack.setOnClickListener(myListener);

        //從 mysql 傳回來的 standard &  setting 資料表
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");
        value = bundle.getString("value");
        String[] split = value.split("good");

        //使用者設定查詢
        try{
            //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(split[0]);
            for (int i = array.length() - 1; i >= 0; i--) {  //能夠從最近到最舊的設定排序
                JSONObject jsonObject = array.getJSONObject(i);

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
        ArrayAdapter<String> adapterSelectDate = new ArrayAdapter<String>(HrSettingActivity.this, R.layout.myspinner, selectDate);
        spnSelectDate.setAdapter(adapterSelectDate);
        spnSelectDate.setOnItemSelectedListener(mySpnListener);


    }

    //選擇日期spinner
    Spinner.OnItemSelectedListener mySpnListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //判斷設定中的模式與歌曲類別
            if(userMode.get(position).equals("low")) {
                tvMode.setText("情緒低落");
                tvMode.setTextColor(0XFF967DC0);
            }
            else if(userMode.get(position).equals("calm")) {
                tvMode.setText("情緒平靜");
                tvMode.setTextColor(0XFF967DC0);
            }
            else if(userMode.get(position).equals("excitement")) {
                tvMode.setText("情緒激動");
                tvMode.setTextColor(0XFF967DC0);
            }
            else if(userMode.get(position).equals("office")) {
                tvMode.setText("辦公模式");
                tvMode.setTextColor(0XFF7FBEB4);
            }
            else if(userMode.get(position).equals("relax")) {
                tvMode.setText("放鬆模式");
                tvMode.setTextColor(0XFFF88968);
            }
            else if(userMode.get(position).equals("morning")) {
                tvMode.setText("早晨模式");
                tvMode.setTextColor(0XFFECD40D);
            }
            else if(userMode.get(position).equals("night")) {
                tvMode.setText("夜晚模式");
                tvMode.setTextColor(0XFF608FEE);
            }

            if(userType.get(position).equals("preset")) {
                tvType.setText("預設");
                tvType.setTextColor(0XFFE66395);
            }
            else if(userType.get(position).equals("self")) {
                tvType.setText("個人");
                tvType.setTextColor(0XFFE66395);
            }

            //判斷設定中的歌曲名稱與燈光名稱
            for(int k = 0; k < standardMode.size(); k ++) {
                if(userMusic.get(position).equals(musicFile.get(k))) {
                    tvMusic.setText(musicChiName.get(k));
                }

                if(userLight.get(position).equals(lightRGB.get(k))) {
                    if(lightName.get(k).equals("Orange")) {
                        tvLight.setText("橙色燈光");
                    }
                    if(lightName.get(k).equals("LightBlue")) {
                        tvLight.setText("淺藍燈光");
                    }
                    if(lightName.get(k).equals("IndianRed")) {
                        tvLight.setText("粉紅燈光");
                    }
                    if(lightName.get(k).equals("Fluorescent")) {
                        tvLight.setText("日光燈色");
                    }
                    if(lightName.get(k).equals("MediumAquamarine")) {
                        tvLight.setText("青色燈光");
                    }
                    if(lightName.get(k).equals("Yellow")) {
                        tvLight.setText("淡黃暖光");
                    }
                    if(lightName.get(k).equals("LightOrange")) {
                        tvLight.setText("橙黃夜燈");
                    }
                }
            }

            String[] colorSplit = userLight.get(position).split(",");
            int a = Integer.parseInt(colorSplit[0]);
            int b = Integer.parseInt(colorSplit[1]);
            int c = Integer.parseInt(colorSplit[2]);
            tvLight.setBackgroundColor(Color.rgb(a, b, c));

            tvStartDate.setText(startDate.get(position));
            tvStartTime.setText(startTime.get(position));

            if(!userHrRate.get(position).equals("")) {
                tvHr.setText("心跳值: " + userHrRate.get(position));
            }
            else {
                tvHr.setText(userHrRate.get(position));
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    //回首頁 & 查詢 button
    ImageButton.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}

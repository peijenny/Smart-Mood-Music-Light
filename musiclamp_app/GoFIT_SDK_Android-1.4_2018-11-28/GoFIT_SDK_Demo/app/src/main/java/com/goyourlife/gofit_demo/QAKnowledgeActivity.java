package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QAKnowledgeActivity extends Activity {

    private Button btnLight, btnMusic, btnAppUse;
    private Spinner spnQuestion;
    private ImageView imgAppUse, imgMusicLight;
    private TextView tvAnswer, tvURL;
    private ImageButton btnGoback;
    String qaText = "music";

    int[] image = {R.drawable.light_img1, R.drawable.light_img2, R.drawable.light_img3, R.drawable.light_img4, R.drawable.light_img5, R.drawable.light_img6,
            R.drawable.music_img1, R.drawable.music_img2, R.drawable.music_img3, R.drawable.music_img4, R.drawable.music_img5, R.drawable.music_img6,
            R.drawable.appuser_img4, R.drawable.appuser_img1, R.drawable.appuser_img5, R.drawable.appuser_img6};

    ArrayList<String> selectQuestion;
    ArrayList<String> qaType = new ArrayList<String>(), question = new ArrayList<String>(), answer = new ArrayList<String>(), qaURL = new ArrayList<String>();
    String account, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qaknowledge);

        //元件初始化
        spnQuestion = findViewById(R.id.spinner5);
        imgMusicLight = findViewById(R.id.imageView3);
        imgAppUse = findViewById(R.id.imageView4);

        tvAnswer = findViewById(R.id.textView76);
        tvURL = findViewById(R.id.textView77);

        btnMusic = findViewById(R.id.button22);
        btnLight = findViewById(R.id.button23);
        btnAppUse = findViewById(R.id.button24);
        btnMusic.setOnClickListener(myListener);
        btnLight.setOnClickListener(myListener);
        btnAppUse.setOnClickListener(myListener);

        btnGoback = findViewById(R.id.imageButton22);
        btnGoback.setOnClickListener(myListener);

        //從 mysql 傳回來的 knowledge 資料表
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");
        value = bundle.getString("value");

        //QA小知識資料表
        try{
            //建立一個JSONArray並帶入JSON格式文字，getString(String key)取出欄位的數值
            JSONArray array = new JSONArray(value);
            for (int i = 0; i < array.length(); i++) {
            //for (int i = array.length() - 1; i >= 0; i--) {
                JSONObject jsonObject = array.getJSONObject(i);

                qaType.add(jsonObject.getString("qa_type"));
                question.add(jsonObject.getString("qa_question"));
                answer.add(jsonObject.getString("qa_answer"));
                qaURL.add(jsonObject.getString("qa_url"));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        selectQuestion = new ArrayList<String>();
        questionShow();

    }



    //問題顯示
    public void questionShow() {
        selectQuestion = new ArrayList<String>();
        for(int i = 0; i < qaType.size(); i++) {
            if(qaType.get(i).equals(qaText) ) {
                selectQuestion.add(question.get(i));
            }
        }
        ArrayAdapter<String> adapterSelectQuestion = new ArrayAdapter<String>(QAKnowledgeActivity.this, R.layout.myspinner, selectQuestion);
        spnQuestion.setAdapter(adapterSelectQuestion);
        spnQuestion.setOnItemSelectedListener(mySpnListener);
    }

    Spinner.OnItemSelectedListener mySpnListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //判斷問題的類別，顯示在不同的imageView上
            for(int j = 0; j < qaType.size(); j++) {
                if(question.get(j).equals(parent.getSelectedItem().toString())) {
                    if(qaText == "music" || qaText == "light") {
                        imgMusicLight.setImageResource(image[j]);
                        imgAppUse.setImageResource(R.drawable.img_icon1);
                        imgAppUse.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        tvAnswer.setText(answer.get(j));
                        tvURL.setText("參考文章: " + qaURL.get(j));
                    }
                    if(qaText == "appuse") {
                        imgAppUse.setImageResource(image[j]);
                        imgMusicLight.setImageResource(R.drawable.img_icon1);
                        imgAppUse.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        tvAnswer.setText("");
                        tvURL.setText("");
                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.imageButton22:  //btn 回前頁
                    finish();
                    break;
                case R.id.button22:  //btn 音樂方面
                    btnMusic.setBackgroundResource(R.drawable.example6);
                    btnMusic.setTextColor(0XFFFFFFFF);
                    btnLight.setBackgroundColor(0X00FFFFFF);
                    btnLight.setTextColor(0XFF000000);
                    btnAppUse.setBackgroundColor(0X00FFFFFF);
                    btnAppUse.setTextColor(0XFF000000);

                    qaText = "music";
                    questionShow();
                    break;
                case R.id.button23:  //btn 燈光方面
                    btnMusic.setBackgroundColor(0X00FFFFFF);
                    btnMusic.setTextColor(0XFF000000);
                    btnLight.setBackgroundResource(R.drawable.example6);
                    btnLight.setTextColor(0XFFFFFFFF);
                    btnAppUse.setBackgroundColor(0X00FFFFFF);
                    btnAppUse.setTextColor(0XFF000000);

                    qaText = "light";
                    questionShow();
                    break;
                case R.id.button24:  //btn APP使用方面
                    btnMusic.setBackgroundColor(0X00FFFFFF);
                    btnMusic.setTextColor(0XFF000000);
                    btnLight.setBackgroundColor(0X00FFFFFF);
                    btnLight.setTextColor(0XFF000000);
                    btnAppUse.setBackgroundResource(R.drawable.example6);
                    btnAppUse.setTextColor(0XFFFFFFFF);
                    qaText = "appuse";
                    questionShow();
                    break;
            }
        }
    };
}

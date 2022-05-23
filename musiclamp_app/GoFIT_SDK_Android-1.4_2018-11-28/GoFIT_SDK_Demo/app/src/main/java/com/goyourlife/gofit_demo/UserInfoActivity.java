package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoActivity extends Activity {

    private TextView userAcc, tvBirthday;
    private EditText edtName,edtEmail;
    private RadioButton rdmale, rdfemale;
    private ImageButton imgUpdate;
    String name, email, birthday, gender;
    String updateGender;
    String account;

    private ImageButton btnGoback;
    private Button btnUpdate;

    //日曆Calendar
    private ImageButton selectDate;
    //private TextView date;
    private DatePickerDialog datePickerDialog;
    private int year,month,dayOfMonth;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        //元件初始化
        userAcc = findViewById(R.id.textView20);
        tvBirthday = findViewById(R.id.textView19);
        edtName = findViewById(R.id.editText8);
        edtEmail = findViewById(R.id.editText10);
        rdmale = findViewById(R.id.radioButton4);
        rdfemale = findViewById(R.id.radioButton5);
        rdmale.setOnClickListener(myRDListener);
        rdfemale.setOnClickListener(myRDListener);

        imgUpdate = findViewById(R.id.imageButton6);
        imgUpdate.setOnClickListener(myImgListener);

        selectDate = findViewById(R.id.imageButton14);
        selectDate.setOnClickListener(myCalendarListener);
        selectDate.setEnabled(false);

        btnGoback = findViewById(R.id.imageButton);
        btnUpdate = findViewById(R.id.button5);
        btnGoback.setOnClickListener(myListener);
        btnUpdate.setOnClickListener(myListener);

        //從主選單連mysql user --> account & name & email & birthday & gender 到使用者設定中
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");
        userAcc.setText(account);

        String value = bundle.getString("value");
        String[] split = value.split("使用者資料正確!!!");

        try {
            JSONArray array = new JSONArray(split[1]);
            JSONObject jsonObject = array.getJSONObject(0);
            name = jsonObject.getString("name");
            email = jsonObject.getString("email");
            birthday = jsonObject.getString("birthday");
            gender = jsonObject.getString("gender");

            if(gender.equals("female")) {
                rdfemale.setChecked(true);
                rdfemale.setTextColor(Color.rgb(153, 0, 51));
                updateGender = "female";
            }
            else {
                rdmale.setChecked(true);
                rdmale.setTextColor(Color.rgb(153, 0, 51));
                updateGender = "male";
            }

            edtName.setText(name);
            edtEmail.setText(email);
            tvBirthday.setText(birthday);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.imageButton:   //btn回前頁
                    finish();
                    break;
                case R.id.button5:  //btn確認修改
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m = p.matcher(edtName.getText().toString());
                    if(edtName.getText().toString().equals("")) {
                        Toast.makeText(UserInfoActivity.this,"姓名欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(m.matches()){   //判斷使用者欄位中是否包含數字
                        Toast.makeText(UserInfoActivity.this,"姓名欄位不可包含數字!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(edtEmail.getText().toString().equals("")) {
                        Toast.makeText(UserInfoActivity.this,"Email欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(!edtEmail.getText().toString().contains("@")) {
                        Toast.makeText(UserInfoActivity.this,"Email欄位格式不正確!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(UserInfoActivity.this,"資料輸入正確!!",Toast.LENGTH_SHORT).show();
                        //將七個欄位傳到sqlConnect
                        String page = "UserInfo";    //登入頁
                        String updateAccount = account;
                        String updateName = edtName.getText().toString();
                        String updateEmail = edtEmail.getText().toString();
                        String updateBirthday = tvBirthday.getText().toString();

                        sqlConnect sc = new sqlConnect(UserInfoActivity.this);  //sql連線
                        sc.execute(page, updateAccount, updateName, updateEmail, updateGender, updateBirthday);  //將值帶到sqlConnect

                        //修改過後，將欄位inEnabled
                        edtName.setEnabled(false);
                        edtEmail.setEnabled(false);
                        rdmale.setEnabled(false);
                        rdfemale.setEnabled(false);
                        selectDate.setEnabled(false);
                        btnUpdate.setEnabled(false);

                        //修改RadioButton inEnabled 後的顏色
                        if(updateGender.equals("female")) {
                            rdfemale.setChecked(true);
                            rdfemale.setTextColor(Color.rgb(153, 0, 51));
                            updateGender = "female";
                        }
                        else {
                            rdmale.setChecked(true);
                            rdmale.setTextColor(Color.rgb(153, 0, 51));
                            updateGender = "male";
                        }
                    }
                    break;
            }
        }
    };

    //RadioButton 男
    RadioButton.OnClickListener myRDListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.radioButton4:  //rd男
                    rdmale.setChecked(true);
                    rdfemale.setChecked(false);
                    updateGender = "male";
                    break;
                case R.id.radioButton5:  //rd女
                    rdfemale.setChecked(true);
                    rdmale.setChecked(false);
                    updateGender = "female";
                    break;
            }
        }
    };

    //Calendar
    ImageButton.OnClickListener myCalendarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(UserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    tvBirthday.setText(year + "/" + (month+1) + "/" + day);  //month 的索引值為0~11，因此需要加1
                }
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        }
    };

    int i = 0;  //判斷 開 or 關 修改按鈕
    //ImageButton -> 開啟修改按鈕
    ImageButton.OnClickListener myImgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(i % 2 == 0) {   //開啟所有元件，進行修改
                edtName.setEnabled(true);
                edtEmail.setEnabled(true);

                rdmale.setEnabled(true);
                rdmale.setTextColor(Color.rgb(0, 0, 0));
                rdfemale.setEnabled(true);
                rdfemale.setTextColor(Color.rgb(0, 0, 0));

                selectDate.setEnabled(true);
                btnUpdate.setEnabled(true);
            }
            else {   //關閉所有元件，不能進行修改
                edtName.setEnabled(false);
                edtEmail.setEnabled(false);
                rdmale.setEnabled(false);
                rdfemale.setEnabled(false);
                selectDate.setEnabled(false);
                btnUpdate.setEnabled(false);

                if(updateGender.equals("female")) {
                    rdfemale.setChecked(true);
                    rdfemale.setTextColor(Color.rgb(153, 0, 51));
                    updateGender = "female";
                }
                else {
                    rdmale.setChecked(true);
                    rdmale.setTextColor(Color.rgb(153, 0, 51));
                    updateGender = "male";
                }
            }
            i++;
        }
    };
}

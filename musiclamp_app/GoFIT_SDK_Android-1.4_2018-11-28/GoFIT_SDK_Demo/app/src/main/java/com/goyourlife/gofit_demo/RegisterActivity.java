package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    //日曆Calendar
    private ImageButton selectDate;
    private TextView date;
    private DatePickerDialog datePickerDialog;
    private int year,month,dayOfMonth;
    private Calendar calendar;

    private EditText edtAccount, edtPassword, edtCheckpassword, edtName, edtEmail;
    private RadioButton rdmale, rdfemale;
    private Button btnRegister;
    private ImageButton btnGoback;
    String gender = "male";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //元件初始化
        edtAccount = findViewById(R.id.editText3);
        edtPassword = findViewById(R.id.editText4);
        edtCheckpassword = findViewById(R.id.editText5);
        edtName = findViewById(R.id.editText6);
        edtEmail = findViewById(R.id.editText7);

        btnGoback = findViewById(R.id.imageButton1);
        btnRegister = findViewById(R.id.button4);
        btnGoback.setOnClickListener(myListener);
        btnRegister.setOnClickListener(myListener);

        //生日imageButton
        selectDate = findViewById(R.id.imageButton3);
        selectDate.setImageResource(R.drawable.date_icon);
        selectDate.setOnClickListener(myCalendarListener);
        date = findViewById(R.id.textView11);

        //RadioButton
        rdmale = findViewById(R.id.radioButton);
        rdfemale = findViewById(R.id.radioButton2);
        rdmale.setOnCheckedChangeListener(myRD1Listener);
        rdfemale.setOnCheckedChangeListener(myRD2Listener);

    }
    //RadioButton 男
    RadioButton.OnCheckedChangeListener myRD1Listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(rdmale.isChecked()) {
                rdmale.setChecked(true);
                rdfemale.setChecked(false);
                gender = "male";
            }
        }
    };
    //RadioButton 女
    RadioButton.OnCheckedChangeListener myRD2Listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(rdfemale.isChecked()) {
                rdfemale.setChecked(true);
                rdmale.setChecked(false);
                gender = "female";
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
            datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    if((month+1) < 10 && (day) < 10) {
                        date.setText(year + "/0" + (month + 1) + "/0" + day);  //month 的索引值為0~11，因此需要加1
                    }
                    else if((month+1) < 10) {
                        date.setText(year + "/0" + (month + 1) + "/" + day);
                    }
                    else if(day < 10) {
                        date.setText(year + "/" + (month + 1) + "/0" + day);
                    }
                    else {
                        date.setText(year + "/" + (month + 1) + "/" + day);
                    }
                }
            }, year, month, dayOfMonth);
            datePickerDialog.show();
        }
    };

    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.imageButton1:  //btn回前頁
                    finish();
                    break;
                case R.id.button4:  //btn確認 --> 判斷使用者輸入的資料格式是否正確
                    Pattern p = Pattern.compile("[0-9]*");
                    Matcher m = p.matcher(edtName.getText().toString());

                    if(edtAccount.getText().toString().equals("")) {
                        Toast.makeText(RegisterActivity.this,"帳號欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(edtPassword.getText().toString().equals("")) {
                        Toast.makeText(RegisterActivity.this,"密碼欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(edtCheckpassword.getText().toString().equals("")) {
                        Toast.makeText(RegisterActivity.this,"確認密碼欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(edtName.getText().toString().equals("")) {
                        Toast.makeText(RegisterActivity.this,"姓名欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(m.matches()) {   //判斷使用者欄位中是否包含數字
                        Toast.makeText(RegisterActivity.this,"姓名欄位不可包含數字!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(edtEmail.getText().toString().equals("")) {
                        Toast.makeText(RegisterActivity.this,"Email欄位不可為空!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(!edtEmail.getText().toString().contains("@")) {
                        Toast.makeText(RegisterActivity.this,"Email欄位格式不正確!!",Toast.LENGTH_SHORT).show();
                    }
                    else if(!edtPassword.getText().toString().equals(edtCheckpassword.getText().toString()) ) {
                        Toast.makeText(RegisterActivity.this,"兩次輸入的密碼不相同!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("確認對話框")
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage("確認要新增帳戶嗎?")
                                .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        final ProgressDialog dialog2 = ProgressDialog.show(RegisterActivity.this,
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

                                        //將七個欄位傳到sqlConnect
                                        String page = "Register";    //登入頁
                                        String newAccount = edtAccount.getText().toString();
                                        String newPassword = edtPassword.getText().toString();
                                        String newName = edtName.getText().toString();
                                        String newEmail = edtEmail.getText().toString();
                                        String newGender = gender;
                                        String newBirthday;

                                        if(date.getText().toString().equals("請選擇日期")) {
                                            newBirthday = "---";
                                        }
                                        else {
                                            newBirthday = date.getText().toString();
                                        }

                                        sqlConnect sc = new sqlConnect(RegisterActivity.this);  //sql連線
                                        sc.execute(page, newAccount, newPassword,newName, newEmail, newGender, newBirthday);  //將值帶到sqlConnect

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

}

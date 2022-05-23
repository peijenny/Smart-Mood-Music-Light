package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {

    private EditText edtAcc, edtEmail;
    private Button btnCheck;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        //元件初始化
        edtAcc = findViewById(R.id.editTextAcc);
        edtEmail = findViewById(R.id.editTextEmail);

        btnCheck = findViewById(R.id.buttonCheck);
        btnCheck.setOnClickListener(myListener);

        btnBack = findViewById(R.id.imageButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!edtAcc.getText().toString().equals("") && !edtEmail.getText().toString().equals("")) {

                final ProgressDialog dialog2 = ProgressDialog.show(ForgotPasswordActivity.this,
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
                btnCheck.setEnabled(false);
                String page = "ForgotPassword";    //登入頁
                String account = edtAcc.getText().toString();
                String email = edtEmail.getText().toString();

                sendEmail se = new sendEmail(ForgotPasswordActivity.this);  //sql連線
                se.execute(page, account, email);
            }
            else
            {
                Toast.makeText(ForgotPasswordActivity.this,"帳號、Email欄位不可為空!!!", Toast.LENGTH_SHORT).show();
            }


        }
    };
}

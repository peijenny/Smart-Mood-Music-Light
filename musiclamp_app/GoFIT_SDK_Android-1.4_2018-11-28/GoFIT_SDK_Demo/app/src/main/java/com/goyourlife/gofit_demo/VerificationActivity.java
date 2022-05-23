package com.goyourlife.gofit_demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VerificationActivity extends Activity {

    String account, value;
    private EditText edtVerification;
    private Button btnCheck;
    private ImageButton btnBack;
    private TextView tvTitle, tvShowChangePass;

    private EditText edtChangePass, edtChangePassAgain;
    private Button btnChangeCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        //元件初始化
        edtVerification = findViewById(R.id.edtVerification);
        tvTitle = findViewById(R.id.tvForgotPassword2);
        btnCheck = findViewById(R.id.btnCheckAgain);
        btnCheck.setOnClickListener(myListener);

        tvShowChangePass = findViewById(R.id.tvShowChangePass);
        edtChangePass = findViewById(R.id.edtChangePass);
        edtChangePassAgain = findViewById(R.id.edtChangePassAgain);
        btnChangeCheck = findViewById(R.id.btnChangeCheck);
        btnChangeCheck.setOnClickListener(myChangeListener);
        edtChangePass.setVisibility(View.INVISIBLE);
        edtChangePassAgain.setVisibility(View.INVISIBLE);
        btnChangeCheck.setVisibility(View.INVISIBLE);
        tvShowChangePass.setVisibility(View.INVISIBLE);

        btnBack = findViewById(R.id.imageButtonBack2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //從 mysql 取得 user account & 驗證碼
        Bundle bundle = getIntent().getExtras();
        account = bundle.getString("account");
        value = bundle.getString("value");

        new AlertDialog.Builder(VerificationActivity.this).setMessage("帳號、Email正確，驗證碼已傳送!").show();
    }

    Button.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {   //確認驗證碼是否正確
            if(edtVerification.getText().toString().equals(value)) {
                tvTitle.setText("更改密碼");
                btnCheck.setBackgroundColor(0X00FFFFFF);
                btnCheck.setTextColor(0XCC0000FF);
                btnCheck.setEnabled(false);
                btnCheck.setText("驗證碼驗證成功!!!");
                edtVerification.setEnabled(false);

                edtChangePass.setVisibility(View.VISIBLE);
                edtChangePassAgain.setVisibility(View.VISIBLE);
                btnChangeCheck.setVisibility(View.VISIBLE);
                tvShowChangePass.setVisibility(View.VISIBLE);

                LinearLayout rl = (LinearLayout)findViewById(R.id.layoutR1);
                rl.setBackgroundColor(0XCCF5DDDD);
                TextView textView93 = findViewById(R.id.textView93);
                textView93.setTextColor(0XCCA7A7A7);
            }
            else {
                TextView textView93 = findViewById(R.id.textView93);
                textView93.setText("驗證碼錯誤!!!");
            }
        }
    };


    Button.OnClickListener myChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {  //確認輸入的兩次密碼都相同
            if(edtChangePass.getText().toString().equals(edtChangePassAgain.getText().toString()) && !edtChangePass.getText().toString().equals("")) {

                final ProgressDialog dialog2 = ProgressDialog.show(VerificationActivity.this,
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

                String page = "Verification";
                String changeAccount = account;
                String chaogePassword = edtChangePassAgain.getText().toString();

                sendEmail se = new sendEmail(VerificationActivity.this);
                se.execute(page, changeAccount, chaogePassword);
            }
            if(!edtChangePass.getText().toString().equals(edtChangePass.getText().toString())) {
                Toast.makeText(VerificationActivity.this,"兩次輸入的密碼不相同!", Toast.LENGTH_SHORT).show();
            }
            if(edtChangePass.getText().toString().equals("") || edtChangePassAgain.getText().toString().equals("")) {
                Toast.makeText(VerificationActivity.this,"密碼與再次輸入密碼欄位不可為空!", Toast.LENGTH_SHORT).show();
            }

        }
    };
}

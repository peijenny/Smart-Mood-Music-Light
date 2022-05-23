package com.goyourlife.gofit_demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class sendEmail extends AsyncTask<String, Void, String> {
    AlertDialog dialog;
    Context context;
    String page;  //從哪一個Activity來
    String connstr;
    String userAccount, userEmail;
    String changeAccount, changePassword;



    public sendEmail(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        dialog = new AlertDialog.Builder(context).create();
        //dialog.setTitle("＊溫馨提醒");
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        //dialog.setMessage(s);
        //dialog.show();

        //php回傳值
        if(s.contains("帳號、Email正確")) {
            String[] split = s.split("Email sent successfully!");

            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),VerificationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",userAccount);
            bundle.putString("value",split[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("帳號、Email錯誤")) {
            dialog.setMessage(s);
            dialog.show();

        }

        if(s.contains("密碼修改成功")) {
            dialog.setMessage(s);
            dialog.show();
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),LoginActivity.class);
            context.startActivity(intent);

            Toast.makeText(context.getApplicationContext(),"密碼已修改，請使用新的密碼登入!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String data = "";
        page = voids[0];

        //從不同的Activity傳來的值
        if(page == "ForgotPassword") {   //忘記密碼 --> 產生驗證碼的mail
            userAccount = voids[1];
            userEmail = voids[2];
            connstr = "http://120.96.63.63/musiclamp/ForgotPassword.php";
        }
        if(page == "Verification") {   //修改密碼 --> 產生驗證碼的mail
            changeAccount = voids[1];
            changePassword = voids[2];
            connstr = "http://120.96.63.63/musiclamp/verification.php";
        }

        // http 設定
        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));

            //post 傳給 php
            if(page == "ForgotPassword") {
                data = URLEncoder.encode("userAccount", "UTF-8") + "=" + URLEncoder.encode(userAccount,"UTF-8")
                        +"&&"+ URLEncoder.encode("userEmail", "UTF-8") + "=" + URLEncoder.encode(userEmail,"UTF-8");
            }
            if(page == "Verification") {
                data = URLEncoder.encode("changeAccount", "UTF-8") + "=" + URLEncoder.encode(changeAccount,"UTF-8")
                        +"&&"+ URLEncoder.encode("changePassword", "UTF-8") + "=" + URLEncoder.encode(changePassword,"UTF-8");
            }

            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"UTF-8"));
            String line = "";

            while ((line = reader.readLine()) != null) {
                result += line;
            }

            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }
}

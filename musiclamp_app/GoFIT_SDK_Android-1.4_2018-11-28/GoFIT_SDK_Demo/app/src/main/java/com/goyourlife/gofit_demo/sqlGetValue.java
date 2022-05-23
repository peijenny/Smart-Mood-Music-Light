package com.goyourlife.gofit_demo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class sqlGetValue extends AsyncTask<String, Void, String> {
    AlertDialog dialog;
    Context context;
    String account;  //從 Login 傳來的值
    String connstr;
    String page;
    String email;

    public sqlGetValue(Context context) { this.context = context; }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("＊溫馨提醒");
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        //dialog.setMessage(s);
        //dialog.show();

        //php回傳值
        if(s.contains("使用者資料正確")) {
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),UserInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", s);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("智慧手環查詢正確")) {
            String[] modeString = s.split("智慧手環查詢正確!!!");
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", modeString[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("手動設定查詢成功")) {
            String[] modeString = s.split("手動設定查詢成功!!!");
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),ManualActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", modeString[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("所有設定查詢成功")) {
            String[] textString = s.split("所有設定查詢成功!!!");
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),HrSettingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", textString[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("修改設定查詢成功")) {
            String[] textString = s.split("修改設定查詢成功!!!");
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),ModifyActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", textString[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("QA小知識查詢成功")) {
            String[] textString = s.split("QA小知識查詢成功!!!");
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),QAKnowledgeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", textString[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

        if(s.contains("情緒分析查詢成功")) {
            String[] textString = s.split("情緒分析查詢成功!!!");
            Intent intent = new Intent();
            //intent.setClass(context.getApplicationContext(), HeartAnalysisActivity.class);
            intent.setClass(context.getApplicationContext(), AnalysisActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            bundle.putString("value", textString[1]);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }


    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String data = "";
        page = voids[0];
        account = voids[1];

        //從不同的Activity傳來的值
        if(page == "UserInfo") {
            connstr = "http://120.96.63.63/musiclamp/userInfo.php";
        }
        if(page == "MainWatch") {
            connstr = "http://120.96.63.63/musiclamp/mainWatch.php";
        }
        if(page == "Manual") {
            connstr = "http://120.96.63.63/musiclamp/manualSelect.php";
        }
        if(page == "HrSetting") {
            connstr = "http://120.96.63.63/musiclamp/settingSelect.php";
        }
        if(page == "Modify") {
            connstr = "http://120.96.63.63/musiclamp/modifySelect.php";
        }
        if(page == "Knowledge") {
            connstr = "http://120.96.63.63/musiclamp/knowledgeSelect.php";
        }
        if(page == "HeartAnalysis") {
            connstr = "http://120.96.63.63/musiclamp/analysisSelect.php";
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
            data = URLEncoder.encode("account", "UTF-8") + "=" + URLEncoder.encode(account,"UTF-8");

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



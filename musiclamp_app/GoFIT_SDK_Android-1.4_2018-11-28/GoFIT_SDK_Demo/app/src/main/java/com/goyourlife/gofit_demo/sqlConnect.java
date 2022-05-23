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

public class sqlConnect extends AsyncTask<String, Void, String> {
    AlertDialog dialog;
    Context context;
    String page;  //從哪一個Activity來
    String account,password;  //從 Login 傳來的值
    String newAccount,newPassword,newName,newGender,newBirthday,newEmail;  //從 Register 傳來的值
    String updateAccount, updateName, updateEmail, updateGender, updateBirthday;
    String watchAccount, watchMode, watchType, watchMusic, watchLight, watchStartDate, watchStartTime, watchHr;
    String manualAccount, manualMode, manualType, manualMusic, manualLight, manualStartDate, manualStartTime;
    String modifyText, modifyAccount, modifySelectStartDate, modifySelectStartTime, modifyMode, modifyType, modifyMusic, modifyLight, modifyStartDate, modifyStartTime;
    String connstr;
    String changeAccount, changePassword;


    public sqlConnect(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("＊溫馨提醒");
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        dialog.setMessage(s);
        dialog.show();

        //php回傳值
        if(s.contains("登入成功")) {
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),SelectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("account",account);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
        if(s.contains("註冊成功")) {
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(),LoginActivity.class);
            context.startActivity(intent);
        }
    }


    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String data = "";
        page = voids[0];

        //從不同的Activity傳來的值
        if(page == "Login") {
            account = voids[1];
            password = voids[2];
            connstr = "http://120.96.63.63/musiclamp/login.php";
        }
        else if(page == "Register") {
            newAccount = voids[1];
            newPassword = voids[2];
            newName = voids[3];
            newEmail = voids[4];
            newGender = voids[5];
            newBirthday = voids[6];
            connstr = "http://120.96.63.63/musiclamp/register.php";
        }
        else if(page == "UserInfo") {
            updateAccount = voids[1];
            updateName = voids[2];
            updateEmail = voids[3];
            updateGender = voids[4];
            updateBirthday = voids[5];
            connstr = "http://120.96.63.63/musiclamp/userUpdate.php";
        }
        else if(page == "SmartWatch") {
            watchAccount = voids[1];
            watchMode = voids[2];
            watchType = voids[3];
            watchMusic = voids[4];
            watchLight = voids[5];
            watchStartDate = voids[6];
            watchStartTime = voids[7];
            watchHr = voids[8];
            connstr = "http://120.96.63.63/musiclamp/smartWatchInsert.php";
        }
        else if(page == "Manual") {
            manualAccount = voids[1];
            manualMode = voids[2];
            manualType = voids[3];
            manualMusic = voids[4];
            manualLight = voids[5];
            manualStartDate = voids[6];
            manualStartTime = voids[7];
            connstr = "http://120.96.63.63/musiclamp/manualInsert.php";
        }
        else if(page == "Modify") {
            modifyText = voids[1];
            modifyAccount = voids[2];
            modifySelectStartDate = voids[3];
            modifySelectStartTime = voids[4];
            modifyMode = voids[5];
            modifyType = voids[6];
            modifyMusic = voids[7];
            modifyLight = voids[8];
            modifyStartDate = voids[9];
            modifyStartTime = voids[10];

            if(modifyText == "update") {
                connstr = "http://120.96.63.63/musiclamp/modifyUpdate.php";
            }
            if(modifyText == "delete") {
                connstr = "http://120.96.63.63/musiclamp/modifyDelete.php";
            }
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
            if(page == "Login") {
                data = URLEncoder.encode("account", "UTF-8") + "=" + URLEncoder.encode(account,"UTF-8")
                        +"&&"+ URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");
            }
            else if(page == "Register") {
                data = URLEncoder.encode("newAccount", "UTF-8") + "=" + URLEncoder.encode(newAccount,"UTF-8")
                        +"&&"+ URLEncoder.encode("newPassword", "UTF-8") + "=" + URLEncoder.encode(newPassword,"UTF-8")
                        +"&&"+ URLEncoder.encode("newName", "UTF-8") + "=" + URLEncoder.encode(newName,"UTF-8")
                        +"&&"+ URLEncoder.encode("newEmail", "UTF-8") + "=" + URLEncoder.encode(newEmail,"UTF-8")
                        +"&&"+ URLEncoder.encode("newGender", "UTF-8") + "=" + URLEncoder.encode(newGender,"UTF-8")
                        +"&&"+ URLEncoder.encode("newBirthday", "UTF-8") + "=" + URLEncoder.encode(newBirthday,"UTF-8");
            }
            else if(page == "UserInfo") {
                data = URLEncoder.encode("updateAccount", "UTF-8") + "=" + URLEncoder.encode(updateAccount,"UTF-8")
                        +"&&"+ URLEncoder.encode("updateName", "UTF-8") + "=" + URLEncoder.encode(updateName,"UTF-8")
                        +"&&"+ URLEncoder.encode("updateEmail", "UTF-8") + "=" + URLEncoder.encode(updateEmail,"UTF-8")
                        +"&&"+ URLEncoder.encode("updateGender", "UTF-8") + "=" + URLEncoder.encode(updateGender,"UTF-8")
                        +"&&"+ URLEncoder.encode("updateBirthday", "UTF-8") + "=" + URLEncoder.encode(updateBirthday,"UTF-8");
            }
            else if(page == "SmartWatch") {
                data = URLEncoder.encode("watchAccount", "UTF-8") + "=" + URLEncoder.encode(watchAccount,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchMode", "UTF-8") + "=" + URLEncoder.encode(watchMode,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchType", "UTF-8") + "=" + URLEncoder.encode(watchType,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchMusic", "UTF-8") + "=" + URLEncoder.encode(watchMusic,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchLight", "UTF-8") + "=" + URLEncoder.encode(watchLight,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchStartDate", "UTF-8") + "=" + URLEncoder.encode(watchStartDate,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchStartTime", "UTF-8") + "=" + URLEncoder.encode(watchStartTime,"UTF-8")
                        +"&&"+ URLEncoder.encode("watchHr", "UTF-8") + "=" + URLEncoder.encode(watchHr,"UTF-8");
            }
            else if(page == "Manual") {
                data = URLEncoder.encode("manualAccount", "UTF-8") + "=" + URLEncoder.encode(manualAccount,"UTF-8")
                        +"&&"+ URLEncoder.encode("manualMode", "UTF-8") + "=" + URLEncoder.encode(manualMode,"UTF-8")
                        +"&&"+ URLEncoder.encode("manualType", "UTF-8") + "=" + URLEncoder.encode(manualType,"UTF-8")
                        +"&&"+ URLEncoder.encode("manualMusic", "UTF-8") + "=" + URLEncoder.encode(manualMusic,"UTF-8")
                        +"&&"+ URLEncoder.encode("manualLight", "UTF-8") + "=" + URLEncoder.encode(manualLight,"UTF-8")
                        +"&&"+ URLEncoder.encode("manualStartDate", "UTF-8") + "=" + URLEncoder.encode(manualStartDate,"UTF-8")
                        +"&&"+ URLEncoder.encode("manualStartTime", "UTF-8") + "=" + URLEncoder.encode(manualStartTime,"UTF-8");
            }
            else if(page == "Modify") {
                if(modifyText == "update") {
                    data = URLEncoder.encode("modifyAccount", "UTF-8") + "=" + URLEncoder.encode(modifyAccount,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifySelectStartDate", "UTF-8") + "=" + URLEncoder.encode(modifySelectStartDate,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifySelectStartTime", "UTF-8") + "=" + URLEncoder.encode(modifySelectStartTime,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifyMode", "UTF-8") + "=" + URLEncoder.encode(modifyMode,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifyType", "UTF-8") + "=" + URLEncoder.encode(modifyType,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifyMusic", "UTF-8") + "=" + URLEncoder.encode(modifyMusic,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifyLight", "UTF-8") + "=" + URLEncoder.encode(modifyLight,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifyStartDate", "UTF-8") + "=" + URLEncoder.encode(modifyStartDate,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifyStartTime", "UTF-8") + "=" + URLEncoder.encode(modifyStartTime,"UTF-8");
                }
                if(modifyText == "delete") {
                    data = URLEncoder.encode("modifyAccount", "UTF-8") + "=" + URLEncoder.encode(modifyAccount,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifySelectStartDate", "UTF-8") + "=" + URLEncoder.encode(modifySelectStartDate,"UTF-8")
                            +"&&"+ URLEncoder.encode("modifySelectStartTime", "UTF-8") + "=" + URLEncoder.encode(modifySelectStartTime,"UTF-8");
                }

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

package com.goyourlife.gofit_demo;


import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BluetoothReceiver extends BroadcastReceiver{

    String pin = "1234";  //此處為妳要連接的藍芽設備的初始密鑰，一般為1234或0000
    public BluetoothReceiver() {

    }

    //廣播接收器，當遠程藍芽設備被發現時，回調涵數onReceiver()會被執行
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction(); //得到action
        Log.e("action1=", action);
        BluetoothDevice btDevice=null;  //創建一個藍芽device對象
        // 從Intent中獲得設備對象
        btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        if(BluetoothDevice.ACTION_FOUND.equals(action)){  //發現設備
            Log.e("發現設備:", "["+btDevice.getName()+"]"+":"+btDevice.getAddress());

            //if(btDevice.getName().contains("HC-05"))//HC-05設備如果有多個，第一個搜到的那個會被嘗試。
            if(btDevice.getName().contains("ESP32"))
            {
                if (btDevice.getBondState() == BluetoothDevice.BOND_NONE) {

                    Log.e("ywq", "attempt to bond:"+"["+btDevice.getName()+"]");
                    try {
                        //通過工具類ClsUtils，調用createBond方法
                        ClsUtils.createBond(btDevice.getClass(), btDevice);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }else
                Log.e("error", "Is failed");
        }else if(action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) //再次得到的action，會等於PAIRING_REQUEST
        {
            Log.e("action2=", action);
            //if(btDevice.getName().contains("HC-05"))
            if(btDevice.getName().contains("ESP32"))
            {
                Log.e("here", "OK OK OK");

                try {

                    //1.確認配對
                    ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
                    //2.終止有序廣播
                    Log.i("order...", "isOrderedBroadcast:"+isOrderedBroadcast()+",isInitialStickyBroadcast:"+isInitialStickyBroadcast());
                    abortBroadcast();//如果沒有將廣播終止，則會出現一個一閃而過的配對框。
                    //3.調用setPin方法進行配對...
                    boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, pin);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else
                Log.e("提示訊息", "這個設備不是目標藍芽設備");

        }
    }
}





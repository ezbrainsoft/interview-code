package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class Timer extends Thread {

    public int miCount;
    Context mContext;
    Handler mHandle;

    public Timer(Context context,Handler handler) {
        miCount = 0;
        mContext = context;
        mHandle = handler;
    }
    public Timer(Context context, Handler handler, int count) {
        miCount = count;
        mContext = context;
        mHandle = handler;
    }

    public void setTimer(Handler handler){
        mHandle = handler;
    }


    @Override
    public void run() {

        if(mHandle==null) return;

        Message msg = null;

        try {
            while (miCount > -1) {
                msg = new Message();
                msg.what=1;
                msg.arg1= miCount;
                mHandle.sendMessage(msg);

                sleep(1000);
                miCount++;
            }

            mHandle.sendMessage(msg);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            miCount = 0;
        }
    }
}

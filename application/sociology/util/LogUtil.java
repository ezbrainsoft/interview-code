package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;

public class LogUtil
{
//    private static final String APP_NAME      = "";
    private static final int    STACK_NUMBUER = 3;
    private static boolean      mDebug        = BuildConfig.DEBUG;    // 최종 릴리즈시 false로
    private static boolean      mWriteToFile  = false;    // 로그를 파일로 쓰거나 쓰지 않거나..
    private static final int STACK_TRACE_INDEX = 3;
    
    private enum logtype{ verbose, info, debug, warn, error };
    
    /**
     * 로그를 logcat에 표시 할 것인지 설정 한다. 
     * @param isDebug
     */
    public static void setDebugMode(boolean isDebug){
        mDebug = isDebug;
    }

    public static void d(String str){
        if(mDebug){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[STACK_TRACE_INDEX];
            String trace = ste.getClassName() + "." + ste.getMethodName()+":" + ste.getLineNumber();
            Log.d("\\[" + ste.getClassName() + "\\]", "["+ste.getMethodName()+":" + ste.getLineNumber()+"]"+str);
        }
    }

    public static void e(String str){
        if(mDebug){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[STACK_TRACE_INDEX];
            String trace = ste.getClassName() + "." + ste.getMethodName() + ":" + ste.getLineNumber();
            Log.e("\\[" + trace + "\\]", str);
        }
    }

    public static void sendErrorLog(Context context, String msg) {
        String logText = "";

        String methodName = new Throwable().getStackTrace()[STACK_NUMBUER].getMethodName();
        int lineNumber = new Throwable().getStackTrace()[STACK_NUMBUER].getLineNumber();

        StackTraceElement ste = Thread.currentThread().getStackTrace()[STACK_TRACE_INDEX];
        String trace = ste.getClassName() + "." + ste.getMethodName() + ":" + ste.getLineNumber();

        logText = "\\[" + trace + "\\] ";

        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = dayTime.format(new Date(time));

        JsonObject jo = new JsonObject();
        jo.addProperty("user_code", BaseActivity.userCode);
        jo.addProperty("class_name", logText);
        jo.addProperty("version_name", CommonUtils.getAppVersion(context));
        jo.addProperty("debug", BuildConfig.DEBUG);
        jo.addProperty("msg", msg);
        jo.addProperty("time", str);


        RequestData.getInstance().getLogsSaveLogs(jo, new NetworkResponse<JsonObject>() {
            @Override
            public void onSuccess(Call call, JsonObject clazz) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }
        });
    }
    
    /**
     * 로그를 파일로 남길 것인지 설정 한다. 
     * @param onoff
     */
    /*public static void isDebugToFileOnOff(boolean onoff){
        mDebug = onoff;
    }

    private static void writeToFile(String level, String log)
    {
        LogToFile logToFile = null;

        try
        {
            logToFile = new LogToFile();
            logToFile.println("[" + level + "]" + log);
        }
        catch (IOException e)
        {
        }
        finally
        {
            if (logToFile != null){
                try
                {
                    logToFile.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }

    private static void log(logtype type, String message){

        if(!mDebug){
            return;
        }

        String logText = "";

        try {
            String tag = "";
            String temp = new Throwable().getStackTrace()[STACK_NUMBUER].getClassName();
            if (temp != null)
            {
                int lastDotPos = temp.lastIndexOf(".");
                tag = temp.substring(lastDotPos + 1);
            }
            String methodName = new Throwable().getStackTrace()[STACK_NUMBUER].getMethodName();
            int lineNumber = new Throwable().getStackTrace()[STACK_NUMBUER].getLineNumber();

            logText = "[" + tag + "] " + methodName + "()" + "[" + lineNumber + "]" + " >> " + message;

            if (mWriteToFile == true)
            {
                writeToFile(type.name(), logText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logText = message;
        }

//        if(type == logtype.verbose){
//            Log.v(APP_NAME, logText);
//        }else if(type == logtype.info){
//            Log.i(APP_NAME, logText);
//        }else if(type == logtype.warn){
//            Log.w(APP_NAME, logText);
//        }else if(type == logtype.error){
//            Log.e(APP_NAME, logText);
//        }else{
//            Log.d(APP_NAME, logText);
//        }

        int maxlen = 4000;
        if (logText.length() > maxlen) {
            int chunkCount = logText.length() / maxlen;     // integer division
            for (int i = 0; i <= chunkCount; i++) {
                int max = maxlen * (i + 1);
                if (max >= logText.length()) {
                	print(type, "("+ i + "/" + chunkCount + ")" + logText.substring(maxlen * i));
                } else {
                	print(type, "("+ i + "/" + chunkCount + ")" + logText.substring(maxlen * i, max));
                }
            }
        } else {
        	print(type, logText.toString());
        }
    }

    private static void print(logtype type, String logText) {

    	if(type == logtype.verbose){
            Log.v(APP_NAME, logText);
        }else if(type == logtype.info){
            Log.i(APP_NAME, logText);
        }else if(type == logtype.warn){
            Log.w(APP_NAME, logText);
        }else if(type == logtype.error){
            Log.e(APP_NAME, logText);
        }else{
            Log.d(APP_NAME, logText);
        }
    }

    public static void v(String message)
    {
        log(logtype.verbose,message);
    }

    public static void i(String message)
    {
        log(logtype.info,message);
    }

    public static void d(String message)
    {
        log(logtype.debug,message);
    }

    public static void w(String message)
    {
        log(logtype.warn,message);
    }

    public static void e(String message)
    {
        log(logtype.error,message);
    }

    public static void debugNativeHeap()
    {
        String tag = "";
        String temp = new Throwable().getStackTrace()[1].getClassName();
        if (temp != null)
        {
            int lastDotPos = temp.lastIndexOf(".");
            tag = temp.substring(lastDotPos + 1);
        }
        String methodName = new Throwable().getStackTrace()[1].getMethodName();
        int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

        Log.i(APP_NAME,
                "[" + tag + "] " + methodName + "()" + "[" + lineNumber + "]" + " >> "
                        + "NativeHeapSize=" + Debug.getNativeHeapSize()
                        + " NativeHeapFreeSize=" + Debug.getNativeHeapFreeSize()
                        + " NativeHeapAllocatedSize()=" + Debug.getNativeHeapAllocatedSize());
    }*/

}

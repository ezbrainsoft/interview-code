package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PrefHelper {

    public static void setString(Context context, String key, String value) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences.Editor editor = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE).edit();
                editor.putString(key, value);
                editor.commit();
            }
        }
    }

    public static void setString(Fragment fragment, String key, String value) {
        if (fragment != null) {
            setString(fragment.getContext(), key, value);
        }
    }

    public static String getString(Context context, String key, String defaultValue) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences pref = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE);
                return pref.getString(key, defaultValue);
            }
        }
        return defaultValue;
    }

    public static String getString(Fragment fragment, String key, String defaultValue) {
        if (fragment != null) {
            return getString(fragment.getContext(), key, defaultValue);
        }

        return defaultValue;
    }

    public static void setInt(Context context, String key, int value) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences.Editor editor = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE).edit();
                editor.putInt(key, value);
                editor.commit();
            }
        }
    }

    public static int getInt(Context context, String key, int defaultValue) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences pref = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE);
                return pref.getInt(key, defaultValue);
            }
        }

        return defaultValue;
    }

    public static void setFloat(Context context, String key, float value) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences.Editor editor = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE).edit();
                editor.putFloat(key, value);
                editor.commit();
            }
        }
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences pref = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE);
                return pref.getFloat(key, defaultValue);
            }
        }

        return defaultValue;
    }
    /*
    *
    * */
    public static void setBoolean(Context context, String key, boolean value) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences.Editor editor = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE).edit();
                editor.putBoolean(key, value);
                editor.commit();
            }
        }
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (context != null) {
            if (context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE) != null) {
                SharedPreferences pref = context.getSharedPreferences(PrefConsts.PREF_FILE_NAME, Context.MODE_PRIVATE);
                return pref.getBoolean(key, defaultValue);
            }
        }

        return defaultValue;
    }

    public static void setStringArrPref(Context context, String key, String[] values){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.length; i++) {
            a.put(values[i]);
        }
        if (values != null) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    public static String[] getStringArrPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        String[] urls = new String[0];

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                urls = new String[a.length()];
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    if(TextUtils.isEmpty(url)){
                        urls[i] = url;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public static void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    public static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}

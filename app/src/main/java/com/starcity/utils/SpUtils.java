package com.starcity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by LiuShao on 2016/2/21.
 */
public class SpUtils {

    private static SharedPreferences sp;

    public static final String SP_NAME = "StarcityApp";

    public static final String TOKEN = "token"; //用户id
    public static final String USERID = "id"; //用户id
    public static final String USERNAME = "username"; //登录名
    public static final String ACTUAL_NAME = "actualName"; //姓名
    public static final String BIRTHDAY = "birthday"; //出生日期
    public static final String EMPLOYER = "employer"; //工作单位
    public static final String PHONE = "phone"; //联系方式
    public static final String CERTIFICATE_CODE = "certificateCode"; //身份证号码
    private static final String regularEx = "#";

    public static void setSharedPerference(Context context) {
        if (SpUtils.sp == null) {
            SpUtils.sp = context.getSharedPreferences(SpUtils.SP_NAME, context.MODE_PRIVATE);
        }
    }

    public static String[] getSharedPreference(String key) {
        String values = sp.getString(key, "");
        return values.split(regularEx);
    }

    public static void setSharedPreference(String key, String[] values) {

        StringBuffer sb = new StringBuffer();

        if (values != null) {
            for (String value : values) {
                sb.append(value);
                sb.append(regularEx);
            }

            sp.edit().putString(key, sb.toString()).commit();
        }
    }

//    public static void saveString(/*Context context,*/
//                                  String key,
//                                  String value){
////        if(sp==null)
////            sp = context.getSharedPreferences(SP_NAME,0);
//
//        sp.edit().putString(key, value).commit();
//    }

    public static void saveString(String key,
                                  String value,
                                  boolean null2empty) {

        if (null2empty && (value==null || "null".equals(value)) ) {
            sp.edit().putString(key, "").commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public static String getString(/*Context context,*/
            String key,
            String defValue) {
//        if (sp == null)
//            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getString(key, defValue);
    }

    public static void saveInt(Context context,
                               String key,
                               int value) {
//        if(sp==null)
//            sp = context.getSharedPreferences(SP_NAME,0);

        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context,
                             String key, int value) {
//        if(sp==null)
//            sp=context.getSharedPreferences(SP_NAME,0);

        return sp.getInt(key, value);
    }

    public static void saveBoolean(Context context,
                                   String key, boolean value) {
//        if(sp == null)
//            sp = context.getSharedPreferences(SP_NAME,0);

        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context,
                                     String key, boolean defValue) {
//        if(sp == null)
//            sp = context.getSharedPreferences(SP_NAME,0);

        return sp.getBoolean(key, defValue);
    }


    public static String SceneList2String(List SceneList) {
        try {
            // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 然后将得到的字符数据装载到ObjectOutputStream
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
            objectOutputStream.writeObject(SceneList);
            // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
            String SceneListString = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            // 关闭objectOutputStream
            objectOutputStream.close();

            return SceneListString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List String2SceneList(String SceneListString) {
        try {
            byte[] mobileBytes = Base64.decode(SceneListString.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List SceneList = (List) objectInputStream.readObject();
            objectInputStream.close();
            return SceneList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

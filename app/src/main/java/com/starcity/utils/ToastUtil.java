package com.starcity.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ThinkPad on 2018/3/25.
 */

public class ToastUtil {

    Context context;

    public ToastUtil(Context context){
        this.context = context;
    }

    public void Show(String strMsg){
        Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
    }
}

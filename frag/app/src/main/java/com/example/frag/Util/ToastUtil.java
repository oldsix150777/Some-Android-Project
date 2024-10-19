package com.example.frag.Util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void show(Context context, String text) {
        // 创建一个Toast对象
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        // 显示Toast
        toast.show();
    }
}

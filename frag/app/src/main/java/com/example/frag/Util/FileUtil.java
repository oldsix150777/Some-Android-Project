package com.example.frag.Util;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    //把字符串保存到指定路径的文本文件
    public static void saveText(String path,String txt) {
        BufferedWriter os = null;
        try {
            os = new BufferedWriter(new FileWriter(path));
            os.write(txt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //从指定路径的文本文件中读取字符串
    public static String openText(String path){
        BufferedReader is=null;
        StringBuilder sb=new StringBuilder();
        try {
            is = new BufferedReader(new FileReader(path));
            String line=null;
            while((line=is.readLine())!=null){
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    //把位图数据保存到指定路径的图片文件
    public static void saveImage(String path, Bitmap bitmap){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


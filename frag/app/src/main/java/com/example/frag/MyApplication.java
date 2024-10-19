package com.example.frag;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


import com.example.frag.Util.FileUtil;
import com.example.frag.Util.SharedUtil;
import com.example.frag.database.ShoppingDBHelper;
import com.example.frag.entity.GoodsInfo;


import java.io.File;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication mApp;
    public static MyApplication getInstance(){
        return mApp;
    }


    //购物车中的商品总数量
    public int goodsCount;


    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
        //初始化商品信息
        initGoodsInfo();
    }

    private void initGoodsInfo() {
        //获取共享参数保存的是否首次打开参数
        boolean isFirst=SharedUtil.getInstance(this).readBoolean( "first",true);
        //获取当前pp的私有下载路径
        String directory=getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        if(isFirst){
            //模拟网络图片下载
            List<GoodsInfo> list =GoodsInfo.getDefaultList();
            for (GoodsInfo info : list) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
                String path=directory+info.id+"jpg";
                // 往存储卡保存商品图片
                FileUtil.saveImage(path,bitmap);
                //回收位图对象
                bitmap.recycle();
                info.picPath=path;
            }
            // 打开数据库，把商品信息插入到表中
            ShoppingDBHelper dbHelper=ShoppingDBHelper.getInstance(this);
            dbHelper.openWriteLink();
            dbHelper.insertGoodsInfos(list);
            dbHelper.closeLink();

            //把是否首次打开写入共享参数
            SharedUtil.getInstance(this).writeBoolean("first",false);
        }
    }

/*    //获取书籍数据库的实例
    public BookDataBase getBookDB(){
        return bookDatabase;
    }*/

}




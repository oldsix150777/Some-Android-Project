package com.example.frag.entity;

import com.example.frag.R;
import com.example.frag.database.ShoppingDBHelper;

import java.util.ArrayList;

public class GoodsInfo {

    public int id;
    //名称
    public String name;
    //描述
    public String description;
    //价格
    public float price;
    //大图的保存路径
    public String picPath;
    //大图的资源编号
    public int pic;

    // 声明一个手机商品的名称数组
    private static String[] mNameArray={
            "iPhone11","Mate30","小米10","OPPo Reno12","vivo X100","荣耀x50","Reno4 Pro","Reno4","Reno4 SE"};
    //声明一个手机商品的描述数组
    private static String[] mDescArray={
            "Apple iPhone11 256GB 绿色 4G全网通手机",
            "华为 HUAWEI Mate30 8GB+256GB 丹霞橙 5G全网通 全面屏手机",
            "小米 MI10 8GB+128GB 钛银黑 5G手机 游戏拍照手机",
            "oPPo Reno12 8GB+128GB 蓝色星夜 双模5G 拍照游戏智能手机",
            "vivo X100 8GB+128GB 绯云 5G全网通 美颜拍照手机",
            "荣耀x50 8GB+128GB 蝶羽红 5G芯片 自拍全面屏手机",
            "Reno4 Pro 8GB+128GB 星河入梦 5G全网通 4800万超清四摄 6.5英寸升降全面屏 4500mAh大电池 4800万超清四摄 6.5英寸升降全面屏 4500mAh大电池",
            "Reno4 8GB+128GB 极光色 5G全网通 4800万超清四摄 6.5英寸升降全面屏 4500mAh大电池",
            "Reno4 SE 8GB+128GB 星夜紫 5G全网通 4800万超清四摄 6.5英寸升降全面屏 4500mAh大电池"
    };
    //声明一个手机商品的价格数组
    private static float[] mPriceArray={6299, 4999, 3999, 2999, 2998, 2399, 2799, 2299, 1999};
    //声明一个手机商品的大图数组
    private static int[] mPicArray={
            R.drawable.iphone,
            R.drawable.huawei,R.drawable.xiaomi,
            R.drawable.oppo,R.drawable.vivo,R.drawable.rongyao,
            R.drawable.reno4_pro,R.drawable.reno4_pro,R.drawable.reno4_se
    };
    private static String[] mPicPathArray = {
            "iphone",
            "huawei",
            "xiaomi",
            "oppo",
            "vivo",
            "rongyao",
            "reno4_pro",
            "reno",
            "reno4_se"
    };
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i=0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.id = i;
            info.name = mNameArray[i];
            info.description = mDescArray[i];
            info.price = mPriceArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }


}


package com.example.frag.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frag.MyApplication;
import com.example.frag.R;
import com.example.frag.Util.ToastUtil;
import com.example.frag.database.ShoppingDBHelper;
import com.example.frag.entity.GoodsInfo;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_count;
    private ImageView iv_goods_pic;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ShoppingDBHelper mDBHelper;
    private int mGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        tv_title = findViewById(R.id.tv_title);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        tv_count=findViewById(R.id.tv_count);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);

        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        mDBHelper = ShoppingDBHelper.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetail();
    }

    private void showDetail() {
        //获取上一个页面传来的商品编号
        mGoodsId = getIntent().getIntExtra("goods_id", 0);
        if (mGoodsId > 0) {
            //根据商品编号查询商品数据库中的商品记录
            GoodsInfo info = mDBHelper.queryGoodsInfoById(mGoodsId);
            tv_title.setText(info.name);
            iv_goods_pic.setImageURI(Uri.parse(info.picPath));
            tv_goods_price.setText(String.valueOf((int) info.price));
            tv_goods_desc.setText(info.description);

        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:
                //跳转到购物车页面
                Intent intent=new Intent(this,ShoppingCartActivity.class);

                //设置启动标志，避免多次返回同一页面的
                /*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                startActivity(intent);
                break;
            case R.id.btn_add_cart:
                addToCart(mGoodsId);
                break;
        }

    }

    private void addToCart(int goodsId) {
        //购物车数量+1
        int count=++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));

        mDBHelper.insertCartInfo(goodsId);

        ToastUtil.show(this,"已添加到购物车");
    }


}


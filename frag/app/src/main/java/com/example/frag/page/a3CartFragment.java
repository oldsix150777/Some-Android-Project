package com.example.frag.page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.frag.MyApplication;
import com.example.frag.R;
import com.example.frag.Util.ToastUtil;
import com.example.frag.database.ShoppingDBHelper;
import com.example.frag.entity.CartInfo;
import com.example.frag.entity.GoodsInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class a3CartFragment extends Fragment implements View.OnClickListener {

    private TextView tv_count;
    private LinearLayout ll_cart;
    private ShoppingDBHelper mDBHelper;
    //声明一个购物车的商品信息列表
    private List<CartInfo> mCartList;
    //声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，这样不用每一次都去查询数据库
    private Map<Integer, GoodsInfo> mGoodsMap = new HashMap<>();
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a3cart, container, false);

        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("购物车");

        ll_cart = view.findViewById(R.id.ll_cart);
        tv_total_price = view.findViewById(R.id.tv_total_price);

        tv_count = view.findViewById(R.id.tv_count);
        mDBHelper = ShoppingDBHelper.getInstance(getActivity());
        view.findViewById(R.id.iv_back).setOnClickListener(this);

        view.findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);
        view.findViewById(R.id.btn_settle).setOnClickListener(this);

        ll_empty = view.findViewById(R.id.ll_empty);
        ll_content = view.findViewById(R.id.ll_content);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showCart();
        showCount();
    }

    // 封装查询购物车商品列表的方法
    private List<CartInfo> queryCartInfo() {
        try {
            return mDBHelper.queryAllCartInfo();
        } catch (Exception e) {
            Log.e("a3CartFragment", "Error querying cart info: " + e.getMessage());
            return null;
        }
    }

    // 展示购物车中的商品列表
    private void showCart() {
        // 移除下面的所有子视图
        ll_cart.removeAllViews();
        // 查询购物车数据库中所有的商品记录
        mCartList = queryCartInfo();
        if (mCartList == null || mCartList.size() == 0) {
            return;
        }
        for (CartInfo info : mCartList) {
            // 根据商品编号查询商品数据库中的商品记录
            GoodsInfo goods = queryGoodsInfoById(info.goodsId);
            if (goods!= null) {
                mGoodsMap.put(info.goodsId, goods);
                // 获取布局文件 item_cart.xml 的根视图
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cart, null);
                ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView tv_desc = view.findViewById(R.id.tv_desc);
                TextView tv_count = view.findViewById(R.id.tv_count);
                TextView tv_price = view.findViewById(R.id.tv_price);
                TextView tv_sum = view.findViewById(R.id.tv_sum);
                // 给控件设置内容
                iv_thumb.setImageURI(Uri.parse(goods.picPath));
                tv_name.setText(goods.name);
                tv_desc.setText(goods.description);
                tv_count.setText(String.valueOf(info.count));
                tv_price.setText(String.valueOf((int) goods.price));
                // 总价
                tv_sum.setText(String.valueOf((int) (info.count * goods.price)));

                // 给商品行添加长按事件。长按商品行就删除该商品
                view.setOnLongClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("是否从购物车删除" + goods.name + "?");
                    builder.setPositiveButton("是", (dialog, which) -> {
                        // 移除当前视图
                        ll_cart.removeView(v);
                        // 删除商品
                        deleteGoods(info);
                    });
                    builder.setNegativeButton("否", null);
                    builder.create().show();
                    return true;
                });
                // 往购物车列表添加商品行
                ll_cart.addView(view);

                view.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), ShoppingDetailActivity.class);
                    intent.putExtra("goods_id", goods.id);
                    startActivity(intent);
                });
            }
        }

        // 重新计算购物车中的商品总金额
        refreshTotalPrice();
    }

    // 封装根据商品编号查询商品信息的方法
    private GoodsInfo queryGoodsInfoById(int goodsId) {
        try {
            return mDBHelper.queryGoodsInfoById(goodsId);
        } catch (Exception e) {
            Log.e("a3CartFragment", "Error querying goods info by id: " + e.getMessage());
            return null;
        }
    }

    private void deleteGoods(CartInfo info) {
        MyApplication.getInstance().goodsCount -= info.count;
        // 从购物车的数据库中删除商品
        boolean success = mDBHelper.deleteCartInfoByGoodsId(info.goodsId);
        if (!success) {
            Log.e("a3CartFragment", "Failed to delete cart info for goodsId: " + info.goodsId);
        }
        // 从购物车列表中删除商品
        CartInfo removed = null;
        for (CartInfo cartInfo : mCartList) {           // 不能在遍历中直接移除
            if (cartInfo.goodsId == info.goodsId) {
                removed = cartInfo;
                break;
            }
        }
        if (removed!= null) {
            mCartList.remove(removed);
        }
        // 显示最新商品数量
        showCount();
        ToastUtil.show(getActivity(), "已从购物车删除" + mGoodsMap.get(info.goodsId).name);
        mGoodsMap.remove(info.goodsId);
        // 刷新购物车中所有商品的总金额
        refreshTotalPrice();
    }

    // 显示购物车图标中的商品数量
    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        // 购物车中没有商品，显示"空空如也"
        if (MyApplication.getInstance().goodsCount == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            ll_cart.removeAllViews();
        } else {
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }

    }

    // 重新计算购物车中的商品总金额
    private void refreshTotalPrice() {
        int totalPrice = 0;
        for (CartInfo info : mCartList) {
            GoodsInfo goods = mGoodsMap.get(info.goodsId);
            if (goods!= null) {
                totalPrice += goods.price * info.count;
            }
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.btn_shopping_channel:
                // 跳回商品页面
                Intent intent = new Intent(getActivity(), ShoppingChannelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_clear:
                // 清空购物车
                boolean cleared = mDBHelper.deleteAllCartInfo();
                if (cleared) {
                    MyApplication.getInstance().goodsCount = 0;
                    showCount();
                    ToastUtil.show(getActivity(), "购物车已清空");
                } else {
                    Log.e("a3CartFragment", "Failed to clear the cart");
                }
                break;
            case R.id.btn_settle:
                // 点击结算
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("结算商品");
                builder.setMessage("客官抱歉，支付功能尚未开通，请下次再来");
                builder.setPositiveButton("我知道了", null);
                builder.create().show();
                break;
        }
    }
}
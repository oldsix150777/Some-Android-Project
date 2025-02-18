package com.example.bilibili.ui.test.mvp.presenter;

import android.util.Log;

import com.bilibili.model.api.WeChatApis;
import com.bilibili.model.bean.WeiXinJingXuanBean;
import com.bilibili.ui.test.mvp.contract.MvpStructureContract;
import com.common.base.AbsBasePresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jiayiyang on 17/3/25.
 */

public class MvpStructurePresenter extends AbsBasePresenter<MvpStructureContract.View> implements MvpStructureContract.Presenter {

    private static final String TAG = MvpStructurePresenter.class.getSimpleName();

    private WeChatApis weChatApis;

    private int num = 1;

    @Inject
    public MvpStructurePresenter(WeChatApis weChatApis) {
        this.weChatApis = weChatApis;
    }


    @Override
    public void loadData() {
        weChatApis.getWeiXinJingXuan(WeChatApis.KEY, 25, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeiXinJingXuanBean>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                        registerRx(disposable);
                        mView.setRefreshing();
                    }

                    @Override
                    public void onNext(@NonNull WeiXinJingXuanBean weiXinJingXuanBean) {
                        mView.updateData(weiXinJingXuanBean.getNewslist());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("misery", "onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("misery", "onCompleted");
                    }
                });
    }

    @Override
    public void releaseData() {

    }

}

package com.song.tasty.common.core.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.song.tasty.common.core.AppManager;

/**
 * @author lichen
 * @date ：2019-07-03 22:18
 * @email : 196003945@qq.com
 * @description :
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        Intent intent = getIntent();
        if (intent != null) {
            parseIntent(intent);
        }
        createView(getLayoutResId(), savedInstanceState);
        initView();
        initData();

    }

    protected abstract int getLayoutResId();

    public void createView(int layoutResId, @Nullable Bundle savedInstanceState) {
        setContentView(layoutResId);
    }


    public void parseIntent(@Nullable Intent intent) {

    }

    public void initView() {

    }

    public void initData() {

    }


}

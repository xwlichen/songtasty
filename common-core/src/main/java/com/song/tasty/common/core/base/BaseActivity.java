package com.song.tasty.common.core.base;

import android.content.Intent;
import android.os.Bundle;

import com.song.tasty.common.core.AppManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    protected void createView(int layoutResId, @Nullable Bundle savedInstanceState) {
        setContentView(layoutResId);
    }


    protected void parseIntent(@Nullable Intent intent) {

    }

    protected void initView() {

    }

    protected void initData() {

    }


}

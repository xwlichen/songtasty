package com.song.tasty.common.app.flutter.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.android.FlutterFragment;

public class FlutterCommonFragment extends FlutterFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT_WATCH){
            container.requestApplyInsets();

        }else{
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

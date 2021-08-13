package com.song.tasty.common.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.MultiTypeAdapter;
import com.song.tasty.common.app.R;
import com.song.tasty.common.app.adapter.MusicBottomNavItemViewBinder;
import com.song.tasty.common.app.music.bean.MusicBean1;

import java.util.ArrayList;
import java.util.List;


/**
 * @date : 2019-08-20 09:57
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicBottomNavView extends FrameLayout {

    private ImageView ivMusicList;
    private RecyclerView rvMusic;


    private MultiTypeAdapter adapter;
    private ArrayList items;


    public MusicBottomNavView(@NonNull Context context) {
        this(context, null);
    }

    public MusicBottomNavView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicBottomNavView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_music_bottom_nav, this);

        ivMusicList = view.findViewById(R.id.ivMusicList);
        rvMusic = view.findViewById(R.id.rvMusic);

        adapter = new MultiTypeAdapter();
        adapter.register(MusicBean1.class, new MusicBottomNavItemViewBinder());
        items = new ArrayList<>();

        rvMusic.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        rvMusic.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvMusic);


        List<MusicBean1> list = new ArrayList<>();
//        MusicBean1 musicBean1 = new MusicBean1("反方向的钟",
//                "https://x128.bailemi.com/attachment/20190820/ApgDZcy9S2IbCkxGK7lm.jpg",
//                "周杰伦");
//
//        MusicBean1 musicBean2 = new MusicBean1("七里香",
//                "https://x128.bailemi.com/attachment/20190820/fkDdEmN26Hn4PezaoCM8.png",
//                "周杰伦");
//
//
//        MusicBean1 musicBean3 = new MusicBean1("发如雪",
//                "https://x128.bailemi.com/attachment/20190819/JELqBZpwo9Mkt86TDUbl.jpg",
//                "周杰伦");
//
//
//        MusicBean1 musicBean4 = new MusicBean1("本草纲目",
//                "https://x128.bailemi.com/attachment/20190819/doaJD6FfGniZ8qgMUzWl.jpg",
//                "周杰伦");
//
//        list.add(musicBean1);
//        list.add(musicBean2);
//        list.add(musicBean3);
//        list.add(musicBean4);

        items.addAll(list);
        adapter.setItems(items);
        adapter.notifyDataSetChanged();


    }
}

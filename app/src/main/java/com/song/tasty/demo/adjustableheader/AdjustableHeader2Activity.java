package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.song.tasty.app.R;

import java.util.ArrayList;

public class AdjustableHeader2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjustable_header2);

        final ImageView bg = findViewById(R.id.imageBg);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdjustableHeader2Activity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        String[] titles = new String[]{"最新", "热门", "我的"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            fragments.add(new TabFragment());
            tabLayout.addTab(tabLayout.newTab());
        }
        tabLayout.setupWithViewPager(viewPager, false);
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        for (int i = 0; i < titles.length; i++) {
            tabLayout.getTabAt(i).setText(titles[i]);

        }
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, AdjustableHeader2Activity.class);
        context.startActivity(intent);
    }
}

package com.song.tasty.demo.adjustableheader;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.song.tasty.app.R;

import java.util.ArrayList;

public class AdjustableHeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main_1);
        final Button button = findViewById(R.id.button);
        final AdjustableHeaderLinearLayout nestedScrollParentLinearLayout = findViewById(R.id.parent);
        button.setOnClickListener(new View.OnClickListener() {
            boolean useDragOver = true;
            @Override
            public void onClick(View v) {
                if (useDragOver) {
                    useDragOver = false;
                    button.setText("use dragover");
                    nestedScrollParentLinearLayout.setNeedDragOver(useDragOver);
                } else {
                    useDragOver = true;
                    button.setText("stop dragover");
                    nestedScrollParentLinearLayout.setNeedDragOver(useDragOver);
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdjustableHeader2Activity.launch(AdjustableHeaderActivity.this);
            }
        });
        final ImageView bg = findViewById(R.id.imageBg);
        nestedScrollParentLinearLayout.setHeaderScrollListener(new AdjustableHeaderLinearLayout.HeaderScrollListener() {
            @Override
            public void onScroll(int dy) {
            }

            @Override
            public void onHeaderTotalHide() {
                Toast.makeText(AdjustableHeaderActivity.this, "header hide", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHeaderTotalShow() {
                Toast.makeText(AdjustableHeaderActivity.this, "header show", Toast.LENGTH_SHORT).show();
            }
        });
        nestedScrollParentLinearLayout.setHeaderBackground(bg);
        nestedScrollParentLinearLayout.setMaxHeaderHeight((int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics()) + 0.5));
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdjustableHeaderActivity.this, "I am textView", Toast.LENGTH_SHORT).show();
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

}

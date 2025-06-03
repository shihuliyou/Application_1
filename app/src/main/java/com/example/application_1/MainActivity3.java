package com.example.application_1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity3 extends AppCompatActivity {

    private static final int PAGE_COUNT = 3;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private final String[] TAB_TITLES = { "第一页", "第二页", "第三页" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        viewPager  = findViewById(R.id.viewPager);
        tabLayout  = findViewById(R.id.tabLayout);

        // 1. 给 ViewPager2 设置 Adapter
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));

        // 2. 通过 TabLayoutMediator 关联 TabLayout 和 ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        // 设置每个 Tab 的标题
                        tab.setText(TAB_TITLES[position]);
                    }
                }
        ).attach();
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull AppCompatActivity fa) {
            super(fa);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 传给 Fragment 的位置参数（1、2、3）
            return PageFragment.newInstance(position + 1);
        }
        @Override
        public int getItemCount() {
            return PAGE_COUNT;
        }
    }
}

package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.previous.PreTestBookMarkCategoryActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdiomListActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    public String sqClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pasttest_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        sqClass = getIntent().getStringExtra("sq_class");
        switch (sqClass){
            case "V":
                tvTitle.setText("표준어");
                break;
            case "W":
                tvTitle.setText("외래어");
                break;
            case "X":
                tvTitle.setText("사자성어");
                break;
        }

        int currentPosition = getIntent().getIntExtra("position", 0);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new WordFragment());
        fragments.add(new QuestionFragment());


        PreTestMainAdapter adapter = new PreTestMainAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        tabLayout.getTabAt(0).setText("어휘");
        tabLayout.getTabAt(1).setText("문제");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        vp.setCurrentItem(currentPosition);
    }


    @OnClick({R.id.btnBack, R.id.btn_close})
    void onFinish(){
        finish();
    }

    private class PreTestMainAdapter extends FragmentPagerAdapter {
        List<Fragment> listFragments;


        public PreTestMainAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.listFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }
    }

    @OnClick(R.id.btn_bookmark)
    void btnBookmark(){
        Intent i = new Intent(this, PreTestBookMarkCategoryActivity.class);
        startActivity(i);

    }
}

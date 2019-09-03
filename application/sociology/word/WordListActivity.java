package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.day.mainDay.DayKoreanQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.word.bookmark.WordBookMarkActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordListActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.btn_close)
    ImageButton btnClose;
    @BindView(R.id.btn_bookmark)
    ImageButton btnBookmark;
    @BindView(R.id.ll_sound)
    LinearLayout llSound;

    public String sqClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pasttest_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        btnClose.setVisibility(View.VISIBLE);
        btnBookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_word_bm_on));

        sqClass = getIntent().getStringExtra("sq_class");
        switch (sqClass) {
            case MODE.SQ_CLASS_V:
                tvTitle.setText("표준어");
                break;
            case MODE.SQ_CLASS_W:
                tvTitle.setText("외래어");
                break;
            case MODE.SQ_CLASS_X:
                tvTitle.setText("사자성어");
                break;
            case MODE.SQ_CLASS_Y:
                tvTitle.setText("한자어");
                break;
            case MODE.SQ_CLASS_F:
                tvTitle.setText("고유어");
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
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        vp.setCurrentItem(currentPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CommonUtils.isKoeanServiceRunning(this)){
            llSound.setVisibility(View.VISIBLE);
        }else{
            llSound.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_sound)
    void clickSound(){
        stopService(new Intent(this, WordTTSService.class));
        llSound.setVisibility(View.GONE);
    }

    @OnClick({R.id.btnBack, R.id.btn_close})
    void onFinish() {
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
    void btnBookmark() {

        Intent intent = new Intent(WordListActivity.this, WordBookMarkActivity.class);
        intent.putExtra("sq_class", sqClass);
        intent.putExtra("position", vp.getCurrentItem());
        startActivity(intent);

    }
}

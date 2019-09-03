package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.view.WithBottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordMainActivity extends BaseActivity {
    @BindView(R.id.btnBack)
    ImageButton btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.ll_sound)
    LinearLayout llSound;

    WordMainAdapter adapter;
    public static boolean withVoca;
    private String lastKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        tvTitle.setText("어휘학습");

        LinearLayoutManager layoutManager = new LinearLayoutManager(WordMainActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);

        adapter = new WordMainAdapter();
        rv.setAdapter(adapter);

        if (!BaseActivity.isCheckWithDialog) {
            WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
            bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        lastKind = PrefHelper.getString(this, PrefConsts.INTENT_REQUEST_WORD_KIND_LIST_APPDAY, "");
        adapter.notifyDataSetChanged();

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

    public class WordMainAdapter extends RecyclerView.Adapter<WordMainAdapter.ViewHolder> implements View.OnClickListener {
        String sqClass = "";

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            LinearLayout root;
            ImageView ivCheckView;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv_title);
                ivCheckView = itemView.findViewById(R.id.iv_check_view);
                root = (LinearLayout) itemView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(WordMainActivity.this).inflate(R.layout.item_rv_list, parent, false);
            return new WordMainAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tv.setText(MODE.WORD_MENU[position]);


            switch (position) {
                case 0:
                    sqClass = MODE.SQ_CLASS_V;//표준어
                    break;
                case 1:
                    sqClass = MODE.SQ_CLASS_W;//외래어
                    break;
                case 2:
                    sqClass = MODE.SQ_CLASS_X;//사자성어
                    break;
                case 3:
                    sqClass = MODE.SQ_CLASS_Y;//한자어
                    break;
                case 4:
                    sqClass = MODE.SQ_CLASS_F;//고유어
                    break;
            }

            if(sqClass.equals(lastKind)){
                holder.ivCheckView.setVisibility(View.VISIBLE);
            }else{
                holder.ivCheckView.setVisibility(View.GONE);
            }


            holder.root.setTag(position);
            holder.root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();

            String sqClass = "";
            switch (position) {
                case 0:
                    sqClass = MODE.SQ_CLASS_V;//표준어
                    break;
                case 1:
                    sqClass = MODE.SQ_CLASS_W;//외래어
                    break;
                case 2:
                    sqClass = MODE.SQ_CLASS_X;//사자성어
                    break;
                case 3:
                    sqClass = MODE.SQ_CLASS_Y;//한자어
                    break;
                case 4:
                    sqClass = MODE.SQ_CLASS_F;//고유어
                    break;
            }

            if (!TextUtils.isEmpty(sqClass)) {
                Intent intent = new Intent(WordMainActivity.this, WordListActivity.class);

                if(sqClass.equals(MODE.SQ_CLASS_F)){
                    intent.putExtra("sq_class", "F");
                }else{
                    intent.putExtra("sq_class", sqClass);
                }

                startActivity(intent);
            }
        }

        @Override
        public int getItemCount() {
            return MODE.WORD_MENU.length;
        }
    }
}

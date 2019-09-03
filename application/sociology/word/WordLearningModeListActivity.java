package com.jlesoft.civilservice.koreanhistoryexam9.word;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.MainActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.PrefConsts;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.englishWord.EnglishWordDayStudyActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.bookmark.EverydayMainExpressionBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.bookmark.EverydayMainPhraseBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.everydayEnglish.bookmark.EverydayMainProverbBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.network.NetworkResponse;
import com.jlesoft.civilservice.koreanhistoryexam9.network.RequestData;
import com.jlesoft.civilservice.koreanhistoryexam9.util.CommonUtils;
import com.jlesoft.civilservice.koreanhistoryexam9.util.DialogUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.util.MODE;
import com.jlesoft.civilservice.koreanhistoryexam9.util.PrefHelper;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.view.WithBottomSheetDialog;
import com.jlesoft.civilservice.koreanhistoryexam9.word.adapter.WordBookmarkListAdapter;
import com.jlesoft.civilservice.koreanhistoryexam9.word.bookmark.WordBookMarkActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.word.bookmark.WordOXBookmarkQuizActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordBookmarkQuestionDao;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordQuestionDao;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;


public class WordLearningModeListActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.cb_learning)
    AppCompatCheckBox cbLearning;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tv_sound)
    TextView tvSound;
    @BindView(R.id.btnMenu)
    ImageButton btnMenu;

    WordLearingAdapter adapter;
    boolean isLearning;
    String sqClass;
    ArrayList<WordQuestionDao.WordQuestionItem> dataArr = new ArrayList<WordQuestionDao.WordQuestionItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_learning_mode_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        dataArr.addAll((ArrayList<WordQuestionDao.WordQuestionItem>) getIntent().getSerializableExtra("data"));
        sqClass = dataArr.get(0).getSqClass();

        if (sqClass.equals("V")) {
            tvTitle.setText("표준어");
        } else if (sqClass.equals("W")) {
            tvTitle.setText("외래어");
        } else if (sqClass.equals("X")) {
            tvTitle.setText("사자성어");
        } else if (sqClass.equals("Y")) {
            tvTitle.setText("한자어");
        } else if (sqClass.equals("F")) {
            tvTitle.setText("고유어");
        }

        btnMenu.setVisibility(View.VISIBLE);
        adapter = new WordLearingAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        isLearning = PrefHelper.getBoolean(this, PrefConsts.WORD_LEARNING_CHECK, true);
        if (isLearning) {
            cbLearning.setChecked(true);
        } else {
            cbLearning.setChecked(false);
        }
    }

    @OnClick(R.id.btnMenu)
    void clickMenu() {
        PopupMenu popup = new PopupMenu(this, btnMenu);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.menu_default, menu);

        menu.findItem(R.id.toWithDialog).setVisible(true);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {
                    case R.id.toMain:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(WordLearningModeListActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toSetting:
                        EnglishWordDayStudyActivity.dataArr.clear();
                        EverydayMainExpressionBookmarkFragment.expressionArr.clear();
                        EverydayMainPhraseBookmarkFragment.phraseArr.clear();
                        EverydayMainProverbBookmarkFragment.proverbArr.clear();

                        intent = new Intent(WordLearningModeListActivity.this, MainActivity.class);
                        intent.putExtra("moveToSetting", true);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();
                        break;

                    case R.id.toTextSize:
                        showTextSizeSettingDialog();
                        break;

                    case R.id.toWithDialog:
                        WithBottomSheetDialog bottomSheetDialog = WithBottomSheetDialog.getInstance();
                        bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                        break;

                }
                return false;
            }
        });
        popup.show();
    }

    public void showTextSizeSettingDialog() {
        DialogUtil.showTextSizeDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.setAdapter(null);
                adapter = new WordLearingAdapter();
                rv.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.isKoeanServiceRunning(this)) {
            tvSound.setText("그만듣기");
        } else {
            tvSound.setText("전체듣기");
        }
    }

    @OnClick(R.id.btn_sound)
    void clickSound() {
        if (!CommonUtils.isKoeanServiceRunning(this)) {
            Intent intent = new Intent(this, WordSoundSettingActivity.class);
            intent.putExtra("sq_class", sqClass);
            startActivityForResult(intent, PrefConsts.REQUEST_WORD_LEARNING_SOUND_SETTING_ACTIVITY);

        } else {
            stopService(new Intent(this, WordTTSService.class));
            tvSound.setText("전체듣기");
        }
    }

    @OnCheckedChanged(R.id.cb_learning)
    void checkLearning(CompoundButton button, boolean checked) {
        if (checked) {
            isLearning = true;
            PrefHelper.setBoolean(this, PrefConsts.WORD_LEARNING_CHECK, true);
        } else {
            isLearning = false;
            PrefHelper.setBoolean(this, PrefConsts.WORD_LEARNING_CHECK, false);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btnBack, R.id.btn_close})
    void clickFinish() {
        finish();
    }

    public class WordLearingAdapter extends RecyclerView.Adapter<WordLearingAdapter.ViewHolder> {
        private View v;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout root, llWord, llTFWord, llFKeyword;
            public RelativeLayout rlBookmark;
            public ImageView ivBookmark;
            public TextView tvTKeyword;
            public TextView tvFKeyword;
            public TextView tvComment, tvIdiom, tvWContents;
            public LinearLayout llSino;
            public TextView tvSinoSqiRCon, tvSinoSqiWCon;


            public ViewHolder(View view) {
                super(view);
                llWord = view.findViewById(R.id.ll_word);
                llTFWord = view.findViewById(R.id.ll_tf_word);
                rlBookmark = view.findViewById(R.id.rl_bookmark);
                ivBookmark = view.findViewById(R.id.iv_bookmark);
                tvWContents = view.findViewById(R.id.tv_w_contents);
                tvTKeyword = view.findViewById(R.id.tv_t_keyword);
                llFKeyword = view.findViewById(R.id.ll_f_keyword);
                tvFKeyword = view.findViewById(R.id.tv_f_keyword);
                tvComment = view.findViewById(R.id.tv_comment);
                tvIdiom = view.findViewById(R.id.tv_idiom);
                llSino = view.findViewById(R.id.ll_sino);
                tvSinoSqiRCon = view.findViewById(R.id.tv_sino_sqiRcontents);
                tvSinoSqiWCon = view.findViewById(R.id.tv_sino_sqiWcontents);
                root = (LinearLayout) view;

                tvIdiom.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize + 4);
                tvWContents.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
                tvTKeyword.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
                tvFKeyword.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
                tvSinoSqiRCon.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize - 4);
                tvSinoSqiWCon.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize - 4);
                tvComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize - 4);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_rv_list, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {


            WordQuestionDao.WordQuestionItem listData = dataArr.get(position);

            //북마크
            if (listData.getBookmark().equals("Y")) {
                holder.ivBookmark.setImageResource(R.drawable.sn_bm_on);

            } else {
                holder.ivBookmark.setImageResource(R.drawable.sn_bm_off);
            }

            //표준어
            if (sqClass.equals("V")) {
                holder.tvTKeyword.setText(listData.getTKeyword());
                holder.tvFKeyword.setText(listData.getFKeyword());

                if (isLearning) {
                    holder.tvSinoSqiRCon.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getSqiCommentary(), R.color.col_3372dc));
                    holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getSqiRContents(), R.color.col_3372dc));

                    holder.llSino.setVisibility(View.VISIBLE);
                    holder.tvComment.setVisibility(View.VISIBLE);
                } else {
                    holder.llSino.setVisibility(View.GONE);
                    holder.tvComment.setVisibility(View.GONE);
                }

                //외래어
            } else if (sqClass.equals("W")) {
                holder.tvTKeyword.setTextColor(Color.BLACK);
                holder.tvTKeyword.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getSqiRContents(), R.color.col_3372dc));

                holder.llFKeyword.setVisibility(View.GONE);

                holder.llSino.setVisibility(View.GONE);

                if (isLearning) {
                    holder.tvComment.setVisibility(View.VISIBLE);
                    holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getSqiCommentary(), R.color.col_3372dc));

                } else {
                    holder.tvComment.setVisibility(View.GONE);
                }

                //사자성어
            } else if (sqClass.equals("X")) {
                holder.llTFWord.setVisibility(View.GONE);


                holder.llWord.setVisibility(View.VISIBLE);
                holder.tvIdiom.setText(listData.getSqiRContents());
                holder.tvWContents.setText(listData.getSqiWContents());

                if (isLearning) {
                    holder.tvSinoSqiRCon.setVisibility(View.VISIBLE);
                    holder.llSino.setVisibility(View.VISIBLE);
                    holder.tvSinoSqiRCon.setText(listData.getFKeyword());

                    holder.tvComment.setVisibility(View.VISIBLE);
                    holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getSqiCommentary(), R.color.col_3372dc));

                } else {
                    holder.llSino.setVisibility(View.GONE);
                    holder.tvComment.setVisibility(View.GONE);
                }

            } else if (sqClass.equals("Y")) {//한자어

                holder.llTFWord.setVisibility(View.GONE);


                holder.llWord.setVisibility(View.VISIBLE);
                holder.tvIdiom.setText(listData.getSqiRContents());
                holder.tvWContents.setText(listData.getSqiWContents());

                if (isLearning) {
                    holder.llSino.setVisibility(View.VISIBLE);
                    holder.tvComment.setVisibility(View.VISIBLE);
                    holder.tvSinoSqiWCon.setVisibility(View.VISIBLE);

                    holder.tvSinoSqiRCon.setText(listData.getFKeyword());
                    holder.tvSinoSqiWCon.setText(listData.getSqiCommentary().replaceAll("\\{#\\}", "\n"));

                    holder.tvComment.setText(StringUtil.removeSpecialCharacter(listData.getAntonym()));

                } else {
                    holder.llSino.setVisibility(View.GONE);
                    holder.tvComment.setVisibility(View.GONE);
                }

            } else if (sqClass.equals("F")) {
                holder.tvTKeyword.setText(listData.getSqiRContents());
                holder.llFKeyword.setVisibility(View.GONE);

                if (isLearning) {
                    holder.tvSinoSqiRCon.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getSqiCommentary(), R.color.col_3372dc));
                    holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(WordLearningModeListActivity.this, listData.getTKeyword(), R.color.col_3372dc));

                    holder.llSino.setVisibility(View.VISIBLE);
                    holder.tvComment.setVisibility(View.VISIBLE);
                } else {
                    holder.llSino.setVisibility(View.GONE);
                    holder.tvComment.setVisibility(View.GONE);
                }
            }

            holder.rlBookmark.setTag(position);
            holder.rlBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    String finalPtype = "";

                    WordQuestionDao.WordQuestionItem data = dataArr.get(position);

                    if (data.getBookmark().equalsIgnoreCase("N"))
                        finalPtype = "add";
                    else finalPtype = "del";

                    JsonObject jo = new JsonObject();
                    jo.addProperty("user_code", BaseActivity.userCode);
                    jo.addProperty("sq_class", data.sqClass);
                    jo.addProperty("gubun", "voca");
                    jo.addProperty("ptype", finalPtype);
                    jo.addProperty("ip_idx", data.getSqiIdx());
                    jo.addProperty("app_day", data.getAppDay());

                    RequestData.getInstance().getVocabularyBookmark(jo, new NetworkResponse<JsonObject>() {
                        @Override
                        public void onSuccess(Call call, JsonObject clazz) {
                            if (clazz.get("statusCode").getAsString().equals("200") && clazz.get("resultData").getAsJsonObject().get("result").getAsString().equals("OK")) {
                                if (data.getBookmark().equalsIgnoreCase("N")) {
                                    Toast.makeText(WordLearningModeListActivity.this, getString(R.string.msg_bookmark_add), Toast.LENGTH_SHORT).show();
                                    dataArr.get(position).setBookmark("Y");

                                } else {
                                    Toast.makeText(WordLearningModeListActivity.this, getString(R.string.msg_bookmark_delete), Toast.LENGTH_SHORT).show();
                                    dataArr.get(position).setBookmark("N");
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFail(Call call, String msg) {
                            Toast.makeText(WordLearningModeListActivity.this, getString(R.string.server_error_default_msg), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            holder.root.setTag(position);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();

                    for (int i = 0; i < dataArr.size(); i++) {
                        dataArr.get(i).setSolvedYN("Y");
                        dataArr.get(i).setCsIsResult("t");
                    }

                    Intent intent = new Intent(WordLearningModeListActivity.this, WordOXLearningQuizActivity.class);
                    intent.putExtra("data", dataArr);
                    intent.putExtra("sq_class", sqClass);
                    intent.putExtra("startNum", position);
                    intent.putExtra("bookmark", true);
                    intent.putExtra("title", tvTitle.getText().toString());
                    intent.putExtra("mode", MODE.LEARNING_MODE);

                    startActivityForResult(intent, PrefConsts.REQUEST_WORD_LEARNING_ACTIVITY);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataArr.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PrefConsts.REQUEST_WORD_LEARNING_ACTIVITY && resultCode == RESULT_OK) {

            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                rv.scrollToPosition(position);
            }
        } else if (requestCode == PrefConsts.REQUEST_WORD_LEARNING_SOUND_SETTING_ACTIVITY && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, WordTTSService.class);
            intent.putExtra("data", dataArr);
            intent.putExtra("sq_class", sqClass);
            startService(intent);

            tvSound.setText("그만듣기");
        }
    }
}

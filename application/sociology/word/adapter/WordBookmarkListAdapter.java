package com.jlesoft.civilservice.koreanhistoryexam9.word.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.BaseActivity;
import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.util.StringUtil;
import com.jlesoft.civilservice.koreanhistoryexam9.word.bookmark.WordBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordBookmarkQuestionDao;

public class WordBookmarkListAdapter extends RecyclerView.Adapter<WordBookmarkListAdapter.ViewHolder> {

    private Context mContext;
    private String sqClass;
    private boolean isLearning;

    private OnItemSelectListener itemClick;
    private OnItemBookmarkListener itemBookmarkClick;

    public interface OnItemSelectListener { // create an interface
        void onItemSelect(int position); // create callback function
    }

    public interface OnItemBookmarkListener {
        void onItemBookmark(int position);
    }

    public WordBookmarkListAdapter(Context context, String sqClass, OnItemSelectListener itemcClick, OnItemBookmarkListener itemBookmarkClick, boolean isLearning) {
        this.mContext = context;
        this.sqClass = sqClass;
        this.itemClick = itemcClick;
        this.itemBookmarkClick = itemBookmarkClick;
        this.isLearning = isLearning;
    }

    public void setLearning(boolean isLearning){
        this.isLearning = isLearning;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

            tvIdiom.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize+4);
            tvWContents.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
            tvTKeyword.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
            tvFKeyword.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize);
            tvSinoSqiRCon.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize-4);
            tvSinoSqiWCon.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize-4);
            tvComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, BaseActivity.fontSize-4);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_rv_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        WordBookmarkQuestionDao.WordQuestionItem listData = WordBookmarkFragment.dataArr.get(position);

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
                holder.tvSinoSqiRCon.setText(StringUtil.replaceSpecialCharacterColor(mContext, listData.getSqiCommentary(), R.color.col_3372dc));
                holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(mContext, listData.getSqiRContents(), R.color.col_3372dc));

                holder.llSino.setVisibility(View.VISIBLE);
                holder.tvComment.setVisibility(View.VISIBLE);
            } else {
                holder.llSino.setVisibility(View.GONE);
                holder.tvComment.setVisibility(View.GONE);
            }

            //외래어
        } else if (sqClass.equals("W")) {
            holder.tvTKeyword.setTextColor(Color.BLACK);
            holder.tvTKeyword.setText( StringUtil.replaceSpecialCharacterColor(mContext, listData.getSqiRContents(), R.color.col_3372dc));

            holder.llFKeyword.setVisibility(View.GONE);

            holder.llSino.setVisibility(View.GONE);

            if (isLearning) {
                holder.tvComment.setVisibility(View.VISIBLE);
                holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(mContext, listData.getSqiCommentary(), R.color.col_3372dc));

            }else{
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
                holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(mContext, listData.getSqiCommentary(), R.color.col_3372dc));

            }else{
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

            }else{
                holder.llSino.setVisibility(View.GONE);
                holder.tvComment.setVisibility(View.GONE);
            }

        } else if (sqClass.equals("F")) {
            holder.tvTKeyword.setText(listData.getSqiRContents());
            holder.llFKeyword.setVisibility(View.GONE);

            if (isLearning) {
                holder.tvSinoSqiRCon.setText(StringUtil.replaceSpecialCharacterColor(mContext, listData.getSqiCommentary(), R.color.col_3372dc));
                holder.tvComment.setText(StringUtil.replaceSpecialCharacterColor(mContext, listData.getTKeyword(), R.color.col_3372dc));

                holder.llSino.setVisibility(View.VISIBLE);
                holder.tvComment.setVisibility(View.VISIBLE);
            } else {
                holder.llSino.setVisibility(View.GONE);
                holder.tvComment.setVisibility(View.GONE);
            }
        }

        holder.rlBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBookmarkClick.onItemBookmark(position);
            }
        });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemSelect(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return WordBookmarkFragment.dataArr.size();
    }


}
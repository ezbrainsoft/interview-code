package com.jlesoft.civilservice.koreanhistoryexam9.word.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.word.bookmark.WordBookmarkFragment;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordBookmarkQuestionDao;

public class VocaWordBookmarkListAdapter extends RecyclerView.Adapter<VocaWordBookmarkListAdapter.ViewHolder>  {//implements View.OnClickListener {

    private static final String TAG = "WordListAda";
//    private ArrayList<WordQuestionDao.WordQuestionItem> list;
    private Context mContext;
    private String sqClass;

    private OnItemSelectListener itemClick;
    private OnItemBookmarkListener itemBookmarkClick;

    public interface OnItemSelectListener { // create an interface
        void onItemSelect(int position); // create callback function
    }

    public interface OnItemBookmarkListener{
        void onItemBookmark(int position);
    }

    public VocaWordBookmarkListAdapter(Context context, String sqClass, OnItemSelectListener itemcClick, OnItemBookmarkListener itemBookmarkClick) {
        this.mContext = context;
//        this.list = list;
        this.sqClass = sqClass;
        this.itemClick = itemcClick;
        this.itemBookmarkClick =itemBookmarkClick;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout root, llWord;
        public RelativeLayout rlBookmark;
        public ImageView ivBookmark;
        public TextView tvTKeyword;
        public TextView tvFKeyword;
        public TextView tvComment, tvIdiom;
        public LinearLayout llSino;
        public TextView tvSinoSqiRCon, tvSinoSqiWCon;

        public ViewHolder(View view) {
            super(view);
            llWord = view.findViewById(R.id.ll_word);
            rlBookmark = view.findViewById(R.id.rl_bookmark);
            ivBookmark = view.findViewById(R.id.iv_bookmark);
            tvTKeyword = view.findViewById(R.id.tv_t_keyword);
            tvFKeyword = view.findViewById(R.id.tv_f_keyword);
            tvComment = view.findViewById(R.id.tv_comment);
            tvIdiom = view.findViewById(R.id.tv_idiom);
            llSino = view.findViewById(R.id.ll_sino);
            tvSinoSqiRCon = view.findViewById(R.id.tv_sino_sqiRcontents);
            tvSinoSqiWCon = view.findViewById(R.id.tv_sino_sqiWcontents);
            root = (LinearLayout) view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark_rv_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        WordBookmarkQuestionDao.WordQuestionItem listData = WordBookmarkFragment.dataArr.get(position);

        Log.d(TAG, "onBindViewHolder ["+position+"] : "+listData.toString());

        if(listData.getBookmark().equals("Y")){
            holder.ivBookmark.setImageResource(R.drawable.sn_bm_on);

        }else{
            holder.ivBookmark.setImageResource(R.drawable.sn_bm_off);
        }

        if(sqClass.equals("V") || sqClass.equals("W")){
            holder.tvTKeyword.setText(listData.getTKeyword());
            holder.tvFKeyword.setText("X "+listData.getFKeyword());

            if(sqClass.equals("W")){
                holder.tvComment.setVisibility(View.VISIBLE);
                holder.tvComment.setText(listData.getSqiField());
            }else if(sqClass.equals("X")){
                holder.tvComment.setVisibility(View.VISIBLE);
                holder.tvComment.setText(listData.getSqiCommentary());
            }
        }else if(sqClass.equals("X")){
            holder.tvIdiom.setVisibility(View.VISIBLE);
            holder.tvIdiom.setText(listData.getSqiRContents());

            holder.tvTKeyword.setText(listData.getSqiWContents());
            holder.tvFKeyword.setVisibility(View.GONE);

            holder.tvComment.setVisibility(View.VISIBLE);
            holder.tvComment.setTextColor(mContext.getResources().getColor(R.color.col_595959));
            holder.tvComment.setText(listData.getSqiCommentary());

        }else if(sqClass.equals("Y")){
            holder.llWord.setVisibility(View.GONE);
            holder.llSino.setVisibility(View.VISIBLE);
            holder.tvSinoSqiRCon.setText(listData.getSqiRContents());
            holder.tvSinoSqiWCon.setText(listData.getSqiWContents());
            holder.tvComment.setVisibility(View.VISIBLE);
            holder.tvComment.setText(listData.getSqiCommentary().replaceAll("\\{#\\}", "\n"));

        }else if(sqClass.equals("F")){
            holder.tvIdiom.setVisibility(View.GONE);
            holder.llWord.setVisibility(View.GONE);
            holder.llSino.setVisibility(View.VISIBLE);
            holder.tvSinoSqiRCon.setText(listData.antonym);
            holder.tvSinoSqiRCon.setTextColor(mContext.getResources().getColor(R.color.col_3372dc));
            holder.tvComment.setVisibility(View.VISIBLE);
            holder.tvComment.setText(listData.tContents);

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


    @Override public int getItemCount() {
        return WordBookmarkFragment.dataArr.size();
    }



}
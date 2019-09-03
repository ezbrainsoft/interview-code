package com.jlesoft.civilservice.koreanhistoryexam9.word.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordSearchListDao;

import java.util.ArrayList;


public class EnglishWordSearchListAdapter extends RecyclerView.Adapter<EnglishWordSearchListAdapter.ViewHolder> {

    private static final String TAG = "WordListAda";
    private ArrayList<WordSearchListDao.WordListItem> list;
    private Context mContext;
    private String searchTxt;


    private OnItemSelectListener listener;

    public interface OnItemSelectListener {
        void onItemSelect(int position);
    }

    public EnglishWordSearchListAdapter(Context context, ArrayList<WordSearchListDao.WordListItem> list, OnItemSelectListener listener) {
        this.mContext = context;
        this.list = list;
        this.listener = listener;
    }

    public void setData(ArrayList<WordSearchListDao.WordListItem> list, String searchTxt) {
        this.list = list;
        this.searchTxt = searchTxt;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWord, tvDetail;
        public CardView root;

        public ViewHolder(View view) {
            super(view);
            root = view.findViewById(R.id.root);
            tvWord = view.findViewById(R.id.tv_word);
            tvDetail = view.findViewById(R.id.tv_detail);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eng_word_search_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        WordSearchListDao.WordListItem listData = list.get(position);

        if (listData.word.contains(listData.word)) {
            int startNum = listData.word.indexOf(searchTxt);
            int endNum = startNum + searchTxt.length();

            SpannableString sunjiSpannable = new SpannableString(listData.word);

            if (startNum != -1 && endNum > startNum && endNum <= searchTxt.length()) {
                sunjiSpannable.setSpan(new BackgroundColorSpan(ContextCompat.getColor(mContext, R.color.col_80FFE0B2)), startNum, endNum,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.tvWord.setText(sunjiSpannable);

        } else {
            holder.tvWord.setText(listData.word);
        }


        holder.tvDetail.setText(listData.explainKor);

        if (listener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelect(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
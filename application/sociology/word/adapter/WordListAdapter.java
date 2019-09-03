package com.jlesoft.civilservice.koreanhistoryexam9.word.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordListDao;

import java.util.ArrayList;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private static final String TAG = "WordListAda";
    private ArrayList<WordListDao.WordListItem> list;
    private Context mContext;

    public WordListAdapter(Context context, ArrayList<WordListDao.WordListItem> list) {
        this.mContext = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearList;
        public TextView tvTitle, tvCount, tvDetail;
        public ImageView ivCheckView;
        public View anchor;

        public ViewHolder(View view) {
            super(view);
            linearList = view.findViewById(R.id.linear_list);
            tvTitle = view.findViewById(R.id.tv_title);
            tvCount = view.findViewById(R.id.tv_cnt);
            ivCheckView = view.findViewById(R.id.iv_check_view);
            tvDetail = view.findViewById(R.id.tv_detail);
            anchor = view.findViewById(R.id.anchor);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pre_rv_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        WordListDao.WordListItem listData = list.get(position);

        holder.tvTitle.setText("Day " + listData.getAppDay());

        holder.tvCount.setText(listData.getMyCnt() + "/" + listData.getTotalCnt());

        if (listData.checkView.equals("N")) {
            holder.ivCheckView.setVisibility(View.GONE);
        } else {
            holder.ivCheckView.setVisibility(View.VISIBLE);
        }

        if (listData.myCnt.equals("0")) {
            holder.tvDetail.setText("미풀이");
        } else {
            holder.tvDetail.setText(listData.getScore() + "점 (풀이:" + listData.myCnt + "개-맞힘:" + listData.tCnt + "개,틀림:" + listData.fCnt + "개)");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
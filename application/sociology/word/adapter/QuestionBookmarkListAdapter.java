package com.jlesoft.civilservice.koreanhistoryexam9.word.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.word.model.WordListDao;

import java.util.ArrayList;

public class QuestionBookmarkListAdapter extends RecyclerView.Adapter<QuestionBookmarkListAdapter.ViewHolder>  {//implements View.OnClickListener {

    private ArrayList<WordListDao.WordListItem> list;
    private Context mContext;
    private String sqClass;
    private OnItemSelectListener itemClick;

    public interface OnItemSelectListener {
        public void onItemSelect(ViewHolder holder, int position);
    }

    public void setData(ArrayList<WordListDao.WordListItem> list){
        this.list = list;
    }

    public QuestionBookmarkListAdapter(Context context, ArrayList<WordListDao.WordListItem> list, OnItemSelectListener listener) {
        this.mContext = context;
        this.list = list;
        this.sqClass = sqClass;
        this.itemClick = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout root;
        public ImageView ivCheckView;
        public TextView tvTitle;
        public TextView tvCnt;
        public View anchor;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            ivCheckView = view.findViewById(R.id.iv_check_view);
            tvCnt = view.findViewById(R.id.tv_cnt);
            anchor = view.findViewById(R.id.anchor);
            root = (LinearLayout) view;
        }
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_list, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, final int position) {

        WordListDao.WordListItem listData = list.get(position);

        holder.tvTitle.setText("Day "+listData.getAppDay());
        holder.tvCnt.setText(listData.getCnt());

        if(!TextUtils.isEmpty(listData.getCheckView()) && listData.getCheckView().equals("Y")){
            holder.ivCheckView.setVisibility(View.VISIBLE);
        }else{
            holder.ivCheckView.setVisibility(View.GONE);
        }

        holder.root.setTag(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onItemSelect(holder, position);
                }
            }
        });
    }


    @Override public int getItemCount() {
        return list.size();
    }

}
package com.jlesoft.civilservice.koreanhistoryexam9.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jlesoft.civilservice.koreanhistoryexam9.R;
import com.jlesoft.civilservice.koreanhistoryexam9.model.DanwonStudyQuestion;
import com.jlesoft.civilservice.koreanhistoryexam9.model.StudyDanwonMainCategory;
import com.jlesoft.civilservice.koreanhistoryexam9.model.StudyDanwonSubCategory;
import com.jlesoft.civilservice.koreanhistoryexam9.util.Dlog;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;


public class StudyDanwonMainAdapter extends BaseExpandableListAdapter {
    private LayoutInflater myinf = null;

    private List<StudyDanwonMainCategory> mainCategoryList;
    private HashMap<StudyDanwonMainCategory, RealmList<StudyDanwonSubCategory>> map;
    private String lastGroup;

    public StudyDanwonMainAdapter(Context context, List<StudyDanwonMainCategory> mainCategoryList, HashMap<StudyDanwonMainCategory, RealmList<StudyDanwonSubCategory>> map, Realm realm, String lastGroup) {
        this.myinf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mainCategoryList = mainCategoryList;
        this.map = map;
        this.lastGroup = lastGroup;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = myinf.inflate(R.layout.item_elv_parent, parent, false);
        }

        final StudyDanwonMainCategory data = getGroup(groupPosition);

        TextView tv = (TextView) convertView.findViewById(R.id.tv);

        tv.setText(data.getIpcName());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dlog.d("main_" + data.getIpcUpCode() + " & " + data.getIpcName());
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = myinf.inflate(R.layout.item_elv_child, parent, false);
        }

        StudyDanwonSubCategory sub = getChild(groupPosition, childPosition);

        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        ImageView ivCheckView = (ImageView) convertView.findViewById(R.id.iv_check_view);
        TextView countTxt = (TextView) convertView.findViewById(R.id.count_txt);

        tv.setText(sub.getIpcName());


        if (TextUtils.isEmpty(lastGroup)) {
            if (sub.getCheckView().equalsIgnoreCase("Y")) {
                ivCheckView.setVisibility(View.VISIBLE);
            } else {
                ivCheckView.setVisibility(View.GONE);
            }
        } else {
            if (sub.getIpcCode().equalsIgnoreCase(lastGroup)) {
                ivCheckView.setVisibility(View.VISIBLE);
            } else {
                ivCheckView.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public StudyDanwonSubCategory getChild(int groupPosition, int childPosition) {
        return map.get(mainCategoryList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(mainCategoryList.get(groupPosition)).size();
    }

    @Override
    public StudyDanwonMainCategory getGroup(int groupPosition) {
        return mainCategoryList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mainCategoryList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

}
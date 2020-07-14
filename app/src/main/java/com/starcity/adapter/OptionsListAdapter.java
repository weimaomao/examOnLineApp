package com.starcity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.starcity.R;
import com.starcity.activity.TestActivity;
import com.starcity.entity.QbQuestion;
import com.starcity.entity.QbQuestionOption;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OptionsListAdapter extends BaseAdapter {
    private Context mContext;
    ListView lv;
    int index;
    public QbQuestion qbQuestion;
    private List<QbQuestionOption> options;


    public OptionsListAdapter(Context context, QbQuestion qbQuestion, ListView lv, int index) {
        this.mContext = context;
        this.qbQuestion = qbQuestion;
        this.lv = lv;
        this.index = index;
        //试题排序一下
        List<QbQuestionOption> optionsList = qbQuestion.getOptions();
        Collections.sort(optionsList, new Comparator<QbQuestionOption>() {
            @Override
            public int compare(QbQuestionOption qbQuestionOption1, QbQuestionOption qbQuestionOption2) {
                return  qbQuestionOption1.getSort().compareTo(qbQuestionOption2.getSort());
            }
        });
        this.options = optionsList;
    }

    public int getCount() {
        return qbQuestion.getOptions().size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item_option, null);
        CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.ctv_name);
        TextView option = (TextView) view.findViewById(R.id.tv_option);

        ctv.setText(options.get(position).getSort());
        option.setText(options.get(position).getTitle());

        //如果有默认选中则进行设置一下勾选
        List<String> studentAnswers = qbQuestion.getStudentAnswers();
        String sort = options.get(position).getSort();
        if (studentAnswers != null && studentAnswers.size() != 0 && studentAnswers.contains(sort)) {
            lv.setItemChecked(position, true);
        }else{
            lv.setItemChecked(position, false);
        }
        updateBackground(position, ctv);
        return view;

    }

    public void updateBackground(int position, View view) {
        int backgroundId;
        //多选题显示的其他
        if ("tmlx_ddt".equals(qbQuestion.getTypeCode())) {
            if (lv.isItemChecked(position)) {
                backgroundId = R.drawable.checkbox_bg_seleted;
            } else {
                backgroundId = R.drawable.checkbox_bg;
            }

        } else {
            if (lv.isItemChecked(position)) {
                backgroundId = R.drawable.option_btn_single_checked;
            } else {
                backgroundId = R.drawable.option_btn_single_normal;
            }
        }
        view.setBackgroundResource(backgroundId);
    }

}

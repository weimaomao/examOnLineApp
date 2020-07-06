package com.starcity.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.starcity.R;
import com.starcity.activity.TestActivity;
import com.starcity.entity.QbQuestion;

import java.util.List;

/**
 * 下面显示的所有题目索引
 */
public class ChooseQuestionAdapter extends BaseAdapter {
    private Context context;
    private int count;
    private ViewPager viewPager;

    public ChooseQuestionAdapter(Context context, int count, ViewPager viewPager) {
        this.context = context;
        this.count = count;
        this.viewPager = viewPager;
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.list_item_fragment_question_bt, null);
        Button imagView = (Button) view.findViewById(R.id.iv_show);
        QbQuestion qbQuestion = TestActivity.qbQuestionList.get(position);
        List<String> studentAnswers = qbQuestion.getStudentAnswers();
        if(position==8){
            System.out.println(position);
        }
        if (studentAnswers != null && studentAnswers.size() > 0) {
            imagView.setBackgroundResource(R.drawable.option_btn_single_checked);
        } else if (position == TestActivity.currentIndex) {
            imagView.setBackgroundResource(R.drawable.option_btn_single_normal);
        } else {
            imagView.setBackgroundResource(R.drawable.btn_question_circle_white);
        }
        imagView.setText((position + 1) + "");
        imagView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(position);
            }
        });
        return view;
    }

}


package com.starcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starcity.R;
import com.starcity.entity.ForeExamHistory;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by
 */
public class TestedListAdapter extends BaseAdapter {

    private List<ForeExamHistory> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public TestedListAdapter(Context context,
                             List<ForeExamHistory> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }


    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return new Object();
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent) {
        ForeExamHistory foreExamHistory = data.get(position);
        if (convertView == null) {
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.list_item_tested, null);
        }
        if (foreExamHistory == null) {
            return convertView;
        }
        //绑定数据
        TextView  titleTx = (TextView) convertView.findViewById(R.id.titleTx);
        TextView  qualifiedTx = (TextView) convertView.findViewById(R.id.qualifiedTx);
        TextView  scoreTx = (TextView) convertView.findViewById(R.id.scoreTx);
        TextView  examTimeTx = (TextView) convertView.findViewById(R.id.examTimeTx);
        titleTx.setText(foreExamHistory.getTitle());
        qualifiedTx.setText(foreExamHistory.getQualified() ? "合格" : "不合格");
        scoreTx.setText(foreExamHistory.getTestScore() + "");
        SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfHH = new SimpleDateFormat("HH:mm:ss");
        if (foreExamHistory.getBeginTime()!=null &&foreExamHistory.getEndTime()!=null) {
            examTimeTx.setText(sdfYYYY.format(foreExamHistory.getBeginTime())+"-"+sdfHH.format(foreExamHistory.getEndTime()));
        }
        return convertView;
    }
}

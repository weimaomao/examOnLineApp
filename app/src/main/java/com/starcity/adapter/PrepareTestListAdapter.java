package com.starcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.starcity.R;
import com.starcity.entity.ForeExamEmployee;

import java.util.List;

/**
 * Created by
 */
public class PrepareTestListAdapter extends BaseAdapter {

    private List<ForeExamEmployee> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public PrepareTestListAdapter(Context context,
                                  List<ForeExamEmployee> data) {
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

        if (convertView == null) {
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.list_item_prepare_test, null);
        } else {
            convertView.getTag();
        }
        ForeExamEmployee foreExamEmployee = data.get(position);
        if(foreExamEmployee==null){
            return convertView;
        }
        TextView titleTx = null;
        TextView examTimeTx = null;
        TextView statusTx = null;

        titleTx = (TextView) convertView.findViewById(R.id.titleTx);
        examTimeTx = (TextView) convertView.findViewById(R.id.examTimeTx);
        statusTx= (TextView) convertView.findViewById(R.id.statusTx);

        titleTx.setText(foreExamEmployee.getTitle());
        examTimeTx.setText(foreExamEmployee.getExamTime());
        if(foreExamEmployee.getStatus()!=null){
            String s = foreExamEmployee.getStatus() == 1 ? "待考试" : "已开始";
            statusTx.setText(s);
        }

        return convertView;
    }
}

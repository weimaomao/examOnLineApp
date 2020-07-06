package com.starcity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.starcity.R;
import com.starcity.activity.TestActivity;
import com.starcity.adapter.OptionsListAdapter;
import com.starcity.database.DBManager;
import com.starcity.entity.DaoSession;
import com.starcity.entity.QbQuestion;
import com.starcity.entity.QbQuestionDao;
import com.starcity.entity.QbQuestionOption;
import com.starcity.entity.QbQuestionOptionDao;
import com.starcity.view.NoScrollListview;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hzc
 * @version 1.0
 * @date 2015-6-24
 */
@SuppressLint("ValidFragment")
public class QuestionItemFragment extends Fragment {
    QbQuestion questionBean;
    int index;
    private OptionsListAdapter adapter;
    private NoScrollListview lv;
    private QbQuestionOptionDao qbQuestionOptionDao;
    private QbQuestionDao qbQuestionDao;
    private DaoSession daoSession;
    private List<QbQuestionOption> options;

    public QuestionItemFragment(int index) {
        this.index = index;
        questionBean = TestActivity.qbQuestionList.get(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取数据库dao
        daoSession = DBManager.getInstance(getActivity()).getDaoSession();
        qbQuestionOptionDao = daoSession.getQbQuestionOptionDao();
        qbQuestionDao = daoSession.getQbQuestionDao();
        View rootView = inflater.inflate(R.layout.pager_item,
                container, false);
        lv = (NoScrollListview) rootView.findViewById(R.id.lv_options);
        TextView tv_description = (TextView) rootView.findViewById(R.id.tv_description);
        options = questionBean.getOptions();
        if (options == null) {
            options = qbQuestionOptionDao.queryBuilder().where(QbQuestionOptionDao.Properties.QuestionId.eq(questionBean.getId())).list();
            questionBean.setOptions(options);
        }
        adapter = new OptionsListAdapter(getActivity(), questionBean, lv, index);
        lv.setAdapter(adapter);

        tv_description.setText(questionBean.getTitle());

        //题干描述前面加上(单选题)或(多选题)
        //判断是否多选
        boolean isDDT = "tmlx_ddt".equals(questionBean.getTypeCode());
        if (!isDDT) {//单选
            SpannableStringBuilder ssb = new SpannableStringBuilder("tmlx_dxt".equals(questionBean.getTypeCode()) ? "(单选题)" : "(判断题)");
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append("  " + (index + 1) + ".");
            ssb.append(questionBean.getTitle());
            ssb.append(" () (" + questionBean.getScore() + "分)");
            tv_description.setText(ssb);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    QbQuestionOption qbQuestionOption = options.get(position);
                    adapter.notifyDataSetChanged();
                    List<String> studentAnswers = new ArrayList<>();
                    studentAnswers.add(qbQuestionOption.getSort());
                    questionBean.setStudentAnswers(studentAnswers);
                    List<String> answers = questionBean.getAnswers();
                    String studentAnswer = answers.get(0);
                    //这个答案是正确的
                    if (studentAnswer.equals(qbQuestionOption.getSort())) {
                        questionBean.setStudentScore(questionBean.getScore());
                    }
                    qbQuestionDao.update(questionBean);
                }
            });
        } else if (isDDT) {//多选
            SpannableStringBuilder ssb = new SpannableStringBuilder("(多选题)");
            ssb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(questionBean.getTitle());
            tv_description.setText(ssb);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long op) {
                    adapter.notifyDataSetChanged();
                    List<String> studentAnswers = new ArrayList<>();
                    long[] ids = lv.getCheckedItemIds();
                    for (int i = 0; i < ids.length; i++) {
                        long id = ids[i];
                        String sort = questionBean.getOptions().get((int) id).getSort();
                        studentAnswers.add(sort);
                    }
                    //比较两个list是否相等  相等则加分
                    List<String> answers = questionBean.getAnswers();
                    boolean bingo = answers.containsAll(studentAnswers);
                    if (bingo) {
                        questionBean.setStudentScore(questionBean.getScore());
                    }
                    questionBean.setStudentAnswers(studentAnswers.size()==0?null:studentAnswers);
                    qbQuestionDao.update(questionBean);
                }
            });
			/*btn_submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					long[] ids = lv.getCheckedItemIds();
					for (int i = 0; i < ids.length; i++) {
						long id = ids[i];
						sb.append(questionBean.getOptions().get((int)id).getSort()).append(" ");
					}
					Toast.makeText(getActivity(), "选中的选项为"+sb.toString(), Toast.LENGTH_SHORT).show();
					sb.setLength(0);
				}
			});*/
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 直接使用getCheckItemIds选中在取消选中后，返回的long[]中还有，所以这个方法代替一下
     *
     * @return
     */
    protected long[] getListCheckedItemIds() {
        final long[] ids = new long[lv.getCount()];
        int checkedCount = 0;
        for (int i = 0; i < lv.getCount(); i++) {
            if (lv.isItemChecked(i)) {
                ids[checkedCount++] = i;
            }
        }
        if (checkedCount == lv.getCount()) {
            return ids;
        } else {
            final long[] result = new long[checkedCount];
            System.arraycopy(ids, 0, result, 0, checkedCount);

            return result;
        }
    }


}
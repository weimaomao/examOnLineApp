package com.starcity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starcity.BaseActivity;
import com.starcity.R;
import com.starcity.api.ApiStores;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitHaveTokenFactory;
import com.starcity.config.RetrofitNoTokenFactory;
import com.starcity.entity.ForeExamEmployee;
import com.starcity.entity.RResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmTestActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_exam_time;
    private TextView tv_duration;
    private TextView tv_sum_mark;
    private TextView tv_pass_mark;
    private TextView tv_tips;
    private Button btn_begin_test;
    private CheckBox cb_read_tips;
    private RelativeLayout mBackRl;

    ICreate iCreate = new RetrofitHaveTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();
    private Call<RResult<Integer>> uptimePaper;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        ForeExamEmployee foreExamEmployee = intent.getParcelableExtra("foreExamEmployee");
        tv_title.setText(foreExamEmployee.getTitle());
        tv_exam_time.setText(foreExamEmployee.getExamTime());
        tv_duration.setText(foreExamEmployee.getDuration() + "分钟");
        tv_sum_mark.setText(foreExamEmployee.getSumMark() + "分");
        tv_pass_mark.setText(foreExamEmployee.getPassMark() + "分");
//        tv_tips.setText(Html.fromHtml("qweqwe<font color='#FF0000'>红颜色</font>fdsd"));
        //获取状态
        uptimePaper = retrofitService.getUptimePaper(foreExamEmployee.getPaperId());
        uptimePaper.enqueue(new Callback<RResult<Integer>>() {
            @Override
            public void onResponse(Call<RResult<Integer>> call, Response<RResult<Integer>> response) {
                RResult<Integer> body = response.body();
                if (body == null) {
                    Toast.makeText(ConfirmTestActivity.this, "服务异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (body.getCode() != 200) {
                    tv_tips.setText(Html.fromHtml("<font color='#FF0000'>" + body.getMessage() + "</font>"));
                    return;
                }
                tv_tips.setText(Html.fromHtml("1.距离本场考试结束考试还有<font color='#FF0000'>" + (body.getResult()%60) + "</font>分钟，结束将自动提交试卷，请合理安排时间。<br/>2.参加本场考试请点击\"开始考试\"。"));
                //设置已阅读可用
                cb_read_tips.setEnabled(true);
            }
            @Override
            public void onFailure(Call<RResult<Integer>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(ConfirmTestActivity.this, "接口调用异常！", Toast.LENGTH_SHORT).show();
            }
        });
        //设置已阅读不可用
        cb_read_tips.setEnabled(false);
        //设置按钮不可用
        btn_begin_test.setEnabled(false);
        cb_read_tips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //设置按钮可用
                    btn_begin_test.setEnabled(true);
                }else{
                    //设置按钮不可用
                    btn_begin_test.setEnabled(false);
                }
            }
        });
        btn_begin_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转考试页面
                Intent intent = new Intent(ConfirmTestActivity.this, TestActivity.class);
                intent.putExtra("paperId",foreExamEmployee.getPaperId());
                startActivity(intent);
            }
        });

        mBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initID() {
        mBackRl = (RelativeLayout) findViewById(R.id.back_rl);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_exam_time = (TextView) findViewById(R.id.tv_exam_time);
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        tv_sum_mark = (TextView) findViewById(R.id.tv_sum_mark);
        tv_pass_mark = (TextView) findViewById(R.id.tv_pass_mark);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        btn_begin_test = (Button) findViewById(R.id.btn_begin_test);
        cb_read_tips = (CheckBox) findViewById(R.id.cb_read_tips);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_test);
    }

}

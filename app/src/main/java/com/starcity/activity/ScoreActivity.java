package com.starcity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starcity.BaseActivity;
import com.starcity.R;

public class ScoreActivity extends BaseActivity {
    private RelativeLayout mBackRl;
    private TextView totalScore;
    private TextView prepareTitle;
    private TextView prepareTime;

    @Override
    protected void initData() {
        mBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ScoreActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        totalScore.setText(TestActivity.qbPaper.getStudentTotalScore() + "");
        prepareTitle.setText(TestActivity.qbPaper.getTitle());
        //考试时长
        int duration = TestActivity.studentDuration;
        //剩余秒数
        int second = (TestActivity.minute * 60) + TestActivity.second;
        prepareTime.setText("考试结束，用时" + (duration - second)/60 + "分钟");
    }

    @Override
    protected void initID() {
        mBackRl = (RelativeLayout) findViewById(R.id.back_rl);
        totalScore = (TextView) findViewById(R.id.totalScore);
        prepareTitle = (TextView) findViewById(R.id.prepareTitle);
        prepareTime = (TextView) findViewById(R.id.prepareTime);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_score);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent homeIntent = new Intent(ScoreActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
        return false;
    }

}

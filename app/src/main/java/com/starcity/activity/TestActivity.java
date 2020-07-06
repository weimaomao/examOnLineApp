package com.starcity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starcity.BaseActivity;
import com.starcity.R;
import com.starcity.adapter.ChooseQuestionAdapter;
import com.starcity.adapter.ViewPagerItemAdapter;
import com.starcity.api.ApiStores;
import com.starcity.config.ICreate;
import com.starcity.config.RetrofitHaveTokenFactory;
import com.starcity.database.DBManager;
import com.starcity.entity.DaoSession;
import com.starcity.entity.QbPaper;
import com.starcity.entity.QbPaperDao;
import com.starcity.entity.QbQuestion;
import com.starcity.entity.QbQuestionDao;
import com.starcity.entity.QbQuestionOption;
import com.starcity.entity.QbQuestionOptionDao;
import com.starcity.entity.RResult;
import com.starcity.view.FloatingLayerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.hutool.core.util.NumberUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 考试页面
 */
public class TestActivity extends BaseActivity implements Callback<RResult<QbPaper>> {

    public static List<QbQuestion> qbQuestionList = new ArrayList<>();
    public static QbPaper qbPaper = new QbPaper();

    private TextView tv_timer;
    ICreate iCreate = new RetrofitHaveTokenFactory();
    ApiStores retrofitService = (ApiStores) iCreate.getRetrofit().createRetrofitService();
    // 覆盖层
    private FloatingLayerView mFloatingLayerView;
    private GridView gv_all;
    private Button btn_questionFloat;
    private Button toLastQuestion;
    private Button toNextQuestion;
    private ViewPager viewPager;
    private ViewPagerItemAdapter pagerAdapter;
    private Context context;
    private TextView submitPapers;

    /**
     * 当前题目下标
     */
    public static int currentIndex;
    public static int totalQuestionSize;

    private ChooseQuestionAdapter chooseQuestionAdapter;
    private TextView tv_questionNum;

    private RelativeLayout rl_questionMenu;
    static int minute = -1;
    static int second = -1;
    Timer timer;
    TimerTask timerTask;
    //学生剩余时长 单位是秒
    static int studentDuration = 0;


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (minute == 0) {
                if (second == 0) {
                    tv_timer.setText("正在提交!");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                    minute = -1;
                    second = -1;
                    submitPapers();
                } else {
                    second--;
                    if (second >= 10) {
                        tv_timer.setText("0" + minute + ":" + second);
                    } else {
                        tv_timer.setText("0" + minute + ":0" + second);
                    }
                    if (second == 10 * 60) {
                        Toast.makeText(TestActivity.this, "距离考试结束还有十分钟！", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        tv_timer.setText(minute + ":" + second);
                    } else {
                        tv_timer.setText("0" + minute + ":" + second);
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            tv_timer.setText(minute + ":" + second);
                        } else {
                            tv_timer.setText("0" + minute + ":" + second);
                        }
                    } else {
                        if (minute >= 10) {
                            tv_timer.setText(minute + ":0" + second);
                        } else {
                            tv_timer.setText("0" + minute + ":0" + second);
                        }
                    }
                }
            }
        }
    };
    private DaoSession daoSession;
    private QbPaperDao qbPaperDao;
    private QbQuestionDao qbQuestionDao;
    private QbQuestionOptionDao qbQuestionOptionDao;


    @Override
    protected void initData() {
        currentIndex = 0;
        context = this;
        Intent intent = getIntent();
        String paperId = intent.getStringExtra("paperId");
        Call<RResult<QbPaper>> papers = retrofitService.getPapers(paperId);
        papers.enqueue(this);

        //再次获取用户状态
        Call<RResult<Integer>> uptimePaper = retrofitService.getUptimePaper(paperId);
        uptimePaper.enqueue(new Callback<RResult<Integer>>() {
            @Override
            public void onResponse(Call<RResult<Integer>> call, Response<RResult<Integer>> response) {
                RResult<Integer> body = response.body();
                if (body == null || body.getCode() != 200) {
                    Toast.makeText(TestActivity.this, body == null ? "服务异常" : body.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                //单位是秒
                studentDuration = body.getResult();
                //开始倒计时
                beginTimer(studentDuration);
            }

            @Override
            public void onFailure(Call<RResult<Integer>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(TestActivity.this, "接口调用异常！", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

       /* mBackRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("退出要提交试卷！");
                finish();
            }
        });*/
        mFloatingLayerView.setShowView(rl_questionMenu);
        btn_questionFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFloatingLayerView.NONE == mFloatingLayerView.getType()) {
                    mFloatingLayerView.none2Half();
                } else {
                    mFloatingLayerView.beforeInput();
                }
            }
        });
        /** 覆盖层中GridView滑动监听 */
        gv_all.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    mFloatingLayerView.setCanHide(true);
                } else {
                    mFloatingLayerView.setCanHide(false);
                }
            }
        });
        //上一页
        toLastQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex == 0) {
                    Toast.makeText(context, "已为第一题", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewPager.setCurrentItem(--currentIndex);
                tv_questionNum.setText((currentIndex + 1) + "/" + totalQuestionSize);
            }
        });
        //下一页
        toNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentIndex == qbQuestionList.size() - 1) {
                    Toast.makeText(context, "已为最后一题，请点击提交！", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewPager.setCurrentItem(++currentIndex);
                tv_questionNum.setText((currentIndex + 1) + "/" + totalQuestionSize);
            }
        });
        //提交试卷
        submitPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //二次确认是否提交
                AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);//内部类
                builder.setTitle("友情提示");
                builder.setMessage("您确定要提交吗?");
                //确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //停止定时器
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        if (timerTask != null) {
                            timerTask = null;
                        }
                        minute = -1;
                        second = -1;
                        submitPapers();
                    }
                });
                //点取消按钮
                builder.setNegativeButton("取消", null);
                //显示对话框
                builder.show();
            }
        });
    }

    /**
     * 提交试卷
     */
    private void submitPapers() {
        List<QbQuestion> qbQuestionList = TestActivity.qbQuestionList;
        TestActivity.qbPaper.setQuestions(qbQuestionList);
        BigDecimal totalScore = new BigDecimal(0);
        for (int i = 0; i < qbQuestionList.size(); i++) {
            QbQuestion qbQuestion = qbQuestionList.get(i);
            totalScore = NumberUtil.add(totalScore, qbQuestion.getStudentScore());
        }
        //计算用户的总分
        TestActivity.qbPaper.setStudentTotalScore(totalScore.doubleValue());
        Call<RResult> rResultCall = retrofitService.submitPapers(TestActivity.qbPaper.getId(), TestActivity.qbPaper);
        rResultCall.enqueue(new Callback<RResult>() {
            @Override
            public void onResponse(Call<RResult> call, Response<RResult> response) {
                RResult body = response.body();
                if (body == null || body.getCode() != 200) {
                    Toast.makeText(TestActivity.this, body == null ? "服务异常" : body.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(TestActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                    return;
                }
                Toast.makeText(TestActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(TestActivity.this, ScoreActivity.class);
                startActivity(homeIntent);
                finish();
            }

            @Override
            public void onFailure(Call<RResult> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(TestActivity.this, "接口调用异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initID() {
        //获取数据库dao
        daoSession = DBManager.getInstance(this).getDaoSession();
        qbPaperDao = daoSession.getQbPaperDao();
        qbQuestionDao = daoSession.getQbQuestionDao();
        qbQuestionOptionDao = daoSession.getQbQuestionOptionDao();
        /*   mBackRl = (RelativeLayout) findViewById(R.id.back_rl);*/
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        tv_questionNum = (TextView) findViewById(R.id.tv_questionNum);
        btn_questionFloat = (Button) findViewById(R.id.btn_questionFloat);
        toLastQuestion = (Button) findViewById(R.id.toLastQuestion);
        toNextQuestion = (Button) findViewById(R.id.toNextQuestion);
        // 覆盖层
        mFloatingLayerView = (FloatingLayerView) findViewById(R.id.activity_shine_ll_cover);
        gv_all = (GridView) findViewById(R.id.activity_shine_gv_all);
        rl_questionMenu = (RelativeLayout) findViewById(R.id.rl_questionMenu);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        submitPapers = (TextView) findViewById(R.id.submitPapers);

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void onResponse(Call<RResult<QbPaper>> call, Response<RResult<QbPaper>> response) {
        RResult<QbPaper> body = response.body();
        if (body == null || body.getCode() != 200) {
            Toast.makeText(TestActivity.this, body == null ? "服务异常" : body.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        QbPaper qbPaper = body.getResult();
        this.qbPaper = qbPaper;
        //查询数据库中是否存在  如果存在则在数据库中取
        String id = qbPaper.getId();
        List<QbQuestion> questionList;
        List<QbPaper> list = qbPaperDao.queryBuilder().where(QbPaperDao.Properties.Id.eq(id)).list();
        if (list != null && list.size() != 0) {
            questionList = qbQuestionDao.queryBuilder().where(QbQuestionDao.Properties.PaperId.eq(id)).list();
            qbPaper.setQuestions(questionList);
            qbQuestionList = questionList;
        } else {
            questionList = qbPaper.getQuestions();
            qbQuestionList = questionList;
            // 数据库操作，插入试卷、试题、选项
            qbPaperDao.insertOrReplace(qbPaper);
            qbQuestionDao.insertOrReplaceInTx(questionList);
            for (int i = 0; i < questionList.size(); i++) {
                QbQuestion qbQuestion = questionList.get(i);
                List<QbQuestionOption> options = qbQuestion.getOptions();
                qbQuestionOptionDao.insertOrReplaceInTx(options);
            }
        }

        totalQuestionSize = questionList.size();
        //设置总题目数
        tv_questionNum.setText((currentIndex + 1) + "/" + totalQuestionSize);

        //设置题目
        viewPager.setCurrentItem(0);
        pagerAdapter = new ViewPagerItemAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                tv_questionNum.setText((currentIndex + 1) + "/" + totalQuestionSize);
                //更新一下下面的题目按钮颜色
                chooseQuestionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置下方题按钮
        chooseQuestionAdapter = new ChooseQuestionAdapter(this, questionList.size(), viewPager);
        gv_all.setAdapter(chooseQuestionAdapter);
    }

    /**
     * 开始倒计时
     *
     * @param duration 考试时长
     */
    private void beginTimer(int duration) {
        if (minute == -1 && second == -1) {
            minute = duration/60;
            second = duration%60;
        }

        tv_timer.setText(minute + ":" + second);

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }


    @Override
    public void onFailure(Call<RResult<QbPaper>> call, Throwable t) {
        System.out.println(t.getMessage());
        Toast.makeText(TestActivity.this, "接口调用异常！", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(TestActivity.this, "考试未结束不允许退出！", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}

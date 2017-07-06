package com.example.administrator.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Examlnfo;
import com.example.administrator.myapplication.bean.Question;
import com.example.administrator.myapplication.biz.ExamBiz;
import com.example.administrator.myapplication.biz.IExamBiz;
import com.example.administrator.myapplication.view.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{

    TextView tvExamInfo,tvExamTitle,tvOp1,tvOp2,tvOp3,tvOp4,tvload,tvexamno,tvTime;
    CheckBox cb01,cb02,cb03,cb04;
    CheckBox[] cbs = new CheckBox[4];
    LinearLayout layoutLoading,layout03,layout04;
    ProgressBar dialog;
    Gallery mGallery;
    ImageView mImageView;
    IExamBiz biz;
    QuestionAdapter mAdapter;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;

    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;
    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast = new LoadExamBroadcast();
        mLoadQuestionBroadcast = new LoadQuestionBroadcast();
        setListener();
        initView();
        biz=new ExamBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvload.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layout03 = (LinearLayout) findViewById(R.id.layout_03);
        layout04 = (LinearLayout) findViewById(R.id.layout_04);
        layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
        dialog = (ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle=(TextView)findViewById(R.id.exam_title);
        tvexamno=(TextView)findViewById(R.id.tv_exam_no);
        tvOp1=(TextView)findViewById(R.id.tv_op1);
        tvOp2=(TextView)findViewById(R.id.tv_op2);
        tvOp3=(TextView)findViewById(R.id.tv_op3);
        tvOp4=(TextView)findViewById(R.id.tv_op4);
        cb01 = (CheckBox) findViewById(R.id.cb_01);
        cb02 = (CheckBox) findViewById(R.id.cb_02);
        cb03 = (CheckBox) findViewById(R.id.cb_03);
        cb04 = (CheckBox) findViewById(R.id.cb_04);
        tvTime= (TextView) findViewById(R.id.tv_Time);
        mGallery= (Gallery) findViewById(R.id.gallery);
        cbs[0] = cb01;
        cbs[1] = cb02;
        cbs[2] = cb03;
        cbs[3] = cb04;
        tvload=(TextView)findViewById(R.id.tv_load);
        mImageView=(ImageView)findViewById(R.id.im_exam_imge);
        layoutLoading.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);
    }
        //选择答案
        CompoundButton.OnCheckedChangeListener listener =new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    int userAnswer = 0;
                    switch (buttonView.getId()) {
                        case R.id.cb_01:
                            userAnswer = 1;
                            break;
                        case R.id.cb_02:
                            userAnswer = 2;
                            break;
                        case R.id.cb_03:
                            userAnswer = 3;
                            break;
                        case R.id.cb_04:
                            userAnswer = 4;
                            break;
                    }

                    if (userAnswer > 0) {
                        for (CheckBox cb : cbs) {
                            cb.setChecked(false);
                        }
                        cbs[userAnswer - 1].setChecked(true);
                    }
                }
            }
        };
    private void initData(){
        if(isLoadExamInfoReceiver && isLoadQuestionsReceiver){
            if(isLoadExamInfo&&isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                Examlnfo examInfo = ExamApplication.getInstance().getmExamInfo();
                if (examInfo != null) {
                    showData(examInfo);
                    initTimer(examInfo);
                }
                    initGallery();
                    showExam(biz.getExam());
            }else{
                    layoutLoading.setEnabled(true);
                    dialog.setVisibility(View.GONE);
                    tvload.setText("下载失败，点击重试！");
            }
        }
    }
    //滚动条
    private void initGallery() {
        mAdapter = new QuestionAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveUserAnwer();
                showExam(biz.getExam(i));
            }
        });
    }
    //计分
    private void initTimer(Examlnfo exanInfo) {
        int sumTime=exanInfo.getLimitTime()*60*1000;
        final long overTime = sumTime+ System.currentTimeMillis();
        final Timer time=new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                long l =  overTime - System.currentTimeMillis();
                final long min = (l/1000/60);
                final long sec = (l/1000%60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("剩余时间："+min+"分"+sec+"秒");
                    }
                });
            }
        },0,1000);
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                time.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        },sumTime);
    }

    private void showExam(Question question) {
        Log.e("showExam","showExam,exam="+question);
        if(question!=null){
            tvexamno.setText(biz.getExamIndex());
            tvExamTitle.setText(question.getQuestion());
            tvOp1.setText(question.getItem1());
            tvOp2.setText(question.getItem2());
            tvOp3.setText(question.getItem3());
            tvOp4.setText(question.getItem4());
            layout03.setVisibility(question.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb03.setVisibility(question.getItem3().equals("")?View.GONE:View.VISIBLE);
            layout04.setVisibility(question.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb04.setVisibility(question.getItem4().equals("")?View.GONE:View.VISIBLE);
            if(question.getUrl()!=null && !question.getUrl().equals("")) {
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(question.getUrl())
                        .into(mImageView);
            }else{
                mImageView.setVisibility(View.GONE);
            }
            resetOptions();
            String userAnswer = question.getUseranswer();
            if(userAnswer!=null && !userAnswer.equals("")){
                int userCB = Integer.parseInt(userAnswer)-1;
                cbs[userCB].setChecked(true);
                setOptions(true);
            }else{
                setOptions(false);
            }
        }
    }
    private void setOptions(boolean hasAnuser){
        for (CheckBox cb : cbs) {
            cb.setEnabled(!hasAnuser);
        }
    }

    private void resetOptions() {
        for(CheckBox cb:cbs){
            cb.setChecked(false);
        }
    }

    private  void saveUserAnwer(){
        for(int i=0;i<cbs.length;i++){
           if(cbs[i].isChecked()){
               biz.getExam().setUseranswer(String.valueOf(i+1));
               setOptions(true);
               mAdapter.notifyDataSetChanged();
               return;
           }
        }
        biz.getExam().setUseranswer("");
        mAdapter.notifyDataSetChanged();
    }

    private void showData(Examlnfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if(mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    public void preExam(View view) {
        saveUserAnwer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUserAnwer();
        showExam(biz.nextQuestion());
    }
    //提交显示分数
    public void commit(View view) {
        saveUserAnwer();
        int s = biz.commitExam();
        View inflate = View.inflate(this,R.layout.layout_result,null);
        TextView tvResult = (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n"+s+"分！");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        builder.create().show();
    }

    class LoadExamBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
       Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
        if(isSuccess){
            isLoadExamInfo = true;
        }
        isLoadExamInfoReceiver = true;
        initData();
    }
}
    class LoadQuestionBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadQuestions = true;
            }
            isLoadQuestionsReceiver = true;
            initData();
        }
    }
}

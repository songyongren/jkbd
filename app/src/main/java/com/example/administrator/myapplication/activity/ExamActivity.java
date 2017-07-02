package com.example.administrator.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Examlnfo;
import com.example.administrator.myapplication.bean.Question;
import com.example.administrator.myapplication.biz.ExamBiz;
import com.example.administrator.myapplication.biz.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvExamInfo,tvExamTitle,tvOp1,tvOp2,tvOp3,tvOp4;
    ImageView mImageView;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;

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
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {

        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle=(TextView)findViewById(R.id.exam_title);
        tvOp1=(TextView)findViewById(R.id.tv_op1);
        tvOp2=(TextView)findViewById(R.id.tv_op2);
        tvOp3=(TextView)findViewById(R.id.tv_op3);
        tvOp4=(TextView)findViewById(R.id.tv_op4);
        mImageView=(ImageView)findViewById(R.id.im_exam_imge);
    }

    private void initData(){
        if(isLoadExamInfo&&isLoadQuestions) {
            Examlnfo examInfo = ExamApplication.getInstance().getmExamInfo();
            if (examInfo != null) {
                showData(examInfo);
            }
            List<Question> Queslist = ExamApplication.getInstance().getmExamList();
            if (Queslist != null) {
                showExam(Queslist);

            }
        }
    }

    private void showExam(List<Question> queslist) {
        Question question=queslist.get(0);
        if(question!=null){
            tvExamTitle.setText(question.getQuestion());
            tvOp1.setText(question.getItem1());
            tvOp2.setText(question.getItem2());
            tvOp3.setText(question.getItem3());
            tvOp4.setText(question.getItem4());
            Picasso.with(ExamActivity.this)
                    .load(question.getUrl())
                    .into(mImageView);
        }
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

    class LoadExamBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
       Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
        if(isSuccess){
            isLoadExamInfo = true;
        }
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
            initData();
        }
    }
}

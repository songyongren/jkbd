package com.example.administrator.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Examlnfo;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvExamInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        initView();
        initData();
    }

    private void initView() {

        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
    }

    private void initData(){
        Examlnfo examInfo= ExamApplication.getInstance().getmExamInfo();
        if(examInfo!=null){
            showData(examInfo);
        }
    }

    private void showData(Examlnfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }


}

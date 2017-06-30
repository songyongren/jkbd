package com.example.administrator.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.administrator.myapplication.bean.Examlnfo;
import com.example.administrator.myapplication.bean.*;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.example.administrator.myapplication.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application{
    Examlnfo mExamInfo;
    List<Question> mExamList;
    private static ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;

        initData();
    }
    public static ExamApplication getInstance(){

        return instance;
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {




            }
        }).start();
    }

    public Examlnfo getmExamInfo() {
        return mExamInfo;
    }

    public void setmExamInfo(Examlnfo mExamInfo) {
        this.mExamInfo = mExamInfo;
    }

    public List<Question> getmExamList() {
        return mExamList;
    }

    public void setmExamList(List<Question> mExamList) {
        this.mExamList = mExamList;
    }
}

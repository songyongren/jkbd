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


        OkHttpUtils<Examlnfo> utils= new OkHttpUtils<>(getApplicationContext());
        String url= "http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(Examlnfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<Examlnfo>() {
                    @Override
                    public void onSuccess(Examlnfo result) {

                        Log.e("main","result="+result);
                        mExamInfo=result;
                    }

                    @Override
                    public void onError(String error) {

                        Log.e("main","error"+error);
                    }
                });
                OkHttpUtils<String>utils1=new OkHttpUtils<String>(instance);
                String url2 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                               Result result1= ResultUtils.getListResultFromJson(result);
                               if(result1!=null&&result1.getError_code()==0){
                                   List<Question>list =result1.getResult();
                                   if(list!=null&&list.size()>0){
                                       mExamList=list;
                                   }
                               }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });
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

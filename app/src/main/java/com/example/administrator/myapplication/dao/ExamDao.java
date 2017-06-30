package com.example.administrator.myapplication.dao;

import android.util.Log;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.bean.Examlnfo;
import com.example.administrator.myapplication.bean.Question;
import com.example.administrator.myapplication.bean.Result;
import com.example.administrator.myapplication.utils.OkHttpUtils;
import com.example.administrator.myapplication.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamDao  implements IExamDao{
    @Override
    public void loadExamInfo() {
        OkHttpUtils<Examlnfo> utils= new OkHttpUtils<>(ExamApplication.getInstance());
        String url= "http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(Examlnfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<Examlnfo>() {
                    @Override
                    public void onSuccess(Examlnfo result) {

                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setmExamInfo(result);

                    }

                    @Override
                    public void onError(String error) {

                        Log.e("main","error"+error);
                    }
                });
    }

    @Override
    public void loadQuestionLists() {
        OkHttpUtils<String>utils1=new OkHttpUtils<String>(ExamApplication.getInstance());
        String url2 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Result result1= ResultUtils.getListResultFromJson(result);
                        if(result1!=null&&result1.getError_code()==0){
                            List<Question> list =result1.getResult();
                            if(list!=null&&list.size()>0){
                                ExamApplication.getInstance().setmExamList(list);

                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
    }
}

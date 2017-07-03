package com.example.administrator.myapplication.biz;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.bean.Question;
import com.example.administrator.myapplication.dao.ExamDao;
import com.example.administrator.myapplication.dao.IExamDao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex = 0;
    List<Question> examList = null;
    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
         examIndex=0;
         dao.loadExamInfo();
         dao.loadQuestionLists();
         examList   = ExamApplication.getInstance().getmExamList();
    }

    @Override
    public Question getExam() {
        if (examList!=null) {
            return examList.get(examIndex);
        }else{
            return null;
        }
    }
    @Override
    public Question nextQuestion() {
        if(examList!=null&& examIndex<examList.size()-1){
            examIndex++;
            return examList.get(examIndex);
        }else{
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if(examList!=null&& examIndex>0){
            examIndex--;
            return examList.get(examIndex);
        }else{
            return null;
        }
    }

    @Override
    public void commitExam() {

    }
}

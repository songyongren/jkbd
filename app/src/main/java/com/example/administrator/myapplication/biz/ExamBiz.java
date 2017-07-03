package com.example.administrator.myapplication.biz;

import com.example.administrator.myapplication.dao.ExamDao;
import com.example.administrator.myapplication.dao.IExamDao;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex = 0;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
         dao.loadExamInfo();
         dao.loadQuestionLists();

    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}

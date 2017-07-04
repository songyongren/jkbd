package com.example.administrator.myapplication.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.ExamApplication;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter{
    Context mContext;
   List<Question> examList;
    public QuestionAdapter(Context mContext) {
        mContext = mContext;
       examList= ExamApplication.getInstance().getmExamList();
    }

    @Override
    public int getCount() {
        return examList==null?0:examList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.exam_question,null);
        TextView tvNo = view.findViewById(R.id.tv_no);
        ImageView ivQuestion = view.findViewById(R.id.iv_question);
        tvNo.setText("第"+(position+1)+"题");
        return view;
    }
}

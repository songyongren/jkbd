package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Examlnfo;
import com.example.administrator.myapplication.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
    }
    //随机测试跳转
    public void test(View view){
        //获取网络数据
        OkHttpUtils<Examlnfo> utils= new OkHttpUtils<>(getApplicationContext());
        String url= "http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(Examlnfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<Examlnfo>() {
                    @Override
                    public void onSuccess(Examlnfo result) {
                        Log.e("main","result="+result);
                    }

                    @Override
                    public void onError(String error) {
                       Log.e("main","error"+error);
                    }
                });


        startActivity(new Intent(MainActivity.this,ExamActivity.class));

    }
    //全部测试跳转
    public void alltest(View view){
        startActivity(new Intent(MainActivity.this,AllexamActivity.class));
    }
    //设置跳转
    public void settest(View view){
        startActivity(new Intent(MainActivity.this,AllexamActivity.class));
    }
    //退出
    public void exit(View view){
        System.exit(0);
        finish();
    }

}

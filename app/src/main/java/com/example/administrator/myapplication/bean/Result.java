package com.example.administrator.myapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */



public class Result {


    /**
     * error_code : 0
     * reason : ok
     * result : [{"id":30,"question":"这个标志是何含义？","answer":"2","item1":"十字交叉路口预告","item2":"互通式立体交叉预告","item3":"Y型交叉路口预告","item4":"环行交叉路口预告","explains":"互通式立体交叉：路线交叉中的一种重要类型。交叉道路之间立体交叉并以匝道相互连通以实现交通转换的交叉。","url":"http://images.juheapi.com/jztk/c1c2subject1/30.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Question> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }

}


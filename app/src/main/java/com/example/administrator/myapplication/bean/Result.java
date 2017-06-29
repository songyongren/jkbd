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
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 30
         * question : 这个标志是何含义？
         * answer : 2
         * item1 : 十字交叉路口预告
         * item2 : 互通式立体交叉预告
         * item3 : Y型交叉路口预告
         * item4 : 环行交叉路口预告
         * explains : 互通式立体交叉：路线交叉中的一种重要类型。交叉道路之间立体交叉并以匝道相互连通以实现交通转换的交叉。
         * url : http://images.juheapi.com/jztk/c1c2subject1/30.jpg
         */

        private int id;
        private String question;
        private String answer;
        private String item1;
        private String item2;
        private String item3;
        private String item4;
        private String explains;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getItem1() {
            return item1;
        }

        public void setItem1(String item1) {
            this.item1 = item1;
        }

        public String getItem2() {
            return item2;
        }

        public void setItem2(String item2) {
            this.item2 = item2;
        }

        public String getItem3() {
            return item3;
        }

        public void setItem3(String item3) {
            this.item3 = item3;
        }

        public String getItem4() {
            return item4;
        }

        public void setItem4(String item4) {
            this.item4 = item4;
        }

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }
}


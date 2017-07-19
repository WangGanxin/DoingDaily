package com.ganxin.doingdaily.common.data.model;

import java.util.List;

/**
 * Description : ShowapiResBodyBean  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/18 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ShowapiResBodyBean {

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }
}

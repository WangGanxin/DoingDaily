package com.ganxin.doingdaily.common.data.model;

import java.io.Serializable;

/**
 * Description : PictureBean  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/18 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureBean implements Serializable{

    private String url;
    private String title;
    private String date;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

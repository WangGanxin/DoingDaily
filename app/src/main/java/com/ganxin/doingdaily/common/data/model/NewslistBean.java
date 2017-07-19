package com.ganxin.doingdaily.common.data.model;

/**
 * Description : NewslistBean  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/18 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class NewslistBean {

    private String title;
    private String picUrl;
    private String description;
    private String ctime;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

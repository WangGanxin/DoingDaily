package com.ganxin.doingdaily.common.data.model;

import java.io.Serializable;

/**
 * Description : ImageurlsBean  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/29 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class ImageurlsBean implements Serializable{

    private int height;
    private int width;
    private String url;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

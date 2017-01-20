package com.ganxin.doingdaily.common.data.model;

import java.io.Serializable;

/**
 * Description : WechatContentlistBean  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatContentlistBean implements Serializable{

    private String date;
    private String weixinNum;
    private String url;
    private String ct;
    private String id;
    private String typeName;
    private String title;
    private String contentImg;
    private String userLogo;
    private String userName;
    private int read_num;
    private int like_num;
    private String typeId;
    private String userLogo_code;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeixinNum() {
        return weixinNum;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserLogo_code() {
        return userLogo_code;
    }

    public void setUserLogo_code(String userLogo_code) {
        this.userLogo_code = userLogo_code;
    }

}

package com.ganxin.doingdaily.common.data.model;

import java.util.List;

/**
 * Description : WechatPageBean  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class WechatPageBean {

    private int allPages;
    private int currentPage;
    private int allNum;
    private int maxResult;

    private List<WechatContentlistBean> contentlist;

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<WechatContentlistBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<WechatContentlistBean> contentlist) {
        this.contentlist = contentlist;
    }
}

package com.ganxin.doingdaily.common.data.model;

import java.util.List;

/**
 * Description : NewsPageBean  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/29 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsPageBean {

    private int allPages;
    private int currentPage;
    private int allNum;
    private int maxResult;

    private List<NewsContentlistBean> contentlist;

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

    public List<NewsContentlistBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<NewsContentlistBean> contentlist) {
        this.contentlist = contentlist;
    }
}

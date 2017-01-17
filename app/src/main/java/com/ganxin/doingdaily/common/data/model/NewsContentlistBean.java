package com.ganxin.doingdaily.common.data.model;

import java.util.List;

/**
 * Description : NewsContentlistBean  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/29 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class NewsContentlistBean {

    private String content;
    private String pubDate;
    private boolean havePic;
    private String title;
    private String channelName;
    private String desc;
    private String source;
    private String channelId;
    private String link;
    private String html;
    private List<AllListBean> allList;
    private List<ImageurlsBean> imageurls;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isHavePic() {
        return havePic;
    }

    public void setHavePic(boolean havePic) {
        this.havePic = havePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<AllListBean> getAllList() {
        return allList;
    }

    public void setAllList(List<AllListBean> allList) {
        this.allList = allList;
    }

    public List<ImageurlsBean> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageurlsBean> imageurls) {
        this.imageurls = imageurls;
    }
}

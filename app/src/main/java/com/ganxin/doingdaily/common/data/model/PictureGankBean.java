package com.ganxin.doingdaily.common.data.model;

import java.util.List;

/**
 * Description : PictureGankBean  <br/>
 * author : WangGanxin <br/>
 * date : 2017/7/18 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class PictureGankBean {

    private boolean error;
    private List<PictureGankRsp> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<PictureGankRsp> getResults() {
        return results;
    }

    public void setResults(List<PictureGankRsp> results) {
        this.results = results;
    }

}

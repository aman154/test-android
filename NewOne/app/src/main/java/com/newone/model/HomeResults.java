package com.newone.model;

import com.google.gson.annotations.Expose;

/**
 * Created by aman on 25/11/15.
 */
public class HomeResults {

    @Expose
    private String mpic;
    @Expose
    private String tt;
    @Expose
    private String tg;

    public String getMpic() {
        return mpic;
    }

    public void setMpic(String mpic) {
        this.mpic = mpic;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }
}

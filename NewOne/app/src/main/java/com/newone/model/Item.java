package com.newone.model;

import com.google.gson.annotations.Expose;


/**
 * Created by aman on 29/10/15.
 */
public class Item {

    @Expose
    private String mpic;
    @Expose
    private String mtittle;
    @Expose
    private int mCount;

    public String getMpic() {
        return mpic;
    }

    public void setMpic(String mpic) {
        this.mpic = mpic;
    }

    public String getMtittle() {
        return mtittle;
    }

    public void setMtittle(String mtittle) {
        this.mtittle = mtittle;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }
}

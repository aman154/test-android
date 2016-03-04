package com.newone.model;

import com.google.gson.annotations.Expose;

/**
 * Created by aman on 25/11/15.
 */
public class ProductResults {

    @Expose
    private String pc;
    @Expose
    private String mpic;
    @Expose
    private String pt;
    @Expose
    private String pd;
    @Expose
    private String ptg;
    @Expose
    private String pstg;
    @Expose
    private int pmrp;
    @Expose
    private int qnt;

    public String getMpic() {
        return mpic;
    }

    public void setMpic(String mpic) {
        this.mpic = mpic;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public int getPmrp() {
        return pmrp;
    }

    public void setPmrp(int pmrp) {
        this.pmrp = pmrp;
    }

    public int getQnt() {
        return qnt;
    }

    public void setQnt(int qnt) {
        this.qnt = qnt;
    }

    public String getPtg() {
        return ptg;
    }

    public void setPtg(String ptg) {
        this.ptg = ptg;
    }

    public String getPstg() {
        return pstg;
    }

    public void setPstg(String pstg) {
        this.pstg = pstg;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }
}

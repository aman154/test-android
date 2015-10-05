package com.example.aman.zwan.models;

import java.util.ArrayList;

/**
 * Created by aman on 24/9/15.
 */
public class DrawerElement  {

    private String tittle;
    private String icon;
    private String isBigBackground;
    private String isSubElement;
    private ArrayList<DrawerSubElement> subElements;


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getIsBigBackground() {
        return isBigBackground;
    }

    public void setIsBigBackground(String isBigBackground) {
        this.isBigBackground = isBigBackground;
    }

    public String getIsSubElement() {
        return isSubElement;
    }

    public void setIsSubElement(String isSubElement) {
        this.isSubElement = isSubElement;
    }

    public ArrayList<DrawerSubElement> getSubElements() {
        return subElements;
    }

    public void setSubElements(ArrayList<DrawerSubElement> subElements) {
        this.subElements = subElements;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

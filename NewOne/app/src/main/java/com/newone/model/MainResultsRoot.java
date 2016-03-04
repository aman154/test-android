package com.newone.model;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aman on 29/10/15.
 */
public class MainResultsRoot {

    @Expose
    private List<HomeResults> homeResults  = new ArrayList<>();

    public List<HomeResults> getHomeResults() {
        return homeResults;
    }

    public void setHomeResults(List<HomeResults> homeResults) {
        this.homeResults = homeResults;
    }
    @Expose
    private List<ProductResults> productResults = new ArrayList<>();

    public List<ProductResults> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<ProductResults> productResults) {
        this.productResults = productResults;
    }

    @Expose
    private List<String> productType;

    public List<String> getProductType() {
        return productType;
    }

    public void setProductType(List<String> productType) {
        this.productType = productType;
    }


  /*  @Expose
    private List<Item> items = new ArrayList<>();

   public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }*/

}

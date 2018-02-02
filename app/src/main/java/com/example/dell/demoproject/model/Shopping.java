package com.example.dell.demoproject.model;

import java.util.List;

/**
 * Created by dell on 30-01-2018.
 */

public class Shopping {
    private List<Categories> categories;
    private List<Rankings> rankings;

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<Rankings> getRankings() {
        return rankings;
    }

    public void setRankings(List<Rankings> rankings) {
        this.rankings = rankings;
    }


}

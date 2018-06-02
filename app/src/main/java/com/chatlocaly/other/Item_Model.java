package com.chatlocaly.other;

/**
 * Created by anjani on 19/12/17.
 */
public class Item_Model {
    /*  Model class for List and Recycler Items  */
    private String title, subTitle;

    public Item_Model(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}

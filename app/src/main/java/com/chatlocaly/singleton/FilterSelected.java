package com.chatlocaly.singleton;

import com.chatlocaly.model.FilterModel;

/**
 * Created by anjani on 15/1/18.
 */

public class FilterSelected {


    public static FilterSelected singleton;
    private FilterModel filterModel;


    public FilterSelected() {
    }

    public static FilterSelected getSingleton() {
        return singleton;
    }

    public static void setSingleton(FilterSelected singleton) {
        FilterSelected.singleton = singleton;
    }

    public static FilterSelected getInstance() {

        if (singleton == null) {
            singleton = new FilterSelected();
        }
        return singleton;
    }

    public static void reset() {
        singleton = new FilterSelected();

    }

    public FilterModel getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(FilterModel filterModel) {
        this.filterModel = filterModel;
    }






}

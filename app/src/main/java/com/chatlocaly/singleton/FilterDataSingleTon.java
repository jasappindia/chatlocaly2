package com.chatlocaly.singleton;

import android.util.Log;

import com.chatlocaly.activity.FilterActivity;
import com.chatlocaly.model.CategoryListModel;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.model.HomeServiceSelected;
import com.chatlocaly.model.LocalityListModel;
import com.chatlocaly.singleton.singletonmodel.BrandListModel;
import com.chatlocaly.singleton.singletonmodel.DiscountListModel;
import com.chatlocaly.singleton.singletonmodel.PriceListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjani on 15/1/18.
 */

public class FilterDataSingleTon {


    public static FilterDataSingleTon singleton;
    private  List<CategoryListModel.DataData.CategoryListData> categoryListData;
    private  BrandListModel brandListModel;
    private  PriceListModel priceListModel;
    private  DiscountListModel discountListModel;
    private  LocalityListModel LocalityListModel;
    private  FilterModel filterModel;
    private  HomeServiceSelected homeServiceSelected;

    public HomeServiceSelected getHomeServiceSelected() {
        if(homeServiceSelected!=null)
            return homeServiceSelected;
        else
        {
            homeServiceSelected=new HomeServiceSelected();
            homeServiceSelected.setHome_service("EVERYWHERE");
            homeServiceSelected.setService_km("0");
            return homeServiceSelected;
        }
    }

    public void setHomeServiceSelected(HomeServiceSelected homeServiceSelected) {
        this.homeServiceSelected = homeServiceSelected;
    }

    public FilterModel getNonselectedfilterModel() {
        return nonselectedfilterModel;
    }

    public void setNonselectedfilterModel(FilterModel nonselectedfilterModel) {

          this.nonselectedfilterModel=new  FilterModel();
          this.nonselectedfilterModel.setData(nonselectedfilterModel.getData());


         }

    private FilterModel nonselectedfilterModel;



    public FilterDataSingleTon() {
    }

    public static FilterDataSingleTon getSingleton() {
        return singleton;
    }

    public static void setSingleton(FilterDataSingleTon singleton) {
        FilterDataSingleTon.singleton = singleton;
    }

    public static FilterDataSingleTon getInstance() {

        if (singleton == null) {
            singleton = new FilterDataSingleTon();
        }
        return singleton;
    }

    public static void reset() {
        singleton = new FilterDataSingleTon();

    }

    public  void cleanFilterlist()
    {

        this.filterModel=new FilterModel();
        this.filterModel=getInstance().getNonselectedfilterModel();


    }



    public FilterModel getFilterModel() {
        return filterModel;
    }

    public void setFilterModel(FilterModel filterModel) {

        if(FilterActivity.filtercount<1) {
            Log.e("setData", "message");
            this.filterModel = filterModel;
        }
    }

    public  List<CategoryListModel.DataData.CategoryListData> getCategoryListData() {
        return categoryListData;
    }

    public void setCategoryListData(List<CategoryListModel.DataData.CategoryListData> categoryListData) {
        this.categoryListData = categoryListData;
    }

    public BrandListModel getBrandListModel() {
        return brandListModel;
    }

    public void setBrandListModel(BrandListModel brandListModel) {
        this.brandListModel = brandListModel;
    }

    public PriceListModel getPriceListModel() {
        return priceListModel;
    }

    public void setPriceListModel(PriceListModel priceListModel) {
        this.priceListModel = priceListModel;
    }

    public DiscountListModel getDiscountListModel() {
        return discountListModel;
    }

    public void setDiscountListModel(DiscountListModel discountListModel) {
        this.discountListModel = discountListModel;
    }

    public com.chatlocaly.model.LocalityListModel getLocalityListModel() {
        return LocalityListModel;
    }

    public void setLocalityListModel(com.chatlocaly.model.LocalityListModel localityListModel) {
        LocalityListModel = localityListModel;
    }
}

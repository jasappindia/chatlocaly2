package com.chatlocaly.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.fragment.BusinessFragment;
import com.chatlocaly.fragment.filter.BrandFragment;
import com.chatlocaly.fragment.filter.CategoryFragment;
import com.chatlocaly.fragment.filter.DiscountFragment;
import com.chatlocaly.fragment.filter.HomeServiceFragment;
import com.chatlocaly.fragment.filter.LocalityFragment;
import com.chatlocaly.fragment.filter.PriceFragment;
import com.chatlocaly.fragment.filter.SortFragment;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.Filter;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.singleton.FilterDataSingleTon;
import com.chatlocaly.singleton.FilterSelected;
import com.chatlocaly.utill.BasicUtill;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static java.util.logging.Logger.global;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {


    public static int requestFilteredActivity = 1020;
    public static int filtercount = 0;
    public static int BUSINESS_FRAGMENT = 2005;
    public static int PORODUCT_FRAGMENT = 2010;
    public static int SERVICE_FRAGMENT = 2011;

    public static boolean ISBACKPRESS=false;
    public static boolean IS_FILTERED_APPLIED = false;
    List listcheck, arrlist2;
    private TextView tv_homeService, tv_sort, tv_locality, tv_category, tv_toolbartitle, tv_brand, tv_price, tv_discount;
    private Button btn_clear, btn_apply;
    private LinearLayout ll_category;
    private Toolbar mToolbar;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private FilterActivity context;
    private String user_id, sec_key, city_id, sub_cate_id = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();
        setOnClick();

        // check multiple device login
        new BasicUtill().CheckStatus(this);


        // call fragment
        replaceFragment(new CategoryFragment(), Constants.FILTER_CATEGORY_FRAGMENT);
        changeButtonbackcolor(0);


    }

    private void setOnClick() {

        ll_category.setOnClickListener(this);
        tv_locality.setOnClickListener(this);
        tv_homeService.setOnClickListener(this);
        tv_brand.setOnClickListener(this);

        tv_brand.setOnClickListener(this);
        tv_price.setOnClickListener(this);
        tv_discount.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        btn_clear.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString(BusinessFragment.SELECTED_SUBCATEGORY) != null && !getIntent().getExtras().getString(BusinessFragment.SELECTED_SUBCATEGORY).trim().equalsIgnoreCase(""))
                sub_cate_id = getIntent().getExtras().getString(BusinessFragment.SELECTED_SUBCATEGORY).trim();

            if (StoreListActivity.APPLYFILTERCOUNT == 0 || !ISBACKPRESS)
                //set subcategory selected  when user first time go on storelist
                setFilterId(sub_cate_id);
        }


    }

    private void setFilterId(String sub_cate_id) {


        if (FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList().size() > 0) {
            int parent = 0;
            for (FilterModel.FilterData.CategoryListData categoryListData : FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList()) {
                int child = 0;
                for (FilterModel.FilterData.CategoryListData.SubCategoriesData subcateg : categoryListData.getSubCategories()) {
                    if (subcateg.getSubCatId().equalsIgnoreCase(sub_cate_id)) {
                        filtercount = 1;
                        FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList().get(parent).getSubCategories().get(child).setIsselecteble(true);
                        changeClearTextcolor();

                    }
                    child = child + 1;
                }
                parent = parent + 1;

            }
        }
    }

    private void init() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbartitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        tv_toolbartitle.setText(" Filters");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        context = FilterActivity.this;

        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);
/**********************************                   ***********************/

        ll_category = (LinearLayout) findViewById(R.id.ll_category);
        tv_category = (TextView) findViewById(R.id.tv_category);
        tv_locality = (TextView) findViewById(R.id.tv_locality);
        tv_homeService = (TextView) findViewById(R.id.tv_home_service);
        tv_brand = (TextView) findViewById(R.id.tv_brand);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_discount = (TextView) findViewById(R.id.tv_discount);

//************ *******************/
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_apply = (Button) findViewById(R.id.btn_apply);
        //
        user_id = chatlocalyshareprefrence.getUserId();
        sec_key = "sky_id";
        city_id = chatlocalyshareprefrence.getCityId();

    }

    // get fragment replace
    public void replaceFragment(Fragment inputfragment, String fragmenttitle) {


        try {

            Fragment fragment = inputfragment;

            if (fragment != null) {

// for
           /* if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                    Log.e("fragment name", fragmenttitle + " " + getSupportFragmentManager().getBackStackEntryAt(i).getName());
                    if (fragmenttitle.equalsIgnoreCase((getSupportFragmentManager().getBackStackEntryAt(i).getName()))) {
                        int count = getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name", fragmenttitle + " " + count);

                        getSupportFragmentManager().popBackStack(fragmenttitle, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        int count2 = getSupportFragmentManager().getBackStackEntryCount();
                        Log.e("fragment name2", fragmenttitle + " " + count2);

                    }

                }
            }*/

                getSupportFragmentManager().beginTransaction().replace(R.id.filter_contaner, fragment).commit();

            }
        } catch (Exception e) {

        }

    }

    public String getSub_cate_id() {
        return sub_cate_id;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.ll_category:

                replaceFragment(new CategoryFragment(), Constants.FILTER_CATEGORY_FRAGMENT);
                changeButtonbackcolor(0);
                break;
            case R.id.tv_locality:
                replaceFragment(new LocalityFragment(), Constants.FILTER_LOCALITY_FRAGMET);
                changeButtonbackcolor(1);
                break;

            case R.id.tv_brand:
                replaceFragment(new BrandFragment(), Constants.FILTER_BRAND);
                changeButtonbackcolor(2);
                break;
            case R.id.tv_price:
                replaceFragment(new PriceFragment(), Constants.FILTER_PRICE);
                changeButtonbackcolor(3);
                break;
            case R.id.tv_discount:
                replaceFragment(new DiscountFragment(), Constants.FILTER_DISCOUNT);
                changeButtonbackcolor(4);
                break;
            case R.id.tv_home_service:
                replaceFragment(new HomeServiceFragment(), Constants.FILTER_HOMESERVICE_FRAGMET);
                changeButtonbackcolor(5);
                break;

            case R.id.btn_apply:


                if (filtercount > 0) {
                     ISBACKPRESS=false;
                    listcheck = FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList();
                    Intent intent = new Intent(this, StoreListActivity.class);
                    setResult(RESULT_OK, intent);
                    finish();
                } else
                    Utill.showCenteredToast(context, getString(R.string.str_please_select_category));
                break;
            case R.id.btn_clear:
                if (filtercount >= 1)
                    getFilterList(sec_key, user_id, city_id);
                else
                    Utill.showCenteredToast(context, "No category selected");

                break;


        }

    }
    /// change back ground color


    public void changeButtonbackcolor(int position) {
        switch (position) {
            case 0:
                ll_category.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_category.setTextColor(getResources().getColor(R.color.white));

                tv_locality.setBackgroundColor(getResources().getColor(R.color.white));
                tv_locality.setTextColor(getResources().getColor(R.color.black));

                tv_brand.setBackgroundColor(getResources().getColor(R.color.white));
                tv_brand.setTextColor(getResources().getColor(R.color.black));

                tv_price.setBackgroundColor(getResources().getColor(R.color.white));
                tv_price.setTextColor(getResources().getColor(R.color.black));

                tv_discount.setBackgroundColor(getResources().getColor(R.color.white));
                tv_discount.setTextColor(getResources().getColor(R.color.black));

                tv_homeService.setBackgroundColor(getResources().getColor(R.color.white));
                tv_homeService.setTextColor(getResources().getColor(R.color.black));
                break;


            case 1:
                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_locality.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_locality.setTextColor(getResources().getColor(R.color.white));

                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_brand.setBackgroundColor(getResources().getColor(R.color.white));
                tv_brand.setTextColor(getResources().getColor(R.color.black));

                tv_price.setBackgroundColor(getResources().getColor(R.color.white));
                tv_price.setTextColor(getResources().getColor(R.color.black));

                tv_discount.setBackgroundColor(getResources().getColor(R.color.white));
                tv_discount.setTextColor(getResources().getColor(R.color.black));

                tv_homeService.setBackgroundColor(getResources().getColor(R.color.white));
                tv_homeService.setTextColor(getResources().getColor(R.color.black));
                break;


            case 2:
                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_locality.setBackgroundColor(getResources().getColor(R.color.white));
                tv_locality.setTextColor(getResources().getColor(R.color.black));

                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_brand.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_brand.setTextColor(getResources().getColor(R.color.white));

                tv_price.setBackgroundColor(getResources().getColor(R.color.white));
                tv_price.setTextColor(getResources().getColor(R.color.black));

                tv_discount.setBackgroundColor(getResources().getColor(R.color.white));
                tv_discount.setTextColor(getResources().getColor(R.color.black));

                tv_homeService.setBackgroundColor(getResources().getColor(R.color.white));
                tv_homeService.setTextColor(getResources().getColor(R.color.black));


                break;
            case 3:
                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_locality.setBackgroundColor(getResources().getColor(R.color.white));
                tv_locality.setTextColor(getResources().getColor(R.color.black));

                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_brand.setBackgroundColor(getResources().getColor(R.color.white));
                tv_brand.setTextColor(getResources().getColor(R.color.black));

                tv_price.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_price.setTextColor(getResources().getColor(R.color.white));

                tv_discount.setBackgroundColor(getResources().getColor(R.color.white));
                tv_discount.setTextColor(getResources().getColor(R.color.black));

                tv_homeService.setBackgroundColor(getResources().getColor(R.color.white));
                tv_homeService.setTextColor(getResources().getColor(R.color.black));

                break;


            case 4:
                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_locality.setBackgroundColor(getResources().getColor(R.color.white));
                tv_locality.setTextColor(getResources().getColor(R.color.black));

                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_brand.setBackgroundColor(getResources().getColor(R.color.white));
                tv_brand.setTextColor(getResources().getColor(R.color.black));

                tv_price.setBackgroundColor(getResources().getColor(R.color.white));
                tv_price.setTextColor(getResources().getColor(R.color.black));

                tv_discount.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_discount.setTextColor(getResources().getColor(R.color.white));

                tv_homeService.setBackgroundColor(getResources().getColor(R.color.white));
                tv_homeService.setTextColor(getResources().getColor(R.color.black));

                break;

            case 5:
                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_locality.setBackgroundColor(getResources().getColor(R.color.white));
                tv_locality.setTextColor(getResources().getColor(R.color.black));

                ll_category.setBackgroundColor(getResources().getColor(R.color.white));
                tv_category.setTextColor(getResources().getColor(R.color.black));

                tv_brand.setBackgroundColor(getResources().getColor(R.color.white));
                tv_brand.setTextColor(getResources().getColor(R.color.black));

                tv_price.setBackgroundColor(getResources().getColor(R.color.white));
                tv_price.setTextColor(getResources().getColor(R.color.black));

                tv_discount.setBackgroundColor(getResources().getColor(R.color.white));
                tv_discount.setTextColor(getResources().getColor(R.color.black));
                /**************         ***************/
                tv_homeService.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                tv_homeService.setTextColor(getResources().getColor(R.color.white));

                break;

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        changeClearTextcolor();
    }


    @Override
    public void onBackPressed() {
       ISBACKPRESS=true;
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeClearTextcolor() {

        if (filtercount < 0)
            filtercount = 0;
        if (filtercount == 0)
            btn_clear.setTextColor(getResources().getColor(R.color.text_color_light));
        else
            btn_clear.setTextColor(getResources().getColor(R.color.colorPrimary));

    }

    /*******************************************************                        ************************************************************/

    public void getFilterList(String encrypt_key, String c_user_id, String city_id) {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        final ProgressDialog pDialog = ProgressDialog.show(FilterActivity.this, "", "Please wait ...", true);


        /*
        *
        *
        *
        *   ncrypt_key=dfgfdg&c_user_id=1&current_page=1&limit=10
        *   &loc_ids[0]=1&sub_cat_ids[0]=1&sorts[0]=popularity
        *   &sorts[1]=relevance&services_km=1&home_services=YES


        * */


        HashMap<String, String> params = new HashMap<>();
        params.put("c_user_id", c_user_id);
        params.put("encrypt_key", encrypt_key);

        params.put("city_id", city_id);

        Call<FilterModel> call = apiService.getfilterparamitter(params);
        call.enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, retrofit2.Response<FilterModel> response) {
                //      pDialog.dismiss();

                FilterModel clientModel = response.body();


                if (clientModel != null && clientModel.getData() != null) {

                    if (clientModel.getData().getResultCode().equalsIgnoreCase("1")) {


                        sub_cate_id = "0";
                        filtercount = 0;

                        FilterModel filterModel = new FilterModel();
                        filterModel.setData(clientModel.getData());

                        FilterDataSingleTon.getInstance().setFilterModel(filterModel);
                        changeClearTextcolor();
                        // call fragment
                        replaceFragment(new CategoryFragment(), Constants.FILTER_CATEGORY_FRAGMENT);
                        changeButtonbackcolor(0);


                    } else {

                        Utill.showCenteredToast(context, clientModel.getData().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {

                String str = t.toString().toLowerCase();
                // if(str.contains(ApiClient.BASE_URL)) Utill.snakbarShow(view,"Check internet Connection");

                Utill.showCenteredToast(context, str);
                // Log error here since request failed
                Log.e("", t.toString());
                //      pDialog.dismiss();
            }
        });
    }


}

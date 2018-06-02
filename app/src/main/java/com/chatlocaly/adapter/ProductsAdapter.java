package com.chatlocaly.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.feed.ChannelFeedApiResponse;
import com.applozic.mobicomkit.uiwidgets.async.AlChannelCreateAsyncTask;
import com.applozic.mobicomkit.uiwidgets.async.AlGroupInformationAsyncTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.bumptech.glide.Glide;
import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.BusinessProfileActivity;
import com.chatlocaly.activity.ProductDetailActivity;
import com.chatlocaly.constant.Constant;
import com.chatlocaly.global.Constants;
import com.chatlocaly.model.BusinessInfoModel;
import com.chatlocaly.model.BusinessInfoModelNew;
import com.chatlocaly.model.BusinessProfileInfoModel;
import com.chatlocaly.model.BussinessGroupCreateModel;
import com.chatlocaly.model.GroupInfoModel;
import com.chatlocaly.model.ProductListModel;
import com.chatlocaly.model.ResultModel;
import com.chatlocaly.preferences.Chatlocalyshareprefrence;
import com.chatlocaly.retrofit.ApiClient;
import com.chatlocaly.retrofit.ApiInterface;
import com.chatlocaly.views.recyclerView.AppListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by windows on 12/19/2017.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    Context context;
    List<ProductListModel.productdata.ProductListdata> productList;
    BusinessProfileInfoModel.Datadata.BusinessDetaildata info;
    private Chatlocalyshareprefrence chatlocalyshareprefrence;
    private ProductListModel.productdata.ProductListdata productListdata;
    private int width, height, itemWidth;

    public ProductsAdapter(Context context, BusinessProfileInfoModel.Datadata.BusinessDetaildata info) {
        this.productList = info.getProducts();
        this.context = context;
        this.info = info;
        chatlocalyshareprefrence = new Chatlocalyshareprefrence(context);

      /*  //Calculate the width and height of device
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        this.width = size.x;
        this.height = size.y;

        //80% of screen width
        itemWidth = (width * 80 / 100);
*/

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_recyclerview, parent, false);


        return new ProductsAdapter.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       /* if(productList.get(position).getProductStatus().equalsIgnoreCase("0"))
            holder.iv_opacity.setVisibility(View.VISIBLE);
        else  holder.iv_opacity.setVisibility(View.GONE);*/

        if (productList.get(position).getDiscount().equals("0")) {

            holder.tv_productPerDiscount.setVisibility(View.GONE);
            holder.tv_prodiscount.setVisibility(View.GONE);
            holder.tv_proPrice.setText("Rs " + Utill.getPriceFormatted("" + Utill.getRoundOffFunction(Double.parseDouble(productList.get(position).getPrice()))));

        } else {
            holder.tv_prodiscount.setVisibility(View.VISIBLE);
            holder.tv_productPerDiscount.setVisibility(View.VISIBLE);
            holder.tv_productPerDiscount.setText("" + productList.get(position).getDiscount() + "%off");
            holder.tv_proPrice.setText("Rs " + Utill.getPriceFormatted("" + getdiscountprice(Float.parseFloat(productList.get(position).getPrice()), Integer.parseInt(productList.get(position).getDiscount()))));
            holder.tv_prodiscount.setText(Utill.getPriceFormatted(productList.get(position).getPrice()) + " ");
            holder.tv_prodiscount.setPaintFlags(holder.tv_prodiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }


        holder.tv_productName.setText("" + productList.get(position).getProductName());
        Glide.with(context).load(productList.get(position).getProductImage()).into(holder.iv_prodImage);


    }

    @Override
    public int getItemCount() {

        if (productList != null) {
            if (productList.size() > 0 && productList.size() < 6) {
                return productList.size();
            } else
                return 6;
        } else
            return 0;
    }

    public int getdiscountprice(float price, int persent_discount) {
        int discountprice = 0;
        discountprice = (int) ((price * persent_discount) / 100);
        discountprice = ((int) price) - discountprice;
        return discountprice;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_prodiscount, tv_proPrice, tv_productName, tv_productPerDiscount;
        ImageView iv_prodImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_productName);
            tv_proPrice = (TextView) itemView.findViewById(R.id.tv_proPrice);
            tv_prodiscount = (TextView) itemView.findViewById(R.id.tv_prodiscount);
            iv_prodImage = (ImageView) itemView.findViewById(R.id.iv_prodImage);

            tv_productPerDiscount = (TextView) itemView.findViewById(R.id.tv_discount_per);


            iv_prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    productListdata = productList.get(getAdapterPosition());
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Constants.PRODUCT_DETAIL, productListdata);
                    context.startActivity(intent);


                }
            });


        /*    //Setting Item width to 80% of device width
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(itemWidth,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(layoutParams);
*/
        }
    }


}

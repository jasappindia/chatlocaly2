package com.chatlocaly.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.Utill;
import com.chatlocaly.activity.StoreListActivity;
import com.chatlocaly.model.CategoryListModel;

import org.apache.http.client.UserTokenHandler;

import java.util.List;

/**
 * Created by anjani on 18/12/17.
 */

public class SubcategaryListAdapter extends RecyclerView.Adapter<SubcategaryListAdapter.ViewHolder> {
    public static String CATEGORY_ID = " cat_id";
    int image[] = {R.mipmap.a1, R.mipmap.a2, R.mipmap.a3, R.mipmap.a4, R.mipmap.a5};
    private Context context;
    private List<CategoryListModel.DataData.CategoryListData.SubCategoriesData> list;

    public SubcategaryListAdapter(Context context, List<CategoryListModel.DataData.CategoryListData.SubCategoriesData> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rowcategory, parent, false);

    int VIEWS_COUNT_TO_DISPLAY=48;

        view.getLayoutParams().width = getScreenWidth() / VIEWS_COUNT_TO_DISPLAY;
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private int getScreenWidth() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.x*10;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final CategoryListModel.DataData.CategoryListData.SubCategoriesData subCategoriesData = list.get(position);


        holder.tv_title.setText("" + subCategoriesData.getSubCatName());
        // set onclick
        Utill.imageshow(context,holder.iv_category,""+subCategoriesData.getIcon());

      ///  holder.iv_category.setImageResource(image[position % 4]);


        holder.iv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utill.isConnectingToInternet(context)) {

                    Intent intent = new Intent(context, StoreListActivity.class);
                    intent.putExtra(CATEGORY_ID, subCategoriesData.getSubCatId());
                    context.startActivity(intent);
                }
                else
                {
                    Utill.showCenteredToast(context,context.getString(R.string.no_internet_connected));
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void notify(List<CategoryListModel.DataData.CategoryListData.SubCategoriesData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView iv_category;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_sub_name);
            iv_category = (ImageView) itemView.findViewById(R.id.iv_sub_cat);
        }

    }


}


package com.chatlocaly.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatlocaly.R;
import com.chatlocaly.activity.FilterActivity;
import com.chatlocaly.model.CategoryListModel;
import com.chatlocaly.model.FilterCategoryListModel;
import com.chatlocaly.model.FilterModel;
import com.chatlocaly.singleton.FilterDataSingleTon;

import java.util.List;

/**
 * Created by Ashok on 24/3/17.
 */

public class ExpandListCategoryListAdapter extends BaseExpandableListAdapter implements View.OnClickListener
{


    List<FilterModel.FilterData.CategoryListData> arrayList;
    String parentCategorytitle;
    private Context context;

    public ExpandListCategoryListAdapter(Context context, List<FilterModel.FilterData.CategoryListData> arrayList)
    {

        this.context = context;
        if (arrayList != null)
            this.arrayList = arrayList;
        this.parentCategorytitle = parentCategorytitle;
    }

    @Override
    public FilterModel.FilterData.CategoryListData getGroup(int groupPosition) {
        return arrayList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return arrayList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FilterModel.FilterData.CategoryListData categoryListModel = arrayList.get(groupPosition);

        ViewHolderFirst viewHolderFirst;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_expand_title, null);
            viewHolderFirst = new ViewHolderFirst(convertView);

            convertView.setTag(viewHolderFirst);

        } else {
            viewHolderFirst = (ViewHolderFirst) convertView.getTag();
        }



      viewHolderFirst.tv_name.setText(categoryListModel.getCatName());
        if(isExpanded)
            viewHolderFirst.iv_indicater.setImageResource(R.drawable.ic_arrow_up);
        else
            viewHolderFirst.iv_indicater.setImageResource(R.drawable.ic_arrow_down);


        return convertView;
    }

    @Override
    public FilterModel.FilterData.CategoryListData.SubCategoriesData getChild(int groupPosition, int childPosition) {
        return arrayList.get(groupPosition).getSubCategories().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final FilterModel.FilterData.CategoryListData.SubCategoriesData subcategory = getChild(groupPosition, childPosition);



        ViewHolderSecond viewHolderThrid = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_child_subcategory, null);
            viewHolderThrid = new ViewHolderSecond(convertView);
            convertView.setTag(viewHolderThrid);

        } else {
            viewHolderThrid = (ViewHolderSecond) convertView.getTag();

        }



        ///
        viewHolderThrid.cb_subtile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Log.e("check","check");
                if(((CheckBox)v).isChecked())
                {
                    FilterActivity.filtercount++;

                    Log.e("check ","check");

                  //  FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList().get(groupPosition).getSubCategories().get(childPosition).setIsselecteble(true);
                      arrayList.get(groupPosition).getSubCategories().get(childPosition).setIsselecteble(true);
                    ((FilterActivity)context).changeClearTextcolor();
                }
                else
                {
                    Log.e("un check ","check");
                    if(FilterActivity.filtercount>0) {
                        FilterActivity.filtercount--;
                        //   FilterDataSingleTon.getInstance().getFilterModel().getData().getCategoryList().get(groupPosition).getSubCategories().get(childPosition).setIsselecteble(false);

                        arrayList.get(groupPosition).getSubCategories().get(childPosition).setIsselecteble(false);
                        ((FilterActivity) context).changeClearTextcolor();
                    }
                }


            }
        });
        // select category check

/*

        if(arrayList.get(groupPosition).getSubCategories().get(childPosition).getSubCatId().equalsIgnoreCase(((FilterActivity)context).getSub_cate_id()))
        {

            arrayList.get(groupPosition).getSubCategories().get(childPosition).setIsselecteble(true);
            ((FilterActivity)context).changeClearTextcolor();


        }
*/



            if(arrayList.get(groupPosition).getSubCategories().get(childPosition).isIsselecteble())
        {

            // set data for text view
            viewHolderThrid.cb_subtile.setText(subcategory.getSubCatName());
            viewHolderThrid.cb_subtile.setChecked(true);
            ((FilterActivity) context).changeClearTextcolor();


        }
        else
        {


            //
            // set data for text view
            viewHolderThrid.cb_subtile.setText(subcategory.getSubCatName());
            viewHolderThrid.cb_subtile.setChecked(false);
            ((FilterActivity) context).changeClearTextcolor();


        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayList.get(groupPosition).getSubCategories().size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public void onClick(View view) {


    }

    /**
     * static cache item view
     */
    public class ViewHolderFirst {

        TextView tv_name;
        ImageView iv_indicater;


        /**
         * holder constructor
         */
        public ViewHolderFirst(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_Title);
            iv_indicater = (ImageView) view.findViewById(R.id.iv_arrow_right);
        }

    }

    /**
     * static cache item view
     */
    public class ViewHolderSecond {


        CheckBox cb_subtile;
        /**
         * holder constructor
         */
        public ViewHolderSecond(View view) {
            cb_subtile = (CheckBox) view.findViewById(R.id.cb_subCategoryName);
        }

    }
}

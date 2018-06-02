package com.chatlocaly.other;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chatlocaly.R;
import com.chatlocaly.activity.MainActivity;
import com.chatlocaly.activity.StoreListActivity;
import com.chatlocaly.adapter.StoreListAdapter;
import com.chatlocaly.model.StoreListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashok on 22/03/16.
 */
public class Toolbar_ActionMode_Callback implements ActionMode.Callback {

    private Context context;
    private StoreListAdapter recyclerView_adapter;
    private List<StoreListModel.DataData.BusinessListData> message_models;
    private boolean isListViewFragment;


    public Toolbar_ActionMode_Callback(Context context, StoreListAdapter recyclerView_adapter, List<StoreListModel.DataData.BusinessListData> message_model) {
        this.context = context;
        this.recyclerView_adapter = recyclerView_adapter;
        this.message_models = message_models;
        this.isListViewFragment = isListViewFragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

       /* //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_delete), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_copy), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_forward), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_copy).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menu.findItem(R.id.action_forward).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }*/

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:


                //If current fragment is recycler view fragment
                //   StoreListActivity recyclerFragment = ;//Get recycler view fragment
                if (context != null)
                    //If recycler fragment not null
                    ((StoreListActivity) context).deleteRows();//delete selected rows

                break;
            case R.id.action_copy:

                //Get selected ids on basis of current fragment action mode
                SparseBooleanArray selected;
            /*    if (isListViewFragment)
                    selected = listView_adapter
                            .getSelectedIds();
          */    //  if
                selected = recyclerView_adapter
                        .getSelectedIds();

                int selectedMessageSize = selected.size();

                //Loop to all selected items
                for (int i = (selectedMessageSize - 1); i >= 0; i--) {
                    if (selected.valueAt(i)) {
                           //Print the data to show if its working properly or not
                        Log.e("Selected Items", "Title - " +   "\n" + "Sub Title - " );

                    }
                }
                Toast.makeText(context, "You selected Copy menu.", Toast.LENGTH_SHORT).show();//Show toast
                mode.finish();//Finish action mode
                break;
            case R.id.action_forward:
                Toast.makeText(context, "You selected Forward menu.", Toast.LENGTH_SHORT).show();//Show toast
                mode.finish();//Finish action mode
                break;


        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode

        recyclerView_adapter.removeSelection();  // remove selection
        if (context != null)
            ((StoreListActivity) context).setNullToActionMode();//Set action mode null

    }
}

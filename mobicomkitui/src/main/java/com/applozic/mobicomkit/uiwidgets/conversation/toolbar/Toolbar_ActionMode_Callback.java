package com.applozic.mobicomkit.uiwidgets.conversation.toolbar;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicomkit.uiwidgets.conversation.adapter.DetailedConversationAdapter;
import com.applozic.mobicomkit.uiwidgets.conversation.fragment.ConversationFragment;
import com.applozic.mobicomkit.uiwidgets.conversation.fragment.MobiComConversationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashok on 22/3/18.
 */

public class Toolbar_ActionMode_Callback   implements ActionMode.Callback {

    private Context context;
    private DetailedConversationAdapter listView_adapter;
    private List<Message> message_models;
    private boolean isListViewFragment;


    public Toolbar_ActionMode_Callback(Context context, DetailedConversationAdapter  listView_adapter, List<Message> message_models, boolean isListViewFragment) {
        this.context = context;
        this.listView_adapter = listView_adapter;
        this.message_models = message_models;
        this.isListViewFragment = isListViewFragment;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_info, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_info), MenuItemCompat.SHOW_AS_ACTION_NEVER);
               } else {
            menu.findItem(R.id.action_info).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                }

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
         if(item.getItemId()== R.id.action_info) {
             //Check if current action mode is from ListView Fragment or RecyclerView Fragment


         }

        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode
        if (isListViewFragment) {
            MobiComConversationFragment listFragment = new ConversationFragment();//Get list fragment
            if (listFragment != null)
                ((MobiComConversationFragment) listFragment).setNullToActionMode();//Set action mode null
        }
    }
}
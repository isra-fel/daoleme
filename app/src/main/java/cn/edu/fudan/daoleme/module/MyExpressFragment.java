package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.ExpressListAdapter;
import cn.edu.fudan.daoleme.database.pojo.Express;
import cn.edu.fudan.daoleme.database.pojo.ExpressHistory;

/**
 * Created by rinnko on 2015/11/15.
 */
public class MyExpressFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener,
        View.OnClickListener,
        View.OnKeyListener {
    private static final String TAG = "MyExpressFragment";

    private ListView mListView;
    private View mOperationBar;
    private boolean mMultiChoiceMode;
    private ExpressListAdapter mAdapter;
    private Menu mSelectMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myexpress, container, false);

        mListView = (ListView)view.findViewById(R.id.express_list);
        mOperationBar = view.findViewById(R.id.operation_bar);
        mAdapter = new ExpressListAdapter(getActivity(), getData(), mListView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        Button btnMultipleMark = (Button)view.findViewById(R.id.multiple_mark);
        Button btnMultiplePin = (Button)view.findViewById(R.id.multiple_pin);
        Button btnMultipleDelete = (Button)view.findViewById(R.id.multiple_delete);
        btnMultipleMark.setOnClickListener(this);
        btnMultiplePin.setOnClickListener(this);
        btnMultipleDelete.setOnClickListener(this);

        setHasOptionsMenu(true);

        return view;

    }

    private List<Express> getData() {
        List<Express> expressList = new ArrayList<>();
        for ( int i = 0; i < 100; i++) {
            Express express = new Express();
            express.name = "name";
            express.tag = "tag";
            List<ExpressHistory> expressHistories = new ArrayList<>();
            ExpressHistory expressHistory = new ExpressHistory();
            expressHistory.date = new Date();
            expressHistories.add(expressHistory);
            express.history = expressHistories;
            expressList.add(express);
        }
        return expressList;
    }

    private void onExitMultiSelectMode() {
        mMultiChoiceMode = false;
        mOperationBar.setVisibility(View.GONE);
        mSelectMenu.setGroupVisible(R.id.group_default, false);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick");
        if (!mMultiChoiceMode) {
            Intent intent = new Intent(getActivity(), ExpressDetailActivity.class);
            // TODO pass express_id
            intent.putExtra("express_id", "express_id");
            startActivity(intent);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListView.getChoiceMode() != AbsListView.CHOICE_MODE_MULTIPLE) {
            mMultiChoiceMode = true;
            mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            mOperationBar.setVisibility(View.VISIBLE);
            mSelectMenu.setGroupVisible(R.id.group_default, true);
        }
        return true;
    }

    private void onMultipleMark() {
        Log.d(TAG, "onMultipleMark");
        // TODO multiple mark
        onExitMultiSelectMode();
    }

    private void onMultiplePin() {
        Log.d(TAG, "onMultiplePin");
        // TODO multiple pin
        onExitMultiSelectMode();
    }

    private void onMultipleDelete() {
        Log.d(TAG, "onMultipleDelete");
        // TODO multiple delete
        onExitMultiSelectMode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.multiple_mark:
                onMultipleMark();
                break;
            case R.id.multiple_delete:
                onMultipleDelete();
                break;
            case R.id.multiple_pin:
                onMultiplePin();
                break;
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mMultiChoiceMode) {
            onExitMultiSelectMode();
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_multiple_select, menu);
        menu.setGroupVisible(R.id.group_default, false);
        mSelectMenu = menu;
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                // TODO select-all or unselect-all
                break;
            case R.id.action_cancel:
                onExitMultiSelectMode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

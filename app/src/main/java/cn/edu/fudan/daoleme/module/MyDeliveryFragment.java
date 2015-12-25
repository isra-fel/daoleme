package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.DeliveryListAdapter;
import cn.edu.fudan.daoleme.data.DeliveryProvider;
import cn.edu.fudan.daoleme.data.pojo.Delivery;

/**
 * Created by rinnko on 2015/11/15.
 */
public class MyDeliveryFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener,
        View.OnClickListener,
        View.OnKeyListener {
    private static final String TAG = "MyDeliveryFragment";

    private ListView mListView;
    private View mOperationBar;
    private boolean mMultiChoiceMode;
    private DeliveryListAdapter mAdapter;
    private Menu mSelectMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_delivery, container, false);

        mListView = (ListView)view.findViewById(R.id.delivery_list);
        mOperationBar = view.findViewById(R.id.operation_bar);
        mAdapter = new DeliveryListAdapter(getActivity(), getData(), mListView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        View btnMultipleMark = view.findViewById(R.id.multiple_receive);
        View btnMultiplePin = view.findViewById(R.id.multiple_pin);
        View btnMultipleDelete = view.findViewById(R.id.multiple_delete);
        btnMultipleMark.setOnClickListener(this);
        btnMultiplePin.setOnClickListener(this);
        btnMultipleDelete.setOnClickListener(this);

        setHasOptionsMenu(true);

        return view;

    }

    private List<Delivery> getData() {
        return DeliveryProvider.queryAll(getActivity().getContentResolver());
        //TODO: getData form DB
//        List<Delivery> deliveryList = new ArrayList<>();
//        for ( int i = 0; i < 100; i++) {
//            Delivery delivery = new Delivery();
//            delivery.setExpressCompanyName("yuantong");
//            delivery.setTag("tag");
//            delivery.setIsReceived(true);
//            delivery.setIsPinned(true);
//            ArrayList<String> state = new ArrayList<>();
//            state.add("history1");
//            state.add("history2");
//            delivery.setState(state);
//            deliveryList.add(delivery);
//        }
//        return deliveryList;
    }

    private void onExitMultiSelectMode() {
        mMultiChoiceMode = false;
        mOperationBar.setVisibility(View.GONE);
        mSelectMenu.setGroupVisible(R.id.group_default, false);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!mMultiChoiceMode) {
            Intent intent = new Intent(getActivity(), DeliveryDetailActivity.class);
            Bundle bundle = new Bundle();
            // TODO pass express_id
            bundle.putString("deliveryId", mAdapter.getItem(position).getId());
            intent.putExtras(bundle);
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

    private void onMultipleReceive() {
        Log.d(TAG, "onMultipleReceive");
        // TODO multiple receive
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

    private void onSelectAll() {
        int itemCount = mAdapter.getCount();
        for ( int i = 0; i < itemCount; i++) {
            mListView.setItemChecked(i, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.multiple_receive:
                onMultipleReceive();
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
                onSelectAll();
                break;
            case R.id.action_cancel:
                onExitMultiSelectMode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

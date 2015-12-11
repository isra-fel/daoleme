package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cn.edu.fudan.daoleme.net.DeliveryClient;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cn.edu.fudan.daoleme.util.ToastUtil;
import cz.msebera.android.httpclient.Header;

/**
 * Created by rinnko on 2015/11/14.
 */
public class DeliveryDetailFragment extends Fragment {
    private static final String TAG = "DeliveryDetailFragment";

    private Delivery mDelivery;
    private Menu mMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDelivery = getDeliveryById(getArguments().getString("deliveryId"));

        View view = inflater.inflate(R.layout.fragment_delivery_detail, container, false);

        TextView expressCompany = (TextView)view.findViewById(R.id.express_company);
        expressCompany.setText(mDelivery.expressCompanyName);
        TextView deliveryId = (TextView)view.findViewById(R.id.delivery_id);
        deliveryId.setText(mDelivery.id);

        //
        // from, to, receiver
        TextView mFrom = (TextView)view.findViewById(R.id.from);
        TextView mTo = (TextView)view.findViewById(R.id.to);
        TextView mReceiver = (TextView)view.findViewById(R.id.receiver);
        //

        ViewGroup mDeliveryState = (ViewGroup)view.findViewById(R.id.delivery_state);
        for (String item : mDelivery.state) {
            TextView textView = new TextView(getActivity());
            mDeliveryState.addView(textView);
        }

        setHasOptionsMenu(true);

        return view;

    }

    private Delivery getDeliveryById(String deliveryId) {
        // TODO get delivery by id
        Delivery delivery = new Delivery();
        delivery.id = deliveryId;
        delivery.tag = "tag";
        delivery.expressCompanyName = "company";
        delivery.isPinned = true;
        delivery.isReceived = false;
        delivery.state = new ArrayList<>();
        delivery.state.add("2015-10-10 到达上海");
        return delivery;
    }

    private void onDelete() {

        LoadingUtil.showLoading(getActivity(), R.string.message_loading_delete);
        DeliveryClient.deleteDelivery(mDelivery.id, getActivity(), new JsonHttpResponseHandler() {

            private String mDeleteId = mDelivery.id;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ToastUtil.toast(R.string.message_delete_success);
                // TODO write database
                if (isAdded()) {
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtil.toast(R.string.message_delete_fail);
                if (isAdded()) {
                    LoadingUtil.hideLoading(getActivity());
                }
            }
        });

    }

    private void onReceive() {
        LoadingUtil.showLoading(getActivity(), R.string.message_loading_edit);
        DeliveryClient.editDelivery(mDelivery.isPinned, !mDelivery.isReceived, getActivity(), new JsonHttpResponseHandler() {

            private boolean mReceivedStatus = mDelivery.isReceived;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ToastUtil.toast(R.string.message_edit_success);
                // TODO write database
                if (isAdded()) {
                    mMenu.getItem(1).setIcon(mReceivedStatus ? R.drawable.ic_action_accept_light: R.drawable.ic_action_accept_active);
                    LoadingUtil.hideLoading(getActivity());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtil.toast(R.string.message_edit_fail);
                if (isAdded()) {
                    LoadingUtil.hideLoading(getActivity());
                }
            }
        });
    }

    private void onPin() {
        LoadingUtil.showLoading(getActivity(), R.string.message_loading_edit);
        DeliveryClient.editDelivery(!mDelivery.isPinned, mDelivery.isReceived, getActivity(), new JsonHttpResponseHandler() {

            private boolean mPinnedStatus = mDelivery.isPinned;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ToastUtil.toast(R.string.message_edit_success);
                // TODO write database
                if (isAdded()) {
                    mMenu.getItem(2).setIcon(mPinnedStatus ? R.drawable.ic_action_make_available_offline_light: R.drawable.ic_action_make_available_offline_active);
                    LoadingUtil.hideLoading(getActivity());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtil.toast(R.string.message_edit_fail);
                if (isAdded()) {
                    LoadingUtil.hideLoading(getActivity());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                onDelete();
                return true;
            case R.id.action_receive:
                onReceive();
                return true;
            case R.id.action_pin:
                onPin();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        inflater.inflate(R.menu.menu_delivery_detail, menu);
        if (mDelivery.isPinned) {
            menu.getItem(2).setIcon(R.drawable.ic_action_make_available_offline_active);
        }
        if (mDelivery.isReceived) {
            menu.getItem(1).setIcon(R.drawable.ic_action_accept_active);
        }
    }

}

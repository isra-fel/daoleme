package cn.edu.fudan.daoleme.module;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cn.edu.fudan.daoleme.module.dialog.QueryDeliveryResultFragment;
import cn.edu.fudan.daoleme.net.APIClient;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cz.msebera.android.httpclient.Header;

/**
 * Created by rinnko on 2015/11/10.
 */
public class ReceiveFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ReceiveFragment";

    private Spinner mExpressCompany;
    private EditText mDeliveryId;
    private QueryDeliveryResultFragment mQueryDeliveryResultFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        String[] expressCompanyList = getResources().getStringArray(R.array.express_company_list);
        mExpressCompany = (Spinner)view.findViewById(R.id.express_company);
        // see http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, expressCompanyList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mExpressCompany.setAdapter(arrayAdapter);
        mDeliveryId = (EditText)view.findViewById(R.id.delivery_id);
        View btnQuery = view.findViewById(R.id.query);
        btnQuery.setOnClickListener(this);

        return view;

    }

    private void onQuery() {

        // TODO validate input
        String company = (String)mExpressCompany.getSelectedItem();
        String deliveryId = mDeliveryId.getText().toString();

        LoadingUtil.showLoading(getActivity(), R.string.message_loading_query);
        APIClient.query(company, deliveryId, getActivity().getApplicationContext(), new JsonHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                onQuerySuccess(response);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                onQueryFail(throwable);
            }
        });
        // onQuerySuccess(null);

    }

    private void onQuerySuccess(JSONObject data) {

        if (!isAdded()) {
            return;
        }

        // TODO parse data to delivery
        Delivery delivery = new Delivery();
        delivery.setExpressCompanyName("company");
        delivery.setId("123113123121231231");
        delivery.setTag("tag");
        delivery.setState(new ArrayList<String>());
        delivery.addState("asdfsafasdfasdfasdfsadfs");

        if (mQueryDeliveryResultFragment == null) {
            mQueryDeliveryResultFragment = new QueryDeliveryResultFragment();
        }
        mQueryDeliveryResultFragment.setDelivery(delivery);
        mQueryDeliveryResultFragment.show(getFragmentManager(), TAG);

    }

    private void onQueryFail(Throwable why) {

        if (!isAdded()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.nothing_found)
                .setTitle(R.string.query)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                onQuery();
                break;
        }
    }

}

package cn.edu.fudan.daoleme.module.dialog;

import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.DeliveryProvider;
import cn.edu.fudan.daoleme.data.constant.ExpressCompany;
import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cn.edu.fudan.daoleme.net.DeliveryClient;
import cn.edu.fudan.daoleme.util.SessionUtil;
import cz.msebera.android.httpclient.Header;

/**
 * Created by rinnko on 2015/12/9.
 */
public class QueryDeliveryResultFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "QueryDeliveryResultFragment";

    private TextView mExpressCompany, mDeliveryId, mTag;
    private LinearLayout mDeliveryState;
    private Delivery mDelivery;

    public void setDelivery(@NonNull Delivery delivery) {
        mDelivery = delivery;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.BaseDialog);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_query_delivery_result, container, false);
        mExpressCompany = (TextView)view.findViewById(R.id.express_company);
        mDeliveryId = (TextView)view.findViewById(R.id.delivery_id);
        mTag = (TextView)view.findViewById(R.id.tag);
        mDeliveryState = (LinearLayout)view.findViewById(R.id.delivery_state);

        View btnMark = view.findViewById(R.id.mark);
        View btnPin = view.findViewById(R.id.pin);
        View btnCancel = view.findViewById(R.id.cancel);

        mExpressCompany.setText(ExpressCompany.valueOf(mDelivery.getExpressCompanyName()).getString(getActivity()));
        mDeliveryId.setText(mDelivery.getId());
        mTag.setText((mDelivery.getTag()).isEmpty() ? getString(R.string.unset) : mDelivery.getTag());

        btnMark.setOnClickListener(this);
        btnPin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        for (String state : mDelivery.getState()) {
            TextView textState = new TextView(getActivity());
            textState.setText(state);
            mDeliveryState.addView(textState);
        }

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mark:
                onPin();
                break;
            case R.id.pin:
                onMark();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private void onPin() {
        // TODO pin
    }


    private void onMark() {
        saveDeliveryToLocalDB();
        saveDeliveryToServer();
        dismiss();
    }

    private void saveDeliveryToServer() {
        DeliveryClient.favor(SessionUtil.getSession(getActivity()).getUser().getId(), mDelivery, getActivity(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void saveDeliveryToLocalDB() {
        ContentValues contentValues = mDelivery.toContentValues();
        getActivity().getContentResolver().insert(DeliveryProvider.CONTENT_URI, contentValues);
    }

}

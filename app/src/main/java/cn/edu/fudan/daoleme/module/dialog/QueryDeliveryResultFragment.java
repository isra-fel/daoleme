package cn.edu.fudan.daoleme.module.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.pojo.Delivery;

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

        mExpressCompany.setText(mDelivery.expressCompanyName);
        mDeliveryId.setText(mDelivery.id);
        mTag.setText(mDelivery.tag);

        btnMark.setOnClickListener(this);
        btnPin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        for (String state : mDelivery.state) {
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
        // TODO mark
    }

}

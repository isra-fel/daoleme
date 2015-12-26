package cn.edu.fudan.daoleme.module.dialog;


import android.os.Bundle;
import android.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.DeliveryProvider;
import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cn.edu.fudan.daoleme.util.ToastUtil;

public class AddTagFragment extends DialogFragment implements View.OnClickListener {

    private Delivery delivery;

    public AddTagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        delivery = DeliveryProvider.queryById(getActivity().getContentResolver(), getArguments().getString("deliveryId"));
        View view = inflater.inflate(R.layout.fragment_add_tag, container, false);
        TextView submit = (TextView) view.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        return view;
    }

    public void onSubmitClick(View view) {
        TextView textView = (TextView) getView().findViewById(R.id.tag);
        DeliveryProvider.setTag(getActivity().getContentResolver(), delivery.getId(), textView.getText().toString());
        ToastUtil.toast(R.string.set_done);
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                onSubmitClick(v);
                break;
        }
    }
}

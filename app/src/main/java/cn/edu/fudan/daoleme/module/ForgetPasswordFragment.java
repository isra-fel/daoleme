package cn.edu.fudan.daoleme.module;

import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/14.
 */
public class ForgetPasswordFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "ForgetPasswordFragment";

    private EditText mEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_forget_password, container, false);
        mEmail = (EditText)view.findViewById(R.id.email);
        Button btnSend = (Button)view.findViewById(R.id.send);
        btnSend.setOnClickListener(this);
        return view;
    }

    private void onSendMail() {
        // TODO send mail
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                onSendMail();
                break;
        }
    }
}

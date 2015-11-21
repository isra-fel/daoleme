package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/14.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "RegisterFragment";

    private EditText mAccount, mPassword, mPasswordConfirm, mEMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAccount = (EditText)view.findViewById(R.id.user_account);
        mPassword = (EditText)view.findViewById(R.id.user_password);
        mPasswordConfirm = (EditText)view.findViewById(R.id.user_password_confirm);
        mEMail = (EditText)view.findViewById(R.id.user_email);
        Button btnRegister = (Button)view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        return view;
    }

    private void onRegister() {
        // TODO register
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                onRegister();
                break;
        }
    }

}

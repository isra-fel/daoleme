package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/14.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LoginFragment";

    private EditText mAccount, mPassword;
    private ForgetPasswordFragment mForgetPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mAccount = (EditText)view.findViewById(R.id.user_account);
        mPassword = (EditText)view.findViewById(R.id.user_password);
        Button btnLogin = (Button)view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        TextView tvRegister = (TextView)view.findViewById(R.id.register);
        tvRegister.setOnClickListener(this);
        TextView tvForgetPassword = (TextView)view.findViewById(R.id.forget_password);
        tvForgetPassword.setOnClickListener(this);
        return view;
    }

    private void onLogin() {
        // TODO login
    }

    private void onForgetPassword() {
        if (mForgetPassword == null) {
            mForgetPassword = new ForgetPasswordFragment();
        }
        mForgetPassword.show(getFragmentManager(), "忘记密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                onLogin();
                break;
            case R.id.register:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.forget_password:
                onForgetPassword();
                break;
        }
    }
}

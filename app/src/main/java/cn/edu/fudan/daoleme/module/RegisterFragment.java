package cn.edu.fudan.daoleme.module;

import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.net.UserClient;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cn.edu.fudan.daoleme.util.ToastUtil;
import cz.msebera.android.httpclient.Header;

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
        View btnRegister = view.findViewById(R.id.register);
        btnRegister.setOnClickListener(this);
        return view;
    }

    private void onRegister() {
        // TODO validate user input
        String username = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        String passwordRepeat = mPasswordConfirm.getText().toString();
        String email = mEMail.getText().toString();
        if (!password.equals(passwordRepeat)) {
            ToastUtil.toast(R.string.message_password_not_match);
            return;
        }
        LoadingUtil.showLoading(getActivity(), R.string.message_loading_register);
        UserClient.signup(username, password, email, getActivity(), new JsonHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                if (!isAdded()) {
                    return;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                LoadingUtil.hideLoading(getActivity());
                if (!isAdded()) {
                    return;
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                onRegister();
                break;
        }
    }

}

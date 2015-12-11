package cn.edu.fudan.daoleme.module;

import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.module.dialog.ForgetPasswordFragment;
import cn.edu.fudan.daoleme.module.dialog.LoadingFragment;
import cn.edu.fudan.daoleme.net.UserClient;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cn.edu.fudan.daoleme.util.ToastUtil;
import cz.msebera.android.httpclient.Header;

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
        View btnLogin = view.findViewById(R.id.login);
        btnLogin.setOnClickListener(this);
        TextView tvRegister = (TextView)view.findViewById(R.id.register);
        tvRegister.setOnClickListener(this);
        TextView tvForgetPassword = (TextView)view.findViewById(R.id.forget_password);
        tvForgetPassword.setOnClickListener(this);
        return view;
    }

    private void onLogin() {

        // TODO validate user input
        String username = mAccount.getText().toString();
        String password = mPassword.getText().toString();

        LoadingUtil.showLoading(getActivity(), R.string.message_loading_login);

        UserClient.signin(username, password, getActivity(), new JsonHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                if (!isAdded()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                LoadingUtil.hideLoading(getActivity());
                if (!isAdded()) {
                    return;
                }
                ToastUtil.toast(R.string.message_login_fail);
            }

        });
    }

    private void onForgetPassword() {
        if (mForgetPassword == null) {
            mForgetPassword = new ForgetPasswordFragment();
        }
        mForgetPassword.show(getFragmentManager(), getString(R.string.forget_password));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
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

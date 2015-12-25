package cn.edu.fudan.daoleme.module.dialog;

import android.app.Application;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.net.UserClient;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cn.edu.fudan.daoleme.util.SessionUtil;
import cn.edu.fudan.daoleme.util.ToastUtil;
import cz.msebera.android.httpclient.Header;

/**
 * Created by rinnko on 2015/11/14.
 */
public class ResetPasswordFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "ResetPasswordFragment";
    private long userId;

    private EditText mOldPassword, mNewPassword, mConfirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reset_password, container, false);
        userId = SessionUtil.getSession(getActivity()).getUser().getId();
        mOldPassword = (EditText)view.findViewById(R.id.old_password);
        mNewPassword = (EditText)view.findViewById(R.id.new_password);
        mConfirmPassword = (EditText)view.findViewById(R.id.confirm_password);
        View btnReset = view.findViewById(R.id.reset);
        btnReset.setOnClickListener(this);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.BaseDialog);
    }

    private void onResetPassword() {
        // TODO validate user input
        String oldPassword = mOldPassword.getText().toString();
        String newPassword = mNewPassword.getText().toString();
        String repeatPassword = mConfirmPassword.getText().toString();
        if (!newPassword.equals(repeatPassword)) {
            ToastUtil.toast(R.string.message_password_not_match);
            return;
        }
        LoadingUtil.showLoading(getActivity(), R.string.message_loading_reset_password);
        UserClient.changePassword(userId, oldPassword, newPassword, getActivity(), new JsonHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                if (isAdded()) {
                    ToastUtil.toast(R.string.message_reset_password_success);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                LoadingUtil.hideLoading(getActivity());
                if (isAdded()) {
                    ToastUtil.toast(R.string.message_reset_password_fail);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                onResetPassword();
                break;
        }
    }
}

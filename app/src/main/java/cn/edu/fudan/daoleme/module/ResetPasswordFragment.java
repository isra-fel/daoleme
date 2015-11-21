package cn.edu.fudan.daoleme.module;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/14.
 */
public class ResetPasswordFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "ResetPasswordFragment";

    private EditText mOldPassword, mNewPassword, mConfirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reset_password, container, false);
        mOldPassword = (EditText)view.findViewById(R.id.old_password);
        mNewPassword = (EditText)view.findViewById(R.id.new_password);
        mConfirmPassword = (EditText)view.findViewById(R.id.confirm_password);
        Button btnReset = (Button)view.findViewById(R.id.reset);
        btnReset.setOnClickListener(this);
        return view;
    }

    private void onResetPassword() {
        // TODO reset password
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

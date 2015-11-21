package cn.edu.fudan.daoleme.module;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/10.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MeFragment";

    private ResetPasswordFragment mResetPasswordFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        // set details
        ImageView userAvatar = (ImageView)view.findViewById(R.id.user_avatar);
        // TODO set avatar
        TextView userId = (TextView)view.findViewById(R.id.user_id);
        // TODO set id
        userId.setText("123");
        TextView userSignature = (TextView)view.findViewById(R.id.user_signature);
        // TODO set signature
        userSignature.setText("signature");
        // TODO set mail-notification
        ImageView mailNotification = (ImageView)view.findViewById(R.id.express_notify);
        if (true) {
            mailNotification.setVisibility(View.VISIBLE);
        }

        View vProfile = view.findViewById(R.id.profile);
        vProfile.setOnClickListener(this);
        View vMyMail = view.findViewById(R.id.my_express);
        vMyMail.setOnClickListener(this);
        View vResetPassword = view.findViewById(R.id.reset_password);
        vResetPassword.setOnClickListener(this);
        View vSetting = view.findViewById(R.id.setting);
        vSetting.setOnClickListener(this);
        Button btnLogout = (Button)view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(this);

        return view;
    }

    private void onResetPassword() {
        if (mResetPasswordFragment == null) {
            mResetPasswordFragment = new ResetPasswordFragment();
        }
        mResetPasswordFragment.show(getFragmentManager(), "修改密码");
    }

    private void onLogout() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                // startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.my_express:
                startActivity(new Intent(getActivity(), MyExpressActivity.class));
                break;
            case R.id.reset_password:
                onResetPassword();
                break;
            case R.id.setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.btn_logout:
                onLogout();
                break;
        }
    }
}

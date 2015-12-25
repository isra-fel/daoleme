package cn.edu.fudan.daoleme.module;

import android.app.Activity;
import android.content.Intent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.dao.Session;
import cn.edu.fudan.daoleme.data.pojo.User;
import cn.edu.fudan.daoleme.module.dialog.ResetPasswordFragment;
import cn.edu.fudan.daoleme.net.UserClient;
import cn.edu.fudan.daoleme.util.SessionUtil;
import cz.msebera.android.httpclient.Header;

/**
 * Created by rinnko on 2015/11/10.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MeFragment";

    private ImageView mUserAvatar, mExpressNotifier;
    private TextView mUserId, mUserName, mLogoutText;
    private View mVMyExpress, mVResetPassword;
    private ResetPasswordFragment mResetPasswordFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        mUserAvatar = (ImageView)view.findViewById(R.id.user_avatar);
        mUserId = (TextView)view.findViewById(R.id.user_id);
        mUserName = (TextView)view.findViewById(R.id.user_name);
        mExpressNotifier = (ImageView)view.findViewById(R.id.express_notify);

        View vProfile = view.findViewById(R.id.profile);
        vProfile.setOnClickListener(this);
        mVMyExpress = view.findViewById(R.id.my_express);
        mVMyExpress.setOnClickListener(this);
        mVResetPassword = view.findViewById(R.id.reset_password);
        mVResetPassword.setOnClickListener(this);
        View vSetting = view.findViewById(R.id.setting);
        vSetting.setOnClickListener(this);
        View vLogout = view.findViewById(R.id.logout);
        vLogout.setOnClickListener(this);
        mLogoutText = (TextView)view.findViewById(R.id.logout_text);

        Session session = SessionUtil.getSession(getActivity());

        if (session.isLogin()) {
            User user = session.getUser();
            mUserAvatar.setImageResource(R.drawable.ic_action_user);
            mUserId.setText(String.valueOf(user.getId()));
            mUserName.setText(user.getName());
        } else {
            mUserAvatar.setImageResource(R.drawable.ic_action_user);
            mUserId.setText("nil");
            mUserName.setText("nil");
            //mVMyExpress.setVisibility(View.GONE);
            //mVResetPassword.setVisibility(View.GONE);
            mLogoutText.setText(getString(R.string.login));
        }

        return view;

    }

    private void onResetPassword() {
        if (mResetPasswordFragment == null) {
            mResetPasswordFragment = new ResetPasswordFragment();
        }
        mResetPasswordFragment.show(getFragmentManager(), getString(R.string.reset_password));
    }

    private void onLogout() {
        final Activity activity = getActivity();
        final Session session = SessionUtil.getSession(activity);
        if (session.isLogin()) {
            UserClient.logout(activity, new JsonHttpResponseHandler("UTF-8") {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    session.deleteUser(activity);
                    if (!isAdded()) {
                        return;
                    }
                    mUserAvatar.setImageResource(R.drawable.ic_action_user);
                    mUserId.setText("nil");
                    mUserName.setText("nil");
                    mVMyExpress.setVisibility(View.GONE);
                    mVResetPassword.setVisibility(View.GONE);
                    mLogoutText.setText(getString(R.string.login));
                    Toast.makeText(
                            activity.getApplicationContext()
                            , getString(R.string.message_logout_success)
                            , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if (!isAdded()) {
                        return;
                    }
                    Toast.makeText(
                            activity.getApplicationContext()
                            , getString(R.string.message_logout_fail)
                            , Toast.LENGTH_SHORT).show();;
                }
            });
        } else {
            // login
            startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                // current version is not support to edit user profile
                // startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.my_express:
                startActivity(new Intent(getActivity(), MyDeliveryActivity.class));
                break;
            case R.id.reset_password:
                onResetPassword();
                break;
            case R.id.setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.logout:
                onLogout();
                break;
        }
    }

}

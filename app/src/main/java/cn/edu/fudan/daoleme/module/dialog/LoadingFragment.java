package cn.edu.fudan.daoleme.module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/12/7.
 */
public class LoadingFragment extends DialogFragment {
    private static final String TAG = "LoadingFragment";
    private CharSequence mMessage;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.LoadingDialog);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(new ProgressBar(context));
        TextView textView = new TextView(context);
        textView.setText(mMessage);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        linearLayout.addView(textView, layoutParams);
        return linearLayout;
    }

    public void setMessage(CharSequence message) {
        mMessage = message;
    }

}

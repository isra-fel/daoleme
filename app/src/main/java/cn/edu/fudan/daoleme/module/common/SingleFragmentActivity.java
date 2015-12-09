package cn.edu.fudan.daoleme.module.common;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/13.
 */
public class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";

    private View.OnKeyListener mOnKeyListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbarMeta = getClass().getAnnotation(Toolbar.class);
        int titleResId = toolbarMeta.title();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(titleResId);
        setSupportActionBar(toolbar);
        // setNavigationIcon() must after setSupportActionBar()
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Content contentMeta = getClass().getAnnotation(Content.class);
        Class<? extends Fragment> clazz = contentMeta.fragment();

        try {
            Fragment clazzInstance = clazz.newInstance();
            if (clazzInstance instanceof View.OnKeyListener) {
                mOnKeyListener = (View.OnKeyListener)clazzInstance;
            }
            FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, clazzInstance);
            transaction.addToBackStack(TAG);
            // see http://blog.csdn.net/stoppig/article/details/31776607
            transaction.commit();
        // API level 14 is not allowed multi-catch clause with reflection
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, @NonNull KeyEvent event) {
        if (mOnKeyListener != null && mOnKeyListener.onKey(null, keyCode, event)) {
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

}

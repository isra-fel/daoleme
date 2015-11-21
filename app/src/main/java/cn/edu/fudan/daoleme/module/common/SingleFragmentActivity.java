package cn.edu.fudan.daoleme.module.common;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/13.
 */
public class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";

    private int mMenuResId;
    private android.support.v7.widget.Toolbar.OnMenuItemClickListener mMenuItemClickHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbarMeta = getClass().getAnnotation(Toolbar.class);
        int titleResId = toolbarMeta.title();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.setSubtitle(titleResId);
        setSupportActionBar(toolbar);
        // setNavigationIcon() must after setSupportActionBar()
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        mMenuResId = toolbarMeta.menu();

        Content contentMeta = getClass().getAnnotation(Content.class);
        Class<? extends Fragment> clazz = contentMeta.fragment();

        try {
            Fragment clazzInstance = clazz.newInstance();
            if (clazzInstance instanceof android.support.v7.widget.Toolbar.OnMenuItemClickListener) {
                mMenuItemClickHandler = (android.support.v7.widget.Toolbar.OnMenuItemClickListener)clazzInstance;
            }
            FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_container, clazzInstance);
            transaction.addToBackStack(TAG);
            // see http://blog.csdn.net/stoppig/article/details/31776607
            transaction.commitAllowingStateLoss();
        // API level 14 is not allowed multi-catch clause with reflection
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result;
        if (mMenuResId != -1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(mMenuResId, menu);
            result = true;
        } else {
            result = super.onCreateOptionsMenu(menu);
        }
        return result;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (mMenuItemClickHandler != null) {
            return mMenuItemClickHandler.onMenuItemClick(item);
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}

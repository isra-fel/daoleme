package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.module.common.SingleFragmentActivity;

/**
 * Created by rinnko on 2015/11/14.
 */
public class ExpressDetailFragment extends Fragment {
    private static final String TAG = "ExpressDetailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_express_detail, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    private void onMark() {
        // TODO mark
    }

    private void onPin() {
        // TODO pin
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mark:
                onMark();
                return true;
            case R.id.action_pin:
                onPin();
                return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO render menu by express_state
        inflater.inflate(R.menu.menu_express_detail, menu);
    }

}

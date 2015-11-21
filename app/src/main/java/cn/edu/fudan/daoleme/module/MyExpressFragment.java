package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.ExpressListAdapter;
import cn.edu.fudan.daoleme.database.pojo.Express;
import cn.edu.fudan.daoleme.database.pojo.ExpressHistory;

/**
 * Created by rinnko on 2015/11/15.
 */
public class MyExpressFragment extends Fragment implements ExpressListAdapter.Callbacks {
    private static final String TAG = "MyExpressFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_myexpress, container, false);
        ListView listView = (ListView)view.findViewById(R.id.express_list);
        listView.setAdapter(new ExpressListAdapter(getActivity(), listView, getData(), this));
        return view;

    }

    private List<Express> getData() {
        List<Express> expresses = new ArrayList<>();
        for ( int i = 0; i < 10; i++) {
            Express express = new Express();
            express.tag = "tag";
            List<ExpressHistory> expressHistory = new ArrayList<>();
            expressHistory.add(new ExpressHistory());
            express.history = expressHistory;
            express.lastUpdate = new Date();
            expresses.add(express);
        }
        return expresses;
    }

    @Override
    public void onMark(int position) {
        Log.d(TAG, "onMark");
    }

    @Override
    public void onReceive(int position) {
        Log.d(TAG, "onReceive");
    }

    @Override
    public void onPin(int position) {
        Log.d(TAG, "onPin");
    }

    @Override
    public void onLongPress(int position) {
        Log.d(TAG, "onLongPress");
    }

}

package cn.edu.fudan.daoleme.module;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.adapter.ExpressDetailAdapter;
import cn.edu.fudan.daoleme.database.pojo.Express;
import cn.edu.fudan.daoleme.database.pojo.ExpressHistory;

/**
 * Created by rinnko on 2015/11/10.
 */
public class ReceiveFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ReceiveFragment";

    private Spinner mExpressName;
    private EditText mMailId;
    private ListView mMailList;
    private ImageView mMailNotFound;
    private int mResultZoneState; // 0->viewStub, 1->listView, 2->imageView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        // TODO fetch express name list
        mExpressName = (Spinner)view.findViewById(R.id.express_company);
        // see http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"顺丰", "圆通"});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpressName.setAdapter(arrayAdapter);
        mMailId = (EditText)view.findViewById(R.id.express_id);
        Button btnQuery = (Button)view.findViewById(R.id.query);
        btnQuery.setOnClickListener(this);
        mMailList = (ListView)view.findViewById(R.id.express_list);
        mMailNotFound = (ImageView)view.findViewById(R.id.express_not_found);
        return view;
    }

    private void onQuery() {
        // TODO query a mail
        onQuerySuccess();
    }

    // see http://stackoverflow.com/questions/3334048/android-layout-replacing-a-view-with-another-view-on-run-time
    private void onQuerySuccess() {
        if (mResultZoneState == 0) {
            mMailList.setVisibility(View.VISIBLE);
        } else if (mResultZoneState == 2) {
            mMailList.setVisibility(View.VISIBLE);
            mMailNotFound.setVisibility(View.GONE);
        }

        // TODO get mail_list
        List<Express> expresses = new ArrayList<>();
        Express express = new Express();
        express.tag = "tag";
        List<ExpressHistory> expressHistory = new ArrayList<>();
        expressHistory.add(new ExpressHistory());
        express.history = expressHistory;
        express.lastUpdate = new Date();
        expresses.add(express);
        //

        mMailList.setAdapter(new ExpressDetailAdapter(getContext(), expresses));
    }

    private void onQueryFail() {
        if (mResultZoneState == 0) {
            mMailNotFound.setVisibility(View.VISIBLE);
        } else if (mResultZoneState == 1) {
            mMailNotFound.setVisibility(View.VISIBLE);
            mMailList.setVisibility(View.GONE);
        }
        // TODO render express_not_found
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                onQuery();
                break;
        }
    }
}

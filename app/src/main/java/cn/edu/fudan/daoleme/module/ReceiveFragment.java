package cn.edu.fudan.daoleme.module;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.database.pojo.Express;
import cn.edu.fudan.daoleme.database.pojo.ExpressHistory;

/**
 * Created by rinnko on 2015/11/10.
 */
public class ReceiveFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ReceiveFragment";

    private Spinner mExpressCompany;
    private EditText mExpressId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        // TODO get express company list
        String[] companyList = new String[]{"顺丰", "圆通"};
        mExpressCompany = (Spinner)view.findViewById(R.id.express_company);
        // see http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, companyList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mExpressCompany.setAdapter(arrayAdapter);
        mExpressId = (EditText)view.findViewById(R.id.express_id);
        Button btnQuery = (Button)view.findViewById(R.id.query);
        btnQuery.setOnClickListener(this);

        return view;

    }

    private void onQuery() {
        // TODO query a express
        onQuerySuccess();
    }

    // see http://stackoverflow.com/questions/3334048/android-layout-replacing-a-view-with-another-view-on-run-time
    private void onQuerySuccess() {

        // TODO get express_list
        List<Express> expresses = new ArrayList<>();
        Express express = new Express();
        express.name = "name";
        express.tag = "tag";
        List<ExpressHistory> expressHistories = new ArrayList<>();
        ExpressHistory expressHistory = new ExpressHistory();
        expressHistory.date = new Date();
        expressHistories.add(expressHistory);
        express.history = expressHistories;
        expresses.add(express);

        // TODO render express_list
    }

    private void onQueryFail() {
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

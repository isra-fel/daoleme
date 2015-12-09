package cn.edu.fudan.daoleme.module;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/10.
 */
public class SendFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SendFragment";

    private Spinner mExpressCompany;
    private EditText mFrom, mTo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);

        String[] expressCompanyList = getResources().getStringArray(R.array.express_company_list);
        mExpressCompany = (Spinner)view.findViewById(R.id.express_company);
        // see http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, expressCompanyList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mExpressCompany.setAdapter(arrayAdapter);
        mFrom = (EditText)view.findViewById(R.id.express_from);
        mTo = (EditText)view.findViewById(R.id.express_to);
        View btnQuery = view.findViewById(R.id.query);
        btnQuery.setOnClickListener(this);

        return view;

    }

    private void onQuerySuccess() {
        // TODO query success
    }

    private void onQueryFail() {
        // TODO query fail
    }

    private void onQuery() {
        // TODO validate input
        String company = (String)mExpressCompany.getSelectedItem();
        String from = mFrom.getText().toString();
        String to = mTo.getText().toString();
        // TODO no such API
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

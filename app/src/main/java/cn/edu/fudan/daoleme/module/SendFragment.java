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

import cn.edu.fudan.daoleme.R;

/**
 * Created by rinnko on 2015/11/10.
 */
public class SendFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SendFragment";

    private Spinner mExpressName;
    private EditText mFrom, mTo, mX, mY, mZ, mWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        // TODO fetch express list
        mExpressName = (Spinner)view.findViewById(R.id.express_company);
        // see http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new String[]{"顺丰", "圆通"});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mExpressName.setAdapter(arrayAdapter);
        mFrom = (EditText)view.findViewById(R.id.express_from);
        mTo = (EditText)view.findViewById(R.id.express_to);
        mX = (EditText)view.findViewById(R.id.express_x);
        mY = (EditText)view.findViewById(R.id.express_y);
        mZ = (EditText)view.findViewById(R.id.express_z);
        mWeight = (EditText)view.findViewById(R.id.express_weight);
        Button btnQuery = (Button)view.findViewById(R.id.query);
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
        // TODO query
        onQuerySuccess();
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

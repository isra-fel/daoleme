package cn.edu.fudan.daoleme.module;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.constant.ExpressCompany;
import cn.edu.fudan.daoleme.data.pojo.Delivery;
import cn.edu.fudan.daoleme.module.dialog.QueryDeliveryResultFragment;
import cn.edu.fudan.daoleme.net.APIClient;
import cn.edu.fudan.daoleme.util.LoadingUtil;
import cz.msebera.android.httpclient.Header;

/**
 * Created by rinnko on 2015/11/10.
 */
public class ReceiveFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ReceiveFragment";

    private Spinner mExpressCompany;
    private EditText mDeliveryId;
    private QueryDeliveryResultFragment mQueryDeliveryResultFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_receive, container, false);

        String[] expressCompanyList = getResources().getStringArray(R.array.express_company_list);
        mExpressCompany = (Spinner)view.findViewById(R.id.express_company);
        // see http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, expressCompanyList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mExpressCompany.setAdapter(arrayAdapter);
        mDeliveryId = (EditText)view.findViewById(R.id.delivery_id);
        View btnQuery = view.findViewById(R.id.query);
        btnQuery.setOnClickListener(this);

        return view;

    }

    private void onQuery() {

        // TODO validate input
        String company = convertBack((String) mExpressCompany.getSelectedItem());
        String deliveryId = mDeliveryId.getText().toString();

        LoadingUtil.showLoading(getActivity(), R.string.message_loading_query);
        APIClient.query(company, deliveryId, getActivity().getApplicationContext(), new JsonHttpResponseHandler("UTF-8") {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                try {
                    onQuerySuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                LoadingUtil.hideLoading(getActivity());
                onQueryFail(throwable);
            }
        });
        // onQuerySuccess(null);

    }

    private String convertBack(String selectedItem) {
        if (selectedItem.equals("圆通")) {
            return ExpressCompany.yuantong.getCompanyId();
        }
        if (selectedItem.equals("顺丰")) {
            return ExpressCompany.shunfeng.getCompanyId();
        }
        throw new IllegalArgumentException("Should be a company name: " + selectedItem);
    }

    private void onQuerySuccess(JSONObject data) throws JSONException {

        if (!isAdded()) {
            return;
        }
        System.out.println(data.toString());

        //sample data: {"message":"ok","nu":"700074134800","ischeck":"1","com":"yuantong","status":"200","condition":"F00","state":"3",
        // "data":[{"time":"2015-11-26 19:09:25","context":"客户 签收人: 近邻宝代收点 已签收  感谢使用圆通速递，期待再次为您服务","ftime":"2015-11-26 19:09:25"},
        // {"time":"2015-11-25 23:09:16","context":"北京市海淀区学清路公司(点击查询电话)n** 派件中 派件员电话17710861559","ftime":"2015-11-25 23:09:16"},
        // {"time":"2015-11-18 21:18:01","context":"北京市海淀区学清路公司 已收入","ftime":"2015-11-18 21:18:01"},
        // {"time":"2015-11-18 10:01:23","context":"北京转运中心 已发出,下一站 北京市海淀区学清路","ftime":"2015-11-18 10:01:23"},
        // {"time":"2015-11-18 09:59:05","context":"北京转运中心 已拆包","ftime":"2015-11-18 09:59:05"},
        // {"time":"2015-11-18 09:26:46","context":"北京转运中心 已收入","ftime":"2015-11-18 09:26:46"},
        // {"time":"2015-11-17 02:17:55","context":"郑州转运中心 已发出,下一站 北京转运中心","ftime":"2015-11-17 02:17:55"},
        // {"time":"2015-11-17 02:17:49","context":"郑州转运中心 已收入","ftime":"2015-11-17 02:17:49"},
        // {"time":"2015-11-16 23:35:13","context":"河南省郑州市中原区公司 已发出,下一站 郑州转运中心","ftime":"2015-11-16 23:35:13"},
        // {"time":"2015-11-14 00:39:49","context":"虎门转运中心 已发出,下一站 北京转运中心","ftime":"2015-11-14 00:39:49"},
        // {"time":"2015-11-14 00:10:34","context":"虎门转运中心 已收入","ftime":"2015-11-14 00:10:34"},
        // {"time":"2015-11-13 22:49:28","context":"广东省东莞市新东城公司 已发出,下一站 虎门转运中心","ftime":"2015-11-13 22:49:28"},
        // {"time":"2015-11-13 19:36:20","context":"广东省东莞市新东城公司 已打包","ftime":"2015-11-13 19:36:20"},
        // {"time":"2015-11-13 12:08:51","context":"广东省东莞市新东城公司(点击查询电话) 已揽收","ftime":"2015-11-13 12:08:51"}]}
        // TODO parse data to delivery
        Delivery delivery = new Delivery(data.getString("com"), data.getString("nu"), "", false, false, new ArrayList<String>());
        JSONArray history = data.getJSONArray("data");
        for (int i = 0; i < history.length(); ++i) {
            delivery.addState(history.getJSONObject(i).getString("context"));
        }

        if (mQueryDeliveryResultFragment == null) {
            mQueryDeliveryResultFragment = new QueryDeliveryResultFragment();
        }
        mQueryDeliveryResultFragment.setDelivery(delivery);
        mQueryDeliveryResultFragment.show(getFragmentManager(), TAG);

    }

    private void onQueryFail(Throwable why) {

        if (!isAdded()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.nothing_found)
                .setTitle(R.string.query)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

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

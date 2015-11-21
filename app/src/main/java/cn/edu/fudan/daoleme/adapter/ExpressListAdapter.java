package cn.edu.fudan.daoleme.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.database.pojo.Express;
import cn.edu.fudan.daoleme.view.ExpressItemView;

/**
 * Created by rinnko on 2015/11/15.
 */
public class ExpressListAdapter extends ArrayAdapter<Express> implements ExpressItemView.Callbacks, View.OnClickListener {
    private List<Express> mExpressList;
    private ExpressItemView.Factory mViewFactory;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onMark(int position);
        void onReceive(int position);
        void onPin(int position);
        void onLongPress(int position);
    }

    public ExpressListAdapter(Context context, ListView listView, List<Express> expressList, Callbacks callbacks) {
        super(context, R.layout.layout_express_item, expressList);
        mExpressList = expressList;
        mViewFactory = new ExpressItemView.Factory(context, listView, this);
        mCallbacks = callbacks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExpressItemView view;
        ViewHolder viewHolder;
        if (convertView != null) {
            view = (ExpressItemView)convertView;
            viewHolder = (ViewHolder)view.getTag();
        } else {
            view = mViewFactory.create();
            viewHolder = new ViewHolder();
            viewHolder.position = position;
            viewHolder.expressName = (TextView)view.findViewById(R.id.express_company);
            viewHolder.expressTag = (TextView)view.findViewById(R.id.express_tag);
            viewHolder.expressDetail = (TextView)view.findViewById(R.id.express_detail);
            viewHolder.expressLastUpdate = (TextView)view.findViewById(R.id.express_last_update);
            viewHolder.btnMark = (Button)view.findViewById(R.id.express_toggle_mark);
            viewHolder.btnReceive = (Button)view.findViewById(R.id.express_toggle_receive);
            viewHolder.btnPin = (Button)view.findViewById(R.id.express_toggle_pin);
            viewHolder.btnMark.setOnClickListener(this);
            viewHolder.btnReceive.setOnClickListener(this);
            viewHolder.btnPin.setOnClickListener(this);
            view.setTag(viewHolder);
        }
        Express express = mExpressList.get(position);
        viewHolder.expressName.setText(express.name);
        viewHolder.expressTag.setText(express.tag);
        viewHolder.expressDetail.setText(express.history.toString());
        viewHolder.expressLastUpdate.setText(express.lastUpdate.toString());
        return view;
    }

    @Override
    public void onClick(View v) {
        ExpressItemView view = (ExpressItemView)v.getParent().getParent().getParent();
        int position = ((ViewHolder)view.getTag()).position;
        switch (v.getId()) {
            case R.id.express_toggle_mark:
                mCallbacks.onMark(position);
                break;
            case R.id.express_toggle_receive:
                mCallbacks.onReceive(position);
                break;
            case R.id.express_toggle_pin:
                mCallbacks.onPin(position);
                break;
        }
    }

    static class ViewHolder {
        int position;
        TextView expressName;
        TextView expressTag;
        TextView expressDetail;
        TextView expressLastUpdate;
        Button btnMark;
        Button btnReceive;
        Button btnPin;
    }

    @Override
    public void onLongPress(MotionEvent e, ExpressItemView view) {
        mCallbacks.onLongPress(((ViewHolder)view.getTag()).position);
    }

}

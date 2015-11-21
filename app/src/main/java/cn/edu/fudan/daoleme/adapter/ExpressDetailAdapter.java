package cn.edu.fudan.daoleme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.database.pojo.Express;

/**
 * Created by rinnko on 2015/11/13.
 */
public class ExpressDetailAdapter extends ArrayAdapter<Express> {
    private List<Express> expresses;

    public ExpressDetailAdapter(Context context, List<Express> expresses) {
        super(context, R.layout.layout_express_detail, expresses);
        this.expresses = expresses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = new LinearLayout(getContext());
            layoutInflater.inflate(R.layout.layout_express_detail, layout);
            convertView = layout;
            viewHolder = new ViewHolder();
            viewHolder.tag = (TextView)convertView.findViewById(R.id.tag);
            viewHolder.state = (TextView)convertView.findViewById(R.id.state);
            viewHolder.lastUpdate = (TextView)convertView.findViewById(R.id.last_update);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Express express = expresses.get(position);
        viewHolder.tag.setText(express.tag);
        viewHolder.state.setText(express.history.get(express.history.size() - 1).toString());
        viewHolder.lastUpdate.setText(express.lastUpdate.toString());
        return convertView;
    }

    static class ViewHolder {
        TextView tag;
        TextView state;
        TextView lastUpdate;
    }
}

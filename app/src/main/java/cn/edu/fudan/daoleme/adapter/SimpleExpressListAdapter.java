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
public class SimpleExpressListAdapter extends ArrayAdapter<Express> {
    private List<Express> expresses;

    public SimpleExpressListAdapter(Context context, List<Express> expresses) {
        super(context, R.layout.layout_express_item_simple, expresses);
        this.expresses = expresses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout = (LinearLayout)layoutInflater.inflate(R.layout.layout_express_item_simple, parent, false);
            holder = new ViewHolder();
            holder.tag = (TextView)convertView.findViewById(R.id.express_tag);
            holder.state = (TextView)convertView.findViewById(R.id.express_state);
            holder.lastUpdate = (TextView)convertView.findViewById(R.id.express_last_update);
            layout.setTag(holder);
            convertView = layout;
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Express express = expresses.get(position);
        holder.tag.setText(express.tag);
        holder.state.setText(express.history.get(express.history.size() - 1).toString());
        holder.lastUpdate.setText(express.history.get(express.history.size() - 1).date.toString());
        return convertView;
    }

    static class ViewHolder {
        TextView tag;
        TextView state;
        TextView lastUpdate;
    }
}

package cn.edu.fudan.daoleme.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.database.pojo.Express;

/**
 * Created by rinnko on 2015/11/15.
 */
public class ExpressListAdapter extends ArrayAdapter<Express>  {
    private static final String TAG = "ExpressListAdapter";

    private ListView mListView;

    public ExpressListAdapter(Context context, List<Express> expressList, ListView listView) {
        super(context, R.layout.layout_express_item, expressList);
        mListView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder)convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.layout_express_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView)layout.findViewById(R.id.express_company);
            holder.tag = (TextView)layout.findViewById(R.id.express_tag);
            holder.detail = (TextView)layout.findViewById(R.id.express_detail);
            holder.lastUpdate = (TextView)layout.findViewById(R.id.express_last_update);
            layout.setTag(holder);
         // cause onItemClickListener not work
         //   layout.setLongClickable(true);
            convertView = layout;
        }
        Express express = getItem(position);
        holder.name.setText(express.name);
        holder.tag.setText(express.tag);
        holder.detail.setText(express.history.toString());
        holder.lastUpdate.setText(express.history.get(express.history.size() - 1).date.toString());
        boolean isItemChecked = mListView.isItemChecked(position);
        convertView.setBackgroundResource(isItemChecked
                ? R.drawable.bg_checked_primary
                : R.drawable.bg_pressable_primary);
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView tag;
        TextView detail;
        TextView lastUpdate;
    }

}

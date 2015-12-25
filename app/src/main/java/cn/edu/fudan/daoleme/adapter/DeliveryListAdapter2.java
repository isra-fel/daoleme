package cn.edu.fudan.daoleme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.data.pojo.Delivery;

/**
 * Created by rinnko on 2015/11/15.
 */
public class DeliveryListAdapter2 extends ArrayAdapter<Delivery>  {
    private static final String TAG = "DeliveryListAdapter";

    private ListView mListView;

    public DeliveryListAdapter2(Context context, List<Delivery> deliveryList, ListView listView) {
        super(context, R.layout.layout_delivery_item, deliveryList);
        mListView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder)convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.layout_delivery_item, parent, false);
            holder = new ViewHolder();
            holder.expressCompany = (TextView)layout.findViewById(R.id.express_company);
            holder.tag = (TextView)layout.findViewById(R.id.tag);
            holder.stateSummary = (TextView)layout.findViewById(R.id.state_summary);
            holder.date = (TextView)layout.findViewById(R.id.date);
            holder.isPinned = (ImageView)layout.findViewById(R.id.ic_pinned);
            holder.isReceived = (ImageView)layout.findViewById(R.id.ic_received);
            layout.setTag(holder);
         // cause onItemClickListener not work
         //   layout.setLongClickable(true);
            convertView = layout;
        }
        Delivery delivery = getItem(position);
        holder.expressCompany.setText(delivery.getExpressCompanyName());
        holder.tag.setText(delivery.getTag());
        holder.stateSummary.setText(delivery.getState().toString());
        holder.date.setText(new Date().toString());
        //if (!delivery.isPinned()) {
            holder.isPinned.setVisibility(View.GONE);
        //}
        //if (!delivery.isReceived()) {
            holder.isReceived.setVisibility(View.GONE);
        //}
        boolean isItemChecked = mListView.isItemChecked(position);
        convertView.setBackgroundResource(isItemChecked
                ? R.drawable.bg_checked_primary
                : R.drawable.bg_pressable_primary);
        return convertView;
    }

    static class ViewHolder {
        TextView expressCompany;
        TextView tag;
        TextView stateSummary;
        TextView date;
        ImageView isPinned;
        ImageView isReceived;
    }

}

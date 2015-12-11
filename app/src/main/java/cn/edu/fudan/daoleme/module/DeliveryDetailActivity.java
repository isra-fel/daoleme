package cn.edu.fudan.daoleme.module;

import android.content.Intent;
import android.os.Bundle;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.module.common.Content;
import cn.edu.fudan.daoleme.module.common.SingleFragmentActivity;
import cn.edu.fudan.daoleme.module.common.Toolbar;

/**
 * Created by rinnko on 2015/11/14.
 */
@Toolbar(title = R.string.delivery_detail)
@Content(fragment = DeliveryDetailFragment.class)
public class DeliveryDetailActivity extends SingleFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //Bundle bundle = intent.getBundleExtra("bundle");
        Bundle bundle = new Bundle();
        bundle.putString("deliveryId", "xxx");
        mContentFragment.setArguments(bundle);
    }
}

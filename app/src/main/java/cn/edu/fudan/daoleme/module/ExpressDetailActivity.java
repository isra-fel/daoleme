package cn.edu.fudan.daoleme.module;

import android.view.MenuItem;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.module.common.Content;
import cn.edu.fudan.daoleme.module.common.SingleFragmentActivity;
import cn.edu.fudan.daoleme.module.common.Toolbar;

/**
 * Created by rinnko on 2015/11/14.
 */
@Toolbar(title = R.string.express_detail, menu = R.menu.menu_express_detail)
@Content(fragment = ExpressDetailFragment.class)
public class ExpressDetailActivity extends SingleFragmentActivity {}

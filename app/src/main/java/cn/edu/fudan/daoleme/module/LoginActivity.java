package cn.edu.fudan.daoleme.module;

import cn.edu.fudan.daoleme.R;
import cn.edu.fudan.daoleme.module.common.Content;
import cn.edu.fudan.daoleme.module.common.SingleFragmentActivity;
import cn.edu.fudan.daoleme.module.common.Toolbar;

/**
 * Created by rinnko on 2015/11/14.
 */
@Toolbar(title = R.string.login)
@Content(fragment = LoginFragment.class)
public class LoginActivity extends SingleFragmentActivity {}

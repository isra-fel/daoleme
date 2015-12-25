package cn.edu.fudan.daoleme.data.constant;

import android.content.Context;

import cn.edu.fudan.daoleme.R;

/**
 * Created by felix on 2015/12/25.
 */
public enum ExpressCompany {
    yuantong(R.string.express_company_yuantong),
    shunfeng(R.string.express_company_shunfeng);

    private int mResId;

    public String getString(Context context) {
        return context.getString(mResId);
    }

    public String getCompanyId() {
        return name();
    }

    ExpressCompany(int mResId) {
        this.mResId = mResId;
    }
}

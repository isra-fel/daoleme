package cn.edu.fudan.daoleme.data.constant;

import android.content.Context;

import cn.edu.fudan.daoleme.R;

/**
 * Created by felix on 2015/12/25.
 */
public enum ExpressCompany {
    yuantong(R.string.express_company_yuantong),
    shunfeng(R.string.express_company_shunfeng),
    shentong(R.string.express_company_shentong),
    yunda(R.string.express_company_yunda),
    zhongtong(R.string.express_company_zhongtong);

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

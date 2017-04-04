package cn.kalyter.ss.view;

import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.MicroblogDetailContract;
import cn.kalyter.ss.model.Microblog;

/**
 * Created by Kalyter on 2017-4-2 0002.
 */

public class MicroblogDetailActivity extends BaseActivity implements MicroblogDetailContract.View {
    @Override
    public void showDetail(Microblog microblog) {

    }

    @Override
    protected void injectComponent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_microblog_detail;
    }
}

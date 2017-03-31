/**
 * Created by Kalyter on 2016/11/18.
 */
package cn.kalyter.ss.view;

import cn.kalyter.ss.R;
import cn.kalyter.ss.common.BaseActivity;
import cn.kalyter.ss.contract.NewContract;

public class NewActivity extends BaseActivity implements NewContract.View {
    @Override
    protected void injectComponent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new;
    }
}

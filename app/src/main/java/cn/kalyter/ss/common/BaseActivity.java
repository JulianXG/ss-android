/**
 * Created by Julian on 2016/9/12.
 */
package cn.kalyter.ss.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectComponent();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    protected abstract void injectComponent();

    protected abstract int getLayoutId();

}

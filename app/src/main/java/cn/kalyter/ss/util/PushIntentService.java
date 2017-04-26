package cn.kalyter.ss.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import cn.kalyter.ss.config.Config;
import cn.kalyter.ss.view.MicroblogDetailActivity;

/**
 * Created by Kalyter on 2017-4-9 0009.
 */

public class PushIntentService extends GTIntentService {
    private static final String TAG = "PushIntentService";

    @Override
    public void onReceiveServicePid(Context context, int i) {
        Log.i(TAG, "onReceiveServicePid: " + i);
    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.i(TAG, "onReceiveClientId: " + s);
        SharedPreferences mSharedPreferences = getSharedPreferences(Config.SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Config.KEY_CLIENT_ID, s);
        editor.apply();
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        Log.i(TAG, "onReceiveMessageData: " );
        byte[] payload = gtTransmitMessage.getPayload();
        if (payload != null) {
            long microblogId = Long.parseLong(new String(payload));
            Log.i(TAG, "microblogId: " + microblogId);
            Intent intent = new Intent(getBaseContext(), MicroblogDetailActivity.class);
            intent.putExtra(Config.INTENT_MICROBLOG_ID, microblogId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        Log.i(TAG, "onReceiveOnlineState: " + b);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        Log.i(TAG, "onReceiveCommandResult: ");
    }
}

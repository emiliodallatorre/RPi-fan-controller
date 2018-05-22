package gq.emiliodallatorre.rpi_fan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class fanService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, fanController.class);
        context.startService(myIntent);
    }
}
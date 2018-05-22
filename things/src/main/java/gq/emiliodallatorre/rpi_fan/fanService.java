package gq.emiliodallatorre.rpi_fan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class fanService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent i = new Intent(context, fanController.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}

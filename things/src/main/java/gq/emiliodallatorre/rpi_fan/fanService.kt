package gq.emiliodallatorre.rpi_fan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class FanService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val i = Intent(context, FanController::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}

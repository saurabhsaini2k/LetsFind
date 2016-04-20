package xcal.letsfind;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
    private NotificationUtils notificationUtils;
    public GCMIntentService() {
        super("228417489761");
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        //displayMessage(context, "Your device registred with GCM");
        Log.d("NAME", "dgdfgdfgdfg");
        ServerUtilities.register(context, Signup.name, Signup.email, Signup.password,Signup.number, registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        //displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {

        String message = intent.getExtras().getString("price");
        String title = intent.getExtras().getString("title");
        String email = intent.getExtras().getString("email");
        DatabaseHelper db = new DatabaseHelper(context);
        db.insertMsg(email,title,message);

        Log.i(TAG, "Received message "+message);

       // generateNotification(context, message);


            Intent resultIntent = new Intent(context, Notification.class);
        showNotificationMessage(context,title, message, resultIntent);


    }

    private void showNotificationMessage(Context context,String title, String message, Intent intent) {

        notificationUtils = new NotificationUtils(context);



        notificationUtils.showNotificationMessage(title,message, intent);
    }
    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        //displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        //displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        //displayMessage(context, getString(R.string.gcm_recoverable_error,errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private void generateNotification(Context context, String message) {
        Intent i = new Intent(this,Notification.class);
        PendingIntent p = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);



        final String Group = "group";
        NotificationID notificationID = new NotificationID();
        int notifyID = notificationID.getID();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.lf)
                .setContentTitle("Lets Find")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setGroup(Group)
                .setContentIntent(p);
        int numMessages = 0;
        notificationBuilder.setContentText(message).setNumber(notifyID);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notifyID /* ID of notification */, notificationBuilder.build());



        //numMessages++;


    }



}

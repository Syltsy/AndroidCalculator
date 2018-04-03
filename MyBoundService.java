import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyBoundService extends Service {
    private static final String TAG = "MyBoundService";
    MediaPlayer player;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        MyBoundService getService() {
            // Return this instance of MyBoundService so clients can call public methods
            return MyBoundService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "My Bound Service Created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate");

        player = MediaPlayer.create(this, R.raw.gd8164kb);
        //player = MediaPlayer.create(this, R.raw.influence);
        player.setLooping(false); // Set looping
        player.start(); // if the start-command of a player is called from here and startservice is not called
        // then the bound service is stopped (and destroyed) automatically when the caller is destroyed
        // if no other program is not using the bound service
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Bound Service Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
        player.stop();
        player.release();
    }

	/*@Override // deprecated
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		player.start();
	}*/

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Bound Service Starting", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onStart");
        player.start();
        return super.onStartCommand(intent,flags,startId);
    } */
}

package com.example.yuval;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends Service {
    FirebaseDatabase database;
    DatabaseReference ref;
    Query q;
    NotificationCompat.Builder builder;
    NotificationManager manager;
    SharedPreferences sharedPreferences;

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences = getSharedPreferences("remembered username", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentPlayerName = sharedPreferences.getString("remembered username", "Guest");
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        q = ref.child("top3").orderByKey();
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Player currentPlayer = snapshot.getValue(Player.class);
                if (currentPlayer.getName().equals(currentPlayerName))
                {
                    builder = new NotificationCompat.Builder(getBaseContext())
                            .setSmallIcon(R.drawable.alert)
                            .setContentTitle("Alert!")
                            .setContentText("Your position in the top 3 leaderboard has changed!")
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    Intent notificationIntent = new Intent(getBaseContext(),MainMenuActivity.class);
                    PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(contentIntent);
                    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(0,builder.build());
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return 1;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onStop()
    {

    }
}
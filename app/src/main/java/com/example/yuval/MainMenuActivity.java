package com.example.yuval;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_play, btn_leaderboard, btn_account;
    SharedPreferences sharedPreferences;
    TextView tv_mainMenu;
    Connectivty connectivty = new Connectivty();
    boolean isUnityLoaded = false;
    static String playingInThisPhone;
    DatabaseReference ref;
    FirebaseDatabase database;
    Intent notificationServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        notificationServiceIntent = new Intent(MainMenuActivity.this, NotificationService.class);
        startService(notificationServiceIntent);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivty, filter);

        btn_play = (Button) findViewById(R.id.btn_in);
        btn_play.setOnClickListener(this);
        btn_leaderboard = (Button) findViewById(R.id.btn_return);
        btn_leaderboard.setOnClickListener(this);
        btn_account = (Button) findViewById(R.id.btn_out);
        btn_account.setOnClickListener(this);
        tv_mainMenu = (TextView) findViewById(R.id.tv_mainMenu);
        sharedPreferences = getSharedPreferences("remembered username", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String rememberedUsername = sharedPreferences.getString("remembered username", "Guest");
        playingInThisPhone = rememberedUsername;
        tv_mainMenu.setText("Welcome! You are logged in as: " + rememberedUsername);


        Query q = ref.child("top3").orderByValue();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    Player p = new Player("temp", 0, "temp");
                    ref.child("top3").child("1st").setValue(p);
                    ref.child("top3").child("2nd").setValue(p);
                    ref.child("top3").child("3rd").setValue(p);
                    q.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.secondary_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.i_how)
        {
            Intent intent = new Intent(this, HtPActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.i_about)
        {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_leaderboard){
            Intent goToLeaderboard = new Intent(MainMenuActivity.this, MainActivity.class);
            startActivity(goToLeaderboard);
        }
        if (view == btn_account){
            Intent goToAccount = new Intent(MainMenuActivity.this, AccountActivity.class);
            startActivity(goToAccount);
        }
        if(view == btn_play){
            //btnLoadUnity();
            Intent goToAccount = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(goToAccount);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivty);
    }


    public void btnLoadUnity() {
        isUnityLoaded = true;
        Intent intent = new Intent(this, MainUnityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) isUnityLoaded = false;
    }

    public void unloadUnity(Boolean doShowToast) {
        if(isUnityLoaded) {
            Intent intent = new Intent(this, MainUnityActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("doQuit", true);
            startActivity(intent);
            isUnityLoaded = false;
        }
        else if(doShowToast) showToast("Show Unity First");
    }

    public void btnUnloadUnity(View v) {
        unloadUnity(true);
    }

    public void showToast(String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

}

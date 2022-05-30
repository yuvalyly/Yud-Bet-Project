package com.example.yuval;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_in, btn_out, btn_return;
    EditText et_username, et_password;
    FirebaseDatabase database;
    DatabaseReference ref;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        btn_in = (Button) findViewById(R.id.btn_in);
        btn_in.setOnClickListener(this);
        btn_return = (Button) findViewById(R.id.btn_return);
        btn_return.setOnClickListener(this);
        btn_out = (Button) findViewById(R.id.btn_out);
        btn_out.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        sharedPreferences = getSharedPreferences("remembered username", 0);
        editor = sharedPreferences.edit();

        String rememberedUsername = sharedPreferences.getString("remembered username", "Guest");
        if(!rememberedUsername.equals("Guest"))
            et_username.setText(rememberedUsername);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_return){
            Intent goToMain = new Intent(AccountActivity.this, MainMenuActivity.class);
            startActivity(goToMain);
        }
        if(view == btn_in)
        {
            if(et_username.getText().toString().trim().length() == 0 || et_password.getText().toString().trim().length() == 0)
            {
                Toast.makeText(AccountActivity.this, "You didn't entered your username or password.",Toast.LENGTH_LONG).show();
            }
            else
            {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Query q = ref.child("users").orderByValue();
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userFound = false;
                        Player p = new Player();
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            p = ds.getValue(Player.class);
                            if(p.getName().equals(username) && p.getPassword().equals(password))
                            {
                                Toast.makeText(AccountActivity.this, "Successfully logged in as: " + username, Toast.LENGTH_LONG).show();
                                userFound = true;
                                editor.putString("remembered username", username);
                                editor.commit();
                            }
                            else if(p.getName().equals(username) && !p.getPassword().equals(password))
                            {
                                Toast.makeText(AccountActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                                userFound = true;
                            }
                        }
                        if(!userFound)
                        {
                            EditText et = new EditText(AccountActivity.this);
                            AlertDialog dialog = new AlertDialog.Builder(AccountActivity.this)
                                    .setTitle("User not found")
                                    .setMessage("Do you want to create a new account?")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Player p = new Player(username, 0, password);
                                            ref.child("users").child(username).setValue(p);
                                            editor.putString("remembered username", username);
                                            editor.commit();
                                            Toast.makeText(AccountActivity.this, "Sucssussfully singed up as: " + username, Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .setNegativeButton("no", null)
                                    .create();
                            dialog.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        if(view == btn_out)
        {
            editor.putString("remembered username", "Guest");
            editor.commit();
            et_username.setText("");
            Toast.makeText(AccountActivity.this, "Logged out successfully", Toast.LENGTH_LONG).show();
        }
    }
}

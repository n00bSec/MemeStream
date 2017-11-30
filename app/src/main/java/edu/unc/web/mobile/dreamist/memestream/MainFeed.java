package edu.unc.web.mobile.dreamist.memestream;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainFeed extends AppCompatActivity {

    private final String TAG = "MEMESTREAM";

    private Toolbar toolbar;

    EditText email_input, password_input;

    private FirebaseAuth mAuth;
    private LinearLayoutCompat social_feed;
    private LinearLayoutCompat login_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        social_feed = findViewById(R.id.social_feed);
        email_input = findViewById(R.id.email);
        password_input = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        login_details = findViewById(R.id.login_details);
    }

    protected void onStart(){
        super.onStart();
        //Check if user is signed in
        if(mAuth.getCurrentUser() == null){
            Snackbar.make(social_feed, "Not logged in!",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        Snackbar.make(social_feed,
                "Logged in as:" + mAuth.getCurrentUser().getDisplayName(),
                Snackbar.LENGTH_SHORT)
                .show();

        initFeed(mAuth.getCurrentUser());
    }

    public void createAccount(View v){
        String email_str = email_input.getText().toString();
        String password_str = password_input.getText().toString();

        mAuth.createUserWithEmailAndPassword(email_str, password_str)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Login successful.");
                            FirebaseUser user = mAuth.getCurrentUser();
                            initFeed(user);
                        } else {
                            Log.w(TAG, "Login failed.");
                            Toast.makeText(MainFeed.this,
                                    "Login failed.",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    public void signIn(View v){
        String email_str = email_input.getText().toString();
        String password_str = password_input.getText().toString();

        mAuth.signInWithEmailAndPassword(email_str, password_str)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Login successful.");
                            FirebaseUser user = mAuth.getCurrentUser();
                            initFeed(user);
                        } else {
                            Log.w(TAG, "Login failure.");
                            Toast.makeText(MainFeed.this,
                                    "Login failure", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    protected void initFeed(FirebaseUser user){
        login_details.setVisibility(View.GONE);
        toolbar.setTitle("Memer:" + user.getUid());
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

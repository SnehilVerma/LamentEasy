package com.ntpc.lamenteasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntpc.lamenteasy.models.User;

public class Login extends AppCompatActivity {



    //public static final String EXTRA_USER_KEY = "user_key";

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private String user_key;
    private int save_profile=0;


    /// database shit////
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        /*user_key = getIntent().getStringExtra(EXTRA_USER_KEY);
        if (user_key == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }*/




        // reference to user in the database created //




        /*
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));

            finish();
        }
        */

        // set the view now
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });


    }



    public void onClick(View view){
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //checkProfile(save_profile);


                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        progressBar.setVisibility(View.GONE);
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                                    }
                                } else if(task.isSuccessful()){

                                    FirebaseUser user=auth.getCurrentUser();
                                    user_key=user.getUid();
                                    mDatabase=FirebaseDatabase.getInstance().getReference().child("users").child(user_key);

                                    ValueEventListener userListener=new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User user=dataSnapshot.getValue(User.class);

                                            String pro=user.profile;
                                            if(pro.equals("admin")){
                                                Toast.makeText(getApplicationContext(), "found admin!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(Login.this, MainActivity.class));
                                                finish();


                                            }
                                            else if(pro.equals("employee")){
                                                Toast.makeText(getApplicationContext(), "found employee!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(Login.this, EmployeeActivity.class));
                                                finish();



                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    };
                                    mDatabase.addValueEventListener(userListener);


                                    //startActivity(new Intent(Login.this, MainActivity.class));
                                    //finish();




                                }



                            }
                        });
            }








    private void checkProfile(int save_profile){
        if(save_profile==1)
        { startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        else if(save_profile==2)
        {Intent intent = new Intent(Login.this, EmployeeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}




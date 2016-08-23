package com.ntpc.lamenteasy;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ntpc.lamenteasy.models.Complaint;
import com.ntpc.lamenteasy.models.User;

import java.util.HashMap;
import java.util.Map;

public class NewPost extends Activity {

    private DatabaseReference mDatabase;
    private EditText title;
    private EditText content;
    private FirebaseAuth auth;

    private String REQUIRED="Required";
    private static final String TAG = "NewPost";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_post);

        auth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);

        findViewById(R.id.fab_submit_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });






    }




    private void submitPost(){
        final String mTitle=title.getText().toString();
        final String mContent=content.getText().toString();


        if (TextUtils.isEmpty(mTitle)) {
            content.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(mContent)) {
            content.setError(REQUIRED);
            return;
        }

        FirebaseUser user=auth.getCurrentUser();
        final String userId=user.getUid();


        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //get user value
                        User user=dataSnapshot.getValue(User.class);

                        if(user==null){
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPost.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, mTitle, mContent);
                        }

                        }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }


    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Complaint complaint = new Complaint(userId, username, title, body);
        Map<String, Object> complaintValues = complaint.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, complaintValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, complaintValues);



        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}


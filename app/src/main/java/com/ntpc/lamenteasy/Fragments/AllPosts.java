package com.ntpc.lamenteasy.Fragments;

import android.app.Activity;
import android.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Snehil Verma on 9/22/2016.
 */
public class AllPosts extends PostList {

    public AllPosts() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }
}

package com.ntpc.lamenteasy.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snehil Verma on 7/14/2016.
 */
public class Complaint {

    public String uid;
    public String author;
    public String title;
    public String body;

    public int viewed;
    //public int taskprogress;
    public int resolved;

    public Complaint(){}


    public Complaint(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]






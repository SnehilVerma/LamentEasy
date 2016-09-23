package com.ntpc.lamenteasy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Snehil Verma on 9/8/2016.
 */
public class EmployeeHome extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_home);

    }



    public void newPost(View view){

        startActivity(new Intent(EmployeeHome.this, NewPost.class));
    }

    public void manageAccount(View view){
        startActivity(new Intent(EmployeeHome.this,EmployeeActivity.class));
    }

    public void viewPost(View view){
        startActivity(new Intent(EmployeeHome.this,PostsSection.class));
    }
}

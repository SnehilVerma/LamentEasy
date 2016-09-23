package com.ntpc.lamenteasy.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntpc.lamenteasy.R;
import com.ntpc.lamenteasy.models.Complaint;

/**
 * Created by Snehil Verma on 9/23/2016.
 */
public class ComplaintHolder extends RecyclerView.ViewHolder {



    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public ComplaintHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(Complaint complaint) {
        titleView.setText(complaint.title);
        authorView.setText(complaint.author);
       // numStarsView.setText(String.valueOf(complaint.starCount));
        bodyView.setText(complaint.body);

        //starView.setOnClickListener(starClickListener);
    }
}



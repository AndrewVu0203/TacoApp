package com.example.andrewvu.rowdyhackapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.zip.Adler32;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 1/1/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mItemTextView = new ArrayList<>();
    private Context mContext;
    private RequestQueue mQueue;

    public RecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images, ArrayList<String> itemText) {
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        mItemTextView = itemText;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lisitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));
        holder.itemTextView.setText(mItemTextView.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;
        TextView itemTextView;
        int i = 0;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            itemTextView = itemView.findViewById(R.id.itemTextView);

            mQueue = Volley.newRequestQueue(mContext);

            jsonParseFromFirebase();
        }

        private void jsonParseFromFirebase() {
            Random random = new Random();
            int ranNum = random.nextInt(20);
            i = ranNum;
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String path = String.format("HEBCoupons/%d/price", i);
                        Double data = (Double) dataSnapshot.child(path).getValue();
                        itemTextView.append("Coupons: " +  data);

                        String pathSecond = String.format("HEBCoupons/%d/product", i);
                        String dataSecond = (String) dataSnapshot.child(pathSecond).getValue();
                        imageName.append("" + dataSecond);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });

            // write works, read does not work
            mDatabase.child("test").setValue("Hello 4:47");

        }
    }
}
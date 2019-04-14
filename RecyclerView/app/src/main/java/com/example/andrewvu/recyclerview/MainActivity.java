package com.example.andrewvu.recyclerview;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private TextView mTextView;
    private TextView mTextView1;
    private TextView mTextView2;
    private Button mParseButton;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: started.");

        final LayoutInflater factory = getLayoutInflater();

        final View textEntryView = factory.inflate(R.layout.layout_listitem, null);

        mTextView = (TextView) textEntryView.findViewById(R.id.image_name);
        mTextView1 = (TextView) textEntryView.findViewById(R.id.image_name1);
        mTextView2 = (TextView) textEntryView.findViewById(R.id.image_name2);

        mParseButton = findViewById(R.id.buttonParse);

        mQueue = Volley.newRequestQueue(this);
        mParseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSONFromFirebase();
            }
        });

        // TODO: Set the Text to TextView in layout_listitem
    }

    private void getJSONFromFirebase() {
        // TODO (now) : jsonParse changeName
        String url = "https://api.myjson.com/bins/hj470";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("employees");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);

                        String firstName = employee.getString("firstname");
                        int age = employee.getInt("age");
                        String mail = employee.getString("mail");

                        mTextView.append(firstName + ", " + String.valueOf(age) + ", " + mail + "\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Havasu Falls");

    }

    private void initRecyclerViewJSON() {
        // TODO: put JSON data to RecyclerView
        // TODO: modify RecyclerViewAdapter

        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
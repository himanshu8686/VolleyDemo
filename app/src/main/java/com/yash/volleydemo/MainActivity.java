package com.yash.volleydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.yash.volleydemo.adapter.RecyclerViewAdapter;
import com.yash.volleydemo.model.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private final String JSON_URL="https://restwork.000webhostapp.com/volleyDemo/json/anime.json";
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private List<Anime> animeList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        jsonRequest();
    }

    private void jsonRequest()
    {
        jsonArrayRequest=new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                JSONObject jsonObject=null;
                for (int i=0;i< response.length();i++)
                {
                    try {
                        jsonObject= response.getJSONObject(i);
                        Anime anime=new Anime();
                        anime.setName(jsonObject.getString("name"));
                        anime.setDescription(jsonObject.getString("description"));
                        anime.setRating(jsonObject.getString("Rating"));
                        anime.setNb_episode(jsonObject.getInt("episode"));
                        anime.setStudio(jsonObject.getString("studio"));
                        System.out.println(jsonObject.getString("img"));
                        anime.setImage_url(jsonObject.getString("img"));
                        anime.setCategorie(jsonObject.getString("categorie"));
                        animeList.add(anime);
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                setupRecyclerView(animeList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void setupRecyclerView(List<Anime> animeList)
    {
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,animeList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
    }

    private void initializeViews()
    {
        recyclerView=findViewById(R.id.recyclerView);
        animeList=new ArrayList<>();
    }
}

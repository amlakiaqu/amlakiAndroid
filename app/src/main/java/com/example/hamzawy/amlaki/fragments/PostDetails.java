package com.example.hamzawy.amlaki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hamzawy.amlaki.Adapters.PostPropertiesRecyclerViewAdapter;
import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.activities.CoreActivity;
import com.example.hamzawy.amlaki.activities.MyApp;
import com.example.hamzawy.amlaki.controllers.GetPostDetails;
import com.example.hamzawy.amlaki.models.Post;
import com.example.hamzawy.amlaki.models.Property;

import java.util.ArrayList;

import static android.support.v7.recyclerview.R.attr.layoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetails extends Fragment {

    private int postId;

    public PostDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        ((CoreActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        initView(view);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                ((CoreActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                ((CoreActivity)getActivity()).getSupportFragmentManager().popBackStack();
                return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null){
            postId = getArguments().getInt("POST_ID");
        }
        getPostDetails();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void getPostDetails() {
        GetPostDetails getPostDetails = new GetPostDetails(PostDetails.this,getActivity());
        getPostDetails.execute(postId);
    }

    private RecyclerView recyclerView;
    private ImageView productImage;
    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        productImage = (ImageView) view.findViewById(R.id.image);
    }


    public void setPostDetails(Post post) {
        if (post.getImage() != null){
            Glide.with(MyApp.getAppContext()).load(post.getImage()).into(productImage);
        }

        if (post.getProperties() != null) {
            initRecyclerView(post.getProperties());
        }
    }

    private void initRecyclerView(ArrayList<Property> properties) {
        PostPropertiesRecyclerViewAdapter postPropertiesRecyclerViewAdapter = new PostPropertiesRecyclerViewAdapter(getActivity() , properties);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(postPropertiesRecyclerViewAdapter);
    }
}

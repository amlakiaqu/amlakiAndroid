package com.example.hamzawy.amlaki.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hamzawy.amlaki.Adapters.PostsRecyclerViewAdapter;
import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.activities.CoreActivity;
import com.example.hamzawy.amlaki.controllers.GetSellPosts;
import com.example.hamzawy.amlaki.models.Post;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellPosts extends Fragment {


    public SellPosts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_posts, container, false);
        ((CoreActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getSellPosts();
    }

    private void getSellPosts() {
        GetSellPosts getSellPosts = new GetSellPosts(SellPosts.this, getActivity());
        getSellPosts.execute();
    }

    private RecyclerView recyclerView;

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }


    public void setPosts(ArrayList<Post> posts) {
        initRecyclerView(posts);
    }

    private void initRecyclerView(ArrayList<Post> posts) {
        PostsRecyclerViewAdapter postsRecyclerViewAdapter = new PostsRecyclerViewAdapter(getActivity(), posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(postsRecyclerViewAdapter);
    }

    public void openPostDetails(int id) {
        PostDetails postDetails = new PostDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("POST_ID", id);
        postDetails.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, postDetails, PostDetails.class.toString()).addToBackStack(PostDetails.class.toString()).commit();
    }
}

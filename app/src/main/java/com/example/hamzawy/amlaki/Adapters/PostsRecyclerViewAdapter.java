package com.example.hamzawy.amlaki.Adapters;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hamzawy.amlaki.R;
import com.example.hamzawy.amlaki.activities.CoreActivity;
import com.example.hamzawy.amlaki.activities.MyApp;
import com.example.hamzawy.amlaki.fragments.SellPosts;
import com.example.hamzawy.amlaki.models.Post;
import com.example.hamzawy.amlaki.models.Property;

import java.util.ArrayList;

/**
 * Created by HamzawY on 1/17/17.
 */

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;

    private ArrayList<Post> posts = new ArrayList<>();
    private Activity activity;

    public PostsRecyclerViewAdapter(Activity activity, ArrayList<Post> posts) {
        this.posts = posts;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View emptyView = inflater.inflate(R.layout.post_item, parent, false);
            return new PostViewHolder(emptyView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof PostViewHolder) {
            PostViewHolder postViewHolder = (PostViewHolder) holder;
            Post post = posts.get(position);
            Glide.with(MyApp.getAppContext()).load(post.getImage()).into(postViewHolder.image);
            if (post.getProperties() != null && !post.getProperties().isEmpty()){
                for (int i = 0 ; i < post.getProperties().size() ; i++){
                    Property property = post.getProperties().get(i);
                    if (property.getCode().equals("PRICE")){
                        postViewHolder.price.setText(property.getPivot().getValue() + " ₪");
                        break;
                    }
                }
            }
            postViewHolder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentManager fm = ((CoreActivity)activity).getSupportFragmentManager();

                    for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
                        Log.i("", "Found fragment: " + fm.getBackStackEntryAt(entry).getName());
                    }
                    SellPosts sellPosts = (SellPosts)fm.findFragmentByTag(SellPosts.class.toString());
                    if (sellPosts != null){
                        sellPosts.openPostDetails(posts.get(position).getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView price;
        private Button details;
        private ImageView image;

        public PostViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            details = (Button) itemView.findViewById(R.id.details);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

    }
}

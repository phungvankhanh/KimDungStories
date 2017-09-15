package com.example.admiralk.kimdungstories;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by AdmiralK on 5/2/2017.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {
    private static final String TAG = "StoryAdapter";
    private List<Story> mStories;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Typeface face;

    public StoryAdapter(Context context, List<Story> Stories) {
        mStories = Stories;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StoryAdapter.StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.card_view_story, parent, false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoryAdapter.StoryViewHolder holder, int position) {
        Story story = mStories.get(position);
        holder.mTextview.setText(story.getmStName());
        //holder.mImageView.setImageResource(R.drawable.b2);
//        face = Typeface.createFromAsset(mContext.getAssets(), "fonts/SEGOEUI.TTF");
//        holder.mTextview.setTypeface(face);
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }


//    public interface mClickListener {
//        public void mClick(View v, int position);
//    }


    public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextview;
        private ImageView mImageView;
        public StoryViewHolder(final View itemView) {
            super(itemView);
            mTextview = (TextView) itemView.findViewById(R.id.text_view_story_name);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view_story);
//            mButton.setOnClickListener((View.OnClickListener) itemView.getContext());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                @Override
//                public void onClick(View v) {
//                    //mButton.setText("Hello fuck");
//                }
//            });

            itemView.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext,Main2Activity.class);
            int pos = getAdapterPosition();
            Story story = mStories.get(pos);
            int id = story.getmId();
            String nameStory = story.getmStName();
            System.out.println(id);
            String strId = Integer.toString(id);
            //strId.toString(id);
            intent.putExtra("ID",strId);
            intent.putExtra("NAME",nameStory);
            mContext.startActivity(intent);
//            mContext.startActivity(new Intent(mContext,Main2Activity.class));
            ///mButton.setText("Khanh");
        }
    }
}

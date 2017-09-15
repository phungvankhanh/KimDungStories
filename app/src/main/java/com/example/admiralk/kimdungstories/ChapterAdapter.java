package com.example.admiralk.kimdungstories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AdmiralK on 5/4/2017.
 */

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private List<ChapterOfStory> mChapters;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Typeface face;

    public ChapterAdapter(List<ChapterOfStory> Chapters, Context Context) {
        this.mChapters = Chapters;
        this.mContext = Context;
        mLayoutInflater = LayoutInflater.from(Context);
    }


    @Override
    public ChapterAdapter.ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.view_chapter_name, parent, false);
        return new ChapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChapterAdapter.ChapterViewHolder holder, int position) {
        ChapterOfStory chapterOfStory = mChapters.get(position);
        holder.mTextView.setText(chapterOfStory.getmDename());
    }

    @Override
    public int getItemCount() {
        return mChapters.size();
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view_chapter_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, Main3Activity.class);
            int pos = getAdapterPosition();
            ChapterOfStory chapter = mChapters.get(pos);
            int iID = chapter.getmDeId();
            String strID = Integer.toString(iID);
            intent.putExtra("DEID", strID);
            mContext.startActivity(intent);
        }
    }
}

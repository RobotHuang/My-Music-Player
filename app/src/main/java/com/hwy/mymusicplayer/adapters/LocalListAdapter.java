package com.hwy.mymusicplayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.activities.PlayLocalActivity;
import com.hwy.mymusicplayer.models.LocalMusicModel;

import java.util.List;

public class LocalListAdapter extends RecyclerView.Adapter<LocalListAdapter.ViewHolder> {

    private Context mContext;

    private View mItemView;

    private RecyclerView mRv;

    private List<LocalMusicModel> musicModelList;

    public LocalListAdapter(Context mContext, RecyclerView mRv, List<LocalMusicModel> musicModelList) {
        this.mContext = mContext;
        this.mRv = mRv;
        this.musicModelList = musicModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LocalMusicModel localMusicModel = musicModelList.get(position);
        holder.tvAuthor.setText(localMusicModel.getAuthor());
        holder.tvName.setText(localMusicModel.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayLocalActivity.class);
                intent.putExtra("name", localMusicModel.getName());
                intent.putExtra("author", localMusicModel.getAuthor());
                intent.putExtra("path", localMusicModel.getPath());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView tvName, tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }

}

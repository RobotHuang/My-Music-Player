package com.hwy.mymusicplayer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hwy.mymusicplayer.R;
import com.hwy.mymusicplayer.activities.PlayMusicActivity;
import com.hwy.mymusicplayer.models.MusicModel;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;

    private View mItemView;

    private RecyclerView mRv;

    private boolean isCalculationRvHeight;

    private List<MusicModel> mDataSource;

    public MusicListAdapter(Context context, RecyclerView recyclerView, List<MusicModel> dataSource) {
        mContext = context;
        mRv = recyclerView;
        mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music, parent, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        setRecyclerViewHeight();

        final MusicModel musicModel = mDataSource.get(position);

        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(viewHolder.ivIcon);

        viewHolder.tvName.setText(musicModel.getName());
        viewHolder.tvAuthor.setText(musicModel.getAuthor());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID, musicModel.getMusicId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    private void setRecyclerViewHeight() {

        if (isCalculationRvHeight || mRv == null) return;

        isCalculationRvHeight = true;

        RecyclerView.LayoutParams itemLayoutParams = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        int itemCount = getItemCount();
        int recyclerViewHeight = itemLayoutParams.height * itemCount;
        LinearLayout.LayoutParams recyclerViewLayoutParams = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        recyclerViewLayoutParams.height = recyclerViewHeight;
        mRv.setLayoutParams(recyclerViewLayoutParams);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView ivIcon;
        TextView tvName, tvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}

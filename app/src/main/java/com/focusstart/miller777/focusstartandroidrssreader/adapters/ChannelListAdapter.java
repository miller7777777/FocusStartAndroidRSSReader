package com.focusstart.miller777.focusstartandroidrssreader.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.focusstart.miller777.focusstartandroidrssreader.activities.NewsListActivity;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;

import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelListViewHolder> {

    private Context context;
    private List<ChannelModel> channels;

    public ChannelListAdapter(Context context, List<ChannelModel> channels) {
        this.context = context;
        this.channels = channels;
    }

    @NonNull
    @Override
    public ChannelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForItem = R.layout.channel_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForItem, parent, false);
        ChannelListViewHolder viewHolder = new ChannelListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelListViewHolder holder, int position) {

        holder.bind(channels.get(position));
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    class ChannelListViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitle;
        TextView itemDescription;
        TextView itemDate;
        String itemLink;


        public ChannelListViewHolder(View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.channel_list_item_title);
            itemDescription = itemView.findViewById(R.id.channel_list_item_description);
            itemDate = itemView.findViewById(R.id.channel_list_item_date);

            itemView.setOnClickListener(view -> onClick());
        }

        private void onClick() {


                Intent newsListActivityIntent = new Intent(context, NewsListActivity.class);
                newsListActivityIntent.putExtra("CHANNEL_RSS_URL", itemLink);
                context.startActivity(newsListActivityIntent);
        }

        public void bind(ChannelModel channel){

            itemTitle.setText(channel.getTitle());
            itemDescription.setText(channel.getDescription());
            itemDate.setText(channel.getLastBuildDate());
            itemLink = channel.getLink();
        }
    }
}

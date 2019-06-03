package com.focusstart.miller777.focusstartandroidrssreader.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.focusstart.miller777.focusstartandroidrssreader.DAO.DataBase;
import com.focusstart.miller777.focusstartandroidrssreader.activities.NewsListActivity;
import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.apps.Constants;
import com.focusstart.miller777.focusstartandroidrssreader.model.ChannelModel;

import java.util.List;

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelListViewHolder> {

    private Context context;
    private List<ChannelModel> channels;
    private static final String TAG = ChannelListAdapter.class.getSimpleName();

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

    class ChannelListViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemDescription;
        TextView itemDate;
        String itemLink;
        String itemRssLink;


        public ChannelListViewHolder(View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.channel_list_item_title);
            itemDescription = itemView.findViewById(R.id.channel_list_item_description);
            itemDate = itemView.findViewById(R.id.channel_list_item_date);

            itemView.setOnClickListener(view -> onClick());
            itemView.setOnLongClickListener(view -> onLongClick());
        }

        private void onClick() {


            Intent newsListActivityIntent = new Intent(context, NewsListActivity.class);
            newsListActivityIntent.putExtra("CHANNEL_RSS_URL", itemRssLink);
            newsListActivityIntent.putExtra(Constants.EXTRA_CHANNEL_TITLE_KEY, itemTitle.getText());
            Log.d(TAG, "itemLink = " + itemLink);
            Log.d(TAG, "title = " + itemTitle.getText());
            context.startActivity(newsListActivityIntent);
        }

        private boolean onLongClick() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete channel?")
                    .setMessage(itemTitle.getText())
                    .setIcon(R.drawable.ic_delete_forever_black_24dp)
                    .setCancelable(true)
                    .setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Toast.makeText(context, "Deleted! " + itemTitle.getText(), Toast.LENGTH_LONG).show();

                                    deleteChannel(itemLink);
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }

        private void deleteChannel(String itemLink) {
            Log.d(TAG, "Удалить канал: " + itemLink);
            DataBase db = new DataBase();
            db.deleteChannelByLink(itemLink);
        }

        public void bind(ChannelModel channel) {

            itemTitle.setText(channel.getTitle());
            itemDescription.setText(channel.getDescription());
            itemDate.setText(channel.getLastBuildDate());
            itemLink = channel.getLink();
            itemRssLink = channel.getRssLink();
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            contextMenu.setHeaderTitle("Select The Action");
//            contextMenu.add(0, view.getId(), 0, "Delete channel");
//        }


    }
}

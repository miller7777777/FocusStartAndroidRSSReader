package com.focusstart.miller777.focusstartandroidrssreader.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.focusstart.miller777.focusstartandroidrssreader.R;
import com.focusstart.miller777.focusstartandroidrssreader.activities.ItemViewActivity;
import com.focusstart.miller777.focusstartandroidrssreader.activities.NewsListActivity;
import com.focusstart.miller777.focusstartandroidrssreader.model.ItemModel;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {

    private Context context;
    private List<ItemModel> newsItems;

    public NewsListAdapter(Context context, List<ItemModel> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }


    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForItem, parent, false);
        NewsListViewHolder viewHolder = new NewsListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder viewHolder, int position) {
        viewHolder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class NewsListViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle;
        TextView itemDescription;
        TextView itemDate;
        String itemLink;

        public NewsListViewHolder( View itemView) {

            super(itemView);

            itemTitle = itemView.findViewById(R.id.news_list_item_title);
            itemDescription = itemView.findViewById(R.id.news_list_item_description);
            itemDate = itemView.findViewById(R.id.news_list_item_date);

            itemView.setOnClickListener(view -> onClick());
        }

        private void onClick() {
//            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemLink)));

            Intent itemWebViewIntent = new Intent(context, ItemViewActivity.class);
            itemWebViewIntent.setAction(Intent.ACTION_VIEW);
            itemWebViewIntent.putExtra("LINK", itemLink);
            context.startActivity(itemWebViewIntent);
        }

        private void bind(ItemModel itemModel){

            itemTitle.setText(itemModel.getTitle());
            itemDescription.setText(itemModel.getDescription());
            itemDate.setText(itemModel.getPubDate());
            itemLink = itemModel.getLink();
        }
    }
}

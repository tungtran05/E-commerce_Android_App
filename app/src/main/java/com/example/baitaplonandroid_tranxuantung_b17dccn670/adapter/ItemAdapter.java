package com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitaplonandroid_tranxuantung_b17dccn670.ItemDetailActivity;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        // set dữ liệu để hiển thị lên
        Item item = itemList.get(position);
        if(item == null)
            return;

        Picasso.with(context).load(item.getItemImageList().get(0).getUrl()).into(holder.imageViewItem);
        holder.textViewItemName.setText(item.getName());
        holder.textViewItemPrice.setText(String.valueOf(item.getSalePrice()));
        holder.cardView_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra("itemId", item.getId());
                intent.putExtra("itemName", item.getName());
                intent.putExtra("itemPrice", item.getSalePrice());
                String[] itemImageUrls = new String[item.getItemImageList().size()];
                for(int i=0; i<item.getItemImageList().size(); i++) {
                    itemImageUrls[i] = item.getItemImageList().get(i).getUrl();
                }
                intent.putExtra("itemImageUrls", itemImageUrls);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemList != null)
            return itemList.size();

        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewItem;
        private TextView textViewItemName;
        private TextView textViewItemPrice;
        private CardView cardView_Item;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
            cardView_Item = itemView.findViewById(R.id.cardView_Item);
        }
    }
}

package com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Cart_Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cart_Item_InOrderHistoryAdapter extends RecyclerView.Adapter<Cart_Item_InOrderHistoryAdapter.CartItemViewHolder2>{

    private Context context;
    private List<Cart_Item> cart_ItemList;

    public Cart_Item_InOrderHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Cart_Item> cart_ItemList) {
        this.cart_ItemList = cart_ItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartItemViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_iteminorderhistory, parent, false);

        return new Cart_Item_InOrderHistoryAdapter.CartItemViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Item_InOrderHistoryAdapter.CartItemViewHolder2 holder, int position) {
        // set dữ liệu để hiện thị lên
        Cart_Item cart_item = cart_ItemList.get(position);
        if(cart_item == null)
            return;

        String rawImageURL = cart_item.getItem().getItemImageList().get(0).getUrl();
        String imageUrl = rawImageURL.substring(0, rawImageURL.length()-5+1) + "-350" +
                rawImageURL.substring(rawImageURL.length()-5+1);
        Log.d("imageURLSmall", imageUrl);
        Picasso.with(context).load(imageUrl).into(holder.imageViewCartItemInOrderHistory);
        holder.textViewCartItemNameInOrderHistory.setText(cart_item.getItem().getName());
        holder.textViewCartItemPriceInOrderHistory.setText("Price: " + cart_item.getItem().getSalePrice());
        holder.textViewCartItemQuantityInOrderHistory.setText(String.valueOf(cart_item.getQuantity()));
        holder.textViewCartItemIntoMoneyInOrderHistory.setText(String.valueOf(cart_item.getQuantity() * cart_item.getItem().getSalePrice()));
    }

    @Override
    public int getItemCount() {
        if(cart_ItemList != null)
            return cart_ItemList.size();
        return 0;
    }

    public class CartItemViewHolder2 extends RecyclerView.ViewHolder {

        private ImageView imageViewCartItemInOrderHistory;
        private TextView textViewCartItemNameInOrderHistory;
        private TextView textViewCartItemPriceInOrderHistory;
        private TextView textViewCartItemQuantityInOrderHistory;
        private TextView textViewCartItemIntoMoneyInOrderHistory;


        public CartItemViewHolder2(@NonNull View itemView) {
            super(itemView);

            imageViewCartItemInOrderHistory = itemView.findViewById(R.id.imageViewCartItemInOrderHistory);
            textViewCartItemNameInOrderHistory = itemView.findViewById(R.id.textViewCartItemNameInOrderHistory);
            textViewCartItemPriceInOrderHistory = itemView.findViewById(R.id.textViewCartItemPriceInOrderHistory);
            textViewCartItemQuantityInOrderHistory = itemView.findViewById(R.id.textViewCartItemQuantityInOrderHistory);
            textViewCartItemIntoMoneyInOrderHistory = itemView.findViewById(R.id.textViewCartItemIntoMoneyInOrderHistory);
        }
    }
}

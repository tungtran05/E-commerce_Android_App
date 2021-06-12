package com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter;

import android.content.Context;
import android.media.Image;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment.CartFragment;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Cart_Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Static_Cart;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class Cart_ItemAdapter extends RecyclerView.Adapter<Cart_ItemAdapter.CartItemViewHolder>{

    private Context context;
    private List<Cart_Item> cart_ItemList;

    public Cart_ItemAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Cart_Item> cart_ItemList) {
        this.cart_ItemList = cart_ItemList;
        notifyDataSetChanged();
    }

//    public float getSubPayment(List<Cart_Item> cart_ItemList) {
//        float subPayment = 0;
//        for(int i=0; i<cart_ItemList.size(); i++) {
//            subPayment += cart_ItemList.get(i).getQuantity() * cart_ItemList.get(i).getItem().getSalePrice();
//        }
//        return subPayment;
//    }

    public void update_Cart_Item(int quantity, long cart_Item_Id, long item_Id) {
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/cart_items/" + cart_Item_Id +
                "?cartId=" + Static_Cart.id + "&itemId=" + item_Id;
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.put(url).setPriority(Priority.HIGH).addJSONObjectBody(jsonBody)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("updated cart_item",response.toString());
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(context, "Add quantity failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete_Cart_Item(long cart_Item_Id, int position) {
        String url = "https://eprojectbytranxuantung.herokuapp.com/api/cart_items/delete2/" + cart_Item_Id;
        AndroidNetworking.delete(url).setPriority(Priority.HIGH).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Delete cart_item",response.toString());
                        cart_ItemList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(context, "Delete cart_item failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @NonNull
    @Override
    public Cart_ItemAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cartitem, parent, false);

        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_ItemAdapter.CartItemViewHolder holder, int position) {
        // set dữ liệu để hiện thị lên
        Cart_Item cart_item = cart_ItemList.get(position);
        if(cart_item == null)
            return;

        String rawImageURL = cart_item.getItem().getItemImageList().get(0).getUrl();
        String imageUrl = rawImageURL.substring(0, rawImageURL.length()-5+1) + "-350" +
                rawImageURL.substring(rawImageURL.length()-5+1);
        Log.d("imageURLSmall", imageUrl);
        Picasso.with(context).load(imageUrl).into(holder.imageViewCartItem);
        holder.textViewCartItemName.setText(cart_item.getItem().getName());
        holder.textViewCartItemPrice.setText("Price: " + cart_item.getItem().getSalePrice());
        holder.textViewCartItemQuantity.setText(String.valueOf(cart_item.getQuantity()));
        holder.textViewCartItemIntoMoney.setText(String.valueOf(cart_item.getQuantity() * cart_item.getItem().getSalePrice()));
//        holder.textViewSubPayment.setText("SubPayment: " + getSubPayment(cart_ItemList));

        holder.imageViewDeleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long cart_Item_Id = cart_item.getId();
                Toast.makeText(context, "Delete Cart_Item", Toast.LENGTH_SHORT).show();
                delete_Cart_Item(cart_Item_Id, position);
                // update SubPayment
//                holder.textViewSubPayment.setText("SubPayment: " + setSubPayment(cart_ItemList));
            }
        });

        holder.imageViewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.textViewCartItemQuantity.getText().toString());
                if(quantity > 1) {
                    quantity = quantity - 1;
                    holder.textViewCartItemQuantity.setText(String.valueOf(quantity));
                    holder.textViewCartItemIntoMoney.setText(String.valueOf(quantity * cart_item.getItem().getSalePrice()));

                }
                long cart_Item_Id = cart_item.getId();
//                Log.d("cart_item_id", String.valueOf(cart_Item_Id));
                long item_Id = cart_item.getItem().getId();
//                Log.d("item_id", String.valueOf(item_Id));

                // Update cart_item
                update_Cart_Item(quantity, cart_Item_Id, item_Id);
                // update subPayment
//                Log.d("SubPayment", String.valueOf(getSubPayment(cart_ItemList)));
//                holder.textViewSubPayment.setText("SubPayment: " + setSubPayment(cart_ItemList));
            }
        });

        holder.imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.textViewCartItemQuantity.getText().toString());
                quantity = quantity + 1;
                holder.textViewCartItemQuantity.setText(String.valueOf(quantity));
                holder.textViewCartItemIntoMoney.setText(String.valueOf(quantity * cart_item.getItem().getSalePrice()));

                long cart_Item_Id = cart_item.getId();
//                Log.d("cart_item_id", String.valueOf(cart_Item_Id));
                long item_Id = cart_item.getItem().getId();
//                Log.d("item_id", String.valueOf(item_Id));

                // Update cart_item
                update_Cart_Item(quantity, cart_Item_Id, item_Id);
                // update subPayment
//                Log.d("SubPayment", String.valueOf(getSubPayment(cart_ItemList)));
//                holder.textViewSubPayment.setText("SubPayment: " + setSubPayment(cart_ItemList));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cart_ItemList != null)
            return cart_ItemList.size();
        return 0;
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewCartItem;
        private TextView textViewCartItemName;
        private TextView textViewCartItemPrice;
        private TextView textViewCartItemQuantity;
        private ImageView imageViewSub;
        private ImageView imageViewAdd;
        private ImageView imageViewDeleteCartItem;
        private TextView textViewCartItemIntoMoney;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCartItem = itemView.findViewById(R.id.imageViewCartItem);
            textViewCartItemName = itemView.findViewById(R.id.textViewCartItemName);
            textViewCartItemPrice = itemView.findViewById(R.id.textViewCartItemPrice);
            textViewCartItemQuantity = itemView.findViewById(R.id.textViewCartItemQuantity);
            imageViewSub = itemView.findViewById(R.id.imageViewSub);
            imageViewAdd = itemView.findViewById(R.id.imageViewAdd);
            imageViewDeleteCartItem = itemView.findViewById(R.id.imageViewDeleteCartItem);
            textViewCartItemIntoMoney = itemView.findViewById(R.id.textViewCartItemIntoMoney);
        }
    }
}
package com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitaplonandroid_tranxuantung_b17dccn670.OrderHistoryDetailActivity;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.Item;
import com.example.baitaplonandroid_tranxuantung_b17dccn670.model.OrderHistory;

import org.w3c.dom.Text;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ItemViewHolder>{

    private Context context;
    private List<OrderHistory> orderHistoryList;

    public OrderHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_orderhistory, parent, false);

        return new OrderHistoryAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ItemViewHolder holder, int position) {
        // set dữ liệu để hiển thị lên
        OrderHistory orderHistory = orderHistoryList.get(position);
        if(orderHistory == null)
            return;

        holder.textViewRecipientName.setText("Recipient Name: " + orderHistory.getRecipientName());
        holder.textViewRecipientPhoneNumber.setText("Recipient Phone number: " + orderHistory.getRecipientPhoneNumber());
        holder.textViewCreatedAt.setText("Ordered Time: " + orderHistory.getCreatedAt());
        holder.textViewStatus.setText("Order Status: " + orderHistory.getStatus());
        holder.textViewPaymentMethod.setText("Payment Method: " + orderHistory.getPaymentMethod());
        holder.textViewTotalPayment.setText("Total Payment: " + orderHistory.getTotalPayment());
        holder.buttonViewDetailOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderHistoryDetailActivity.class);
                intent.putExtra("orderId", orderHistory.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(orderHistoryList != null)
            return orderHistoryList.size();
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewRecipientName, textViewRecipientPhoneNumber, textViewCreatedAt,
                textViewStatus, textViewPaymentMethod, textViewTotalPayment;
        private Button buttonViewDetailOrderHistory;
        private CardView cardview_orderHistory;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRecipientName = itemView.findViewById(R.id.textViewRecipientName);
            textViewRecipientPhoneNumber = itemView.findViewById(R.id.textViewRecipientPhoneNumber);
            textViewCreatedAt = itemView.findViewById(R.id.textViewCreatedAt);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewPaymentMethod = itemView.findViewById(R.id.textViewPaymentMethod);
            textViewTotalPayment = itemView.findViewById(R.id.textViewTotalPayment);
            buttonViewDetailOrderHistory = itemView.findViewById(R.id.buttonViewDetailOrderHistory);
            cardview_orderHistory = itemView.findViewById(R.id.cardview_orderHistory);
        }
    }
}

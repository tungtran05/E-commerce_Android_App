package com.example.baitaplonandroid_tranxuantung_b17dccn670.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.baitaplonandroid_tranxuantung_b17dccn670.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ItemImagesAdapter extends PagerAdapter {

    Context context;
    String[] imageUrls;
    LayoutInflater layoutInflater;

    public ItemImagesAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(imageUrls != null)
            return imageUrls.length;
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull  Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item_image, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        Picasso.with(context).load(imageUrls[position]).into(imageView);
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

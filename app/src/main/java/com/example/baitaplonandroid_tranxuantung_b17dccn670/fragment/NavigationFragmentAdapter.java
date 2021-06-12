package com.example.baitaplonandroid_tranxuantung_b17dccn670.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class NavigationFragmentAdapter extends FragmentStatePagerAdapter {
    private int numPage = 5;

    public NavigationFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TshirtFragment();
            case 1: return new PantsFragment();
            case 2: return new BagsFragment();
            case 3: return new CartFragment();
            case 4: return new UserFragment();
            default: return new TshirtFragment();
        }
    }

    @Override
    public int getCount() {
        return numPage;
    }
}

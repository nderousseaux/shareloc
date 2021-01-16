package com.example.shareloc.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareloc.R;

public class UserFragment extends Fragment {

    private View viewUser;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewUser = inflater.inflate(R.layout.fragment_user, container, false);

        return viewUser;
    }
}
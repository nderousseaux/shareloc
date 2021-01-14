package com.example.shareloc.ui.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareloc.R;

public class TestFragment extends Fragment {

    private TestViewModel mViewModel;

    private View viewTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewTest = inflater.inflate(R.layout.fragment_test, container, false);

        return viewTest;
    }
}
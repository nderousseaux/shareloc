package com.example.shareloc.ui.test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareloc.R;

public class TestFragment extends Fragment {

    private TestViewModel mViewModel;

    private View viewTest;

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewTest = inflater.inflate(R.layout.fragment_test, container, false);

        return viewTest;
    }
}
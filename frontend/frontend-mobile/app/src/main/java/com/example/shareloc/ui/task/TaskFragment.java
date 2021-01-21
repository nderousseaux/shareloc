package com.example.shareloc.ui.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shareloc.R;

public class TaskFragment extends Fragment {

    private View viewTask;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewTask = inflater.inflate(R.layout.fragment_task, container, false);

        return viewTask;
    }
}
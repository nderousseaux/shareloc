package com.example.shareloc.ui.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.volley.JsonNoResponseRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class AddTaskFragment extends Fragment {

    private View viewAddTask;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewAddTask = inflater.inflate(R.layout.fragment_add_task, container, false);

        TextView tvAddTask = viewAddTask.findViewById(R.id.tvAddTask);

        // On vérifie si une colocation est séléctionné, sinon on dit que y en a pas
        if(((MainActivity)getActivity()).getSelectedFlatsharing() != null)
        {
            tvAddTask.setText("Propose a task to the flatsharing");

            // On affiche le layout
            viewAddTask.findViewById(R.id.layoutAddTask).setVisibility(View.VISIBLE);

            viewAddTask.findViewById(R.id.btnAddTask).setOnClickListener(view -> clickAddTask());
        }
        else tvAddTask.setText("No flatsharing found, create one or get invited by someone");

        return viewAddTask;
    }

    // On propose une tâche à la colocation
    public void clickAddTask() {
        String name = ((EditText)viewAddTask.findViewById(R.id.edtAddTaskName)).getText().toString();
        String cost = ((EditText)viewAddTask.findViewById(R.id.edtAddTaskCost)).getText().toString();
        String description = ((EditText)viewAddTask.findViewById(R.id.edtAddTaskDescription)).getText().toString();

        if(name.isEmpty() || cost.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ((EditText)viewAddTask.findViewById(R.id.edtAddTaskName)).setText("");
        ((EditText)viewAddTask.findViewById(R.id.edtAddTaskCost)).setText("");
        ((EditText)viewAddTask.findViewById(R.id.edtAddTaskDescription)).setText("");

        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/task";

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("cost", cost);
        bodyMap.put("description", description);
        JSONObject body = new JSONObject(bodyMap);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.PUT, url, body, response -> {
            Log.e("success AddTask", response == null ? "null" : response.toString());
            Toast.makeText(getContext(), "Task added to the flatsharing", Toast.LENGTH_SHORT).show();
        }, error -> {
            Log.e("error AddTask", error.toString());
            Toast.makeText(getContext(), "Error, could not add the task", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(request);
    }
}
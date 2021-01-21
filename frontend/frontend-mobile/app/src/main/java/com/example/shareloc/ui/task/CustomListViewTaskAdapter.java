package com.example.shareloc.ui.task;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.FragWhoami;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shareloc.Api.SERVER_URL;

public class CustomListViewTaskAdapter extends ArrayAdapter {
    private final Activity activity;
    private final TaskFragment fragment;
    private final List<Task> tasksList;

    private View rowView;

    public CustomListViewTaskAdapter(Activity activity, TaskFragment fragment, List<Task> tasksList) {
        super(activity, R.layout.listview_task_row, tasksList);
        this.activity = activity;
        this.fragment = fragment;
        this.tasksList = tasksList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        rowView = inflater.inflate(R.layout.listview_task_row, null, true);

        Task taskRow = tasksList.get(position);

        TextView tvTaskRowName = rowView.findViewById(R.id.tvTaskRowName);
        TextView tvTaskRowCost = rowView.findViewById(R.id.tvTaskRowCost);
        TextView tvTaskRowDescription = rowView.findViewById(R.id.tvTaskRowDescription);

        tvTaskRowName.setText("Name : " + taskRow.getName());
        tvTaskRowCost.setText("Cost : " + taskRow.getCost());
        tvTaskRowDescription.setText("Description : " + taskRow.getDescription());

        TextView tvTaskRowStatut = rowView.findViewById(R.id.tvTaskRowStatut);
        Button btnTaskRowService = rowView.findViewById(R.id.btnTaskRowService);
        Button btnTaskRowVoteAgree = rowView.findViewById(R.id.btnTaskRowVoteAgree);
        Button btnTaskVoteDisagree = rowView.findViewById(R.id.btnTaskVoteDisagree);
        // Lance la requête pour récupérer le statut de la tache et mettre à jour l'affichage des boutons
        getTaskStatut(taskRow.getId(), tvTaskRowStatut, btnTaskRowService, btnTaskRowVoteAgree, btnTaskVoteDisagree);

        return rowView;
    }

    public void getTaskStatut(int id, TextView tvTaskRowStatut, Button btnTaskRowService, Button btnTaskRowVoteAgree, Button btnTaskVoteDisagree) {
        String token = ((MainActivity)activity).getToken();
        String url = SERVER_URL + "task/" + id  + "/state";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            switch(response) {
                case "VOTINGCREATING":
                    tvTaskRowStatut.setText("Add this task ?");
                    btnTaskRowVoteAgree.setVisibility(View.VISIBLE);
                    btnTaskVoteDisagree.setVisibility(View.VISIBLE);
                    break;
                case "REFUSE":
                    tvTaskRowStatut.setText("Task refused");
                    break;
                case "ACTIVE":
                    tvTaskRowStatut.setText("Task active");
                    btnTaskRowService.setVisibility(View.VISIBLE);
                    break;
                case "VOTINGDELETE":
                    tvTaskRowStatut.setText("Remove this task ?");
                    btnTaskRowVoteAgree.setVisibility(View.VISIBLE);
                    btnTaskVoteDisagree.setVisibility(View.VISIBLE);
                    break;
                case "DELETE":
                    tvTaskRowStatut.setText("Task deleted");
                    break;
                case "VOTEDELETEREFUSE":
                    tvTaskRowStatut.setText("Refused to delete");
                    btnTaskRowService.setVisibility(View.VISIBLE);
                    break;
                default:
                    tvTaskRowStatut.setText("Statut ?");
            }
        }, error -> {
            Log.e("error TaskStatut", error.toString());
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
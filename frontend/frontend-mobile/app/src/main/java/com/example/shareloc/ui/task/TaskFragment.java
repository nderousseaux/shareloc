package com.example.shareloc.ui.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.Task;
import com.example.shareloc.models.UserBis;
import com.example.shareloc.ui.user.CustomListViewUserAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class TaskFragment extends Fragment {

    private View viewTask;

    private List<Task> tasksList = null;

    private boolean isTaskLoaded = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewTask = inflater.inflate(R.layout.fragment_task, container, false);

        TextView tvTask = viewTask.findViewById(R.id.tvTask);

        // On vérifie si une colocation est séléctionné, sinon on dit que y en a pas
        if(((MainActivity)getActivity()).getSelectedFlatsharing() != null)
        {
            tvTask.setText("Tasks of the flatsharing " + ((MainActivity)getActivity()).getSelectedFlatsharing().getName());

            resetView();
        }
        else tvTask.setText("No flatsharing found, create one or get invited by someone");

        return viewTask;
    }

    // Actualise le fragment quand si il y a un changement
    public void resetView () {
        tasksList = null;
        isTaskLoaded = false;
        loadTasks();
        // On lance le rendu de l'interface dans une des méthode load... quand les données sont récupérées
    }

    // Récupère les taches de la colocation et les enregistre dans l'attribut privée tasksList
    public void loadTasks () {
        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/task";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success loadTasks", response.toString());

            // On récupère la liste des taches
            tasksList = new ArrayList<Task>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    Task t = new Task(obj.getInt("id"), obj.getString("name"), obj.getInt("cost"), obj.getString("description"));
                    tasksList.add(t);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            isTaskLoaded = true;
            // Lance peut être le rendu de l'interface
            if(isTaskLoaded) {
                displayUi();
            }
        }, error -> {
            Log.e("error loadTasks", error.toString());
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

    public void displayUi() {
        ListView lvTaskList = viewTask.findViewById(R.id.lvTaskList);
        CustomListViewTaskAdapter adapter = new CustomListViewTaskAdapter(getActivity(), this, tasksList);
        lvTaskList.setAdapter(adapter);
    }
}
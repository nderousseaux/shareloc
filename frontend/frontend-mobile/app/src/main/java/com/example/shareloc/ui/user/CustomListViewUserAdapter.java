package com.example.shareloc.ui.user;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.User;
import com.example.shareloc.models.UserBis;
import com.example.shareloc.volley.JsonNoResponseRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class CustomListViewUserAdapter extends ArrayAdapter {
    private final Activity activity;
    private final UserFragment fragment;
    private final List<UserBis> usersList;
    private final List<UserBis> managersList;

    public CustomListViewUserAdapter(Activity activity, UserFragment fragment, List<UserBis> usersList, List<UserBis> managersList) {
        super(activity, R.layout.listview_user_row, usersList);
        this.activity = activity;
        this.fragment = fragment;
        this.usersList = usersList;
        this.managersList = managersList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_user_row, null,true);

        TextView tvUserRowName = rowView.findViewById(R.id.tvUserRowName);
        TextView tvUserRowEmail = rowView.findViewById(R.id.tvUserRowEmail);

        UserBis userRow = usersList.get(position);

        tvUserRowName.setText(userRow.toString());
        tvUserRowEmail.setText(userRow.getEmail());

        // Si l'utilisateur de l'application est manager
        if(isManager(User.getInstance().getEmail())) {
            // Si l'utilisateur de la ligne courante est manager, on affiche un label et on laisse les boutons cachés
            if(isManager(userRow.getEmail())) {
                rowView.findViewById(R.id.layoutLabels).setVisibility(View.VISIBLE);
            }
            // Si l'utilisateur de la ligne courante n'est pas manager, on affiche les boutons
            else {
                rowView.findViewById(R.id.layoutButtons).setVisibility(View.VISIBLE);
                // Ajouter les réactions aux cliques des boutons ici
                rowView.findViewById(R.id.btnUserRowManager).setOnClickListener(v -> clickPromoteManager(userRow.getEmail()));
                rowView.findViewById(R.id.btnUserRowRemove).setOnClickListener(v -> clickRemoveFromFlatsharing(userRow.getEmail()));
            }
        }
        return rowView;
    }

    // On promouvoit l'utilisateur manager
    public void clickPromoteManager(String email) {
        String token = ((MainActivity)activity).getToken();
        int idFlatsharing = ((MainActivity)activity).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/manager/" + email;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.PUT, url, null, response -> {
            Log.e("success PromoteManager", response == null ? "null" : response.toString());

            Toast.makeText(getContext(), "User " + email + " promoted manager of the flatsharing", Toast.LENGTH_SHORT).show();

            // On réactualise le fragement
            fragment.resetView();
        }, error -> {
            Log.e("error PromoteManager", error.toString());
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

    // On supprime l'utilisateur de la colocation
    public void clickRemoveFromFlatsharing(String email) {
        String token = ((MainActivity)activity).getToken();
        int idFlatsharing = ((MainActivity)activity).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/user/" + email;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.DELETE, url, null, response -> {
            Log.e("success RemoveUser", response == null ? "null" : response.toString());

            Toast.makeText(getContext(), "User " + email + " removed from the flatsharing", Toast.LENGTH_SHORT).show();

            // On réactualise le fragement
            fragment.resetView();
        }, error -> {
            Log.e("error RemoveUser", error.toString());
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

    // On vérifie si l'utilisateur identifié par son email est manager
    public boolean isManager(String email) {
        boolean res = false;
        for ( UserBis u : managersList) {
            if (u.getEmail().equals(email)) {
                res = true;
            }
        }
        return res;
    }
}

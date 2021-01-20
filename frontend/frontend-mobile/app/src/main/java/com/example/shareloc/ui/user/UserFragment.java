package com.example.shareloc.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.User;
import com.example.shareloc.models.UserBis;
import com.example.shareloc.volley.JsonNoResponseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class UserFragment extends Fragment {

    private View viewUser;

    private List<UserBis> usersList = null;

    private List<UserBis> managersList = null;

    private boolean isUsersListLoaded = false;

    private boolean isManagersListLoaded = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewUser = inflater.inflate(R.layout.fragment_user, container, false);

        TextView tvUserList = viewUser.findViewById(R.id.tvUserList);

        // On vérifie si une colocation est séléctionné, sinon on dit que y en a pas
        if(((MainActivity)getActivity()).getSelectedFlatsharing() != null)
        {
            tvUserList.setText("Members of the flatsharing " + ((MainActivity)getActivity()).getSelectedFlatsharing().getName());

            resetView();

            viewUser.findViewById(R.id.btnAddUser).setOnClickListener(view -> clickAddUser());
        }
        else tvUserList.setText("No flatsharing found, create one or get invited by someone");

        return viewUser;
    }

    // Actualise le fragment quand un manager fait un changement (ajout d'un membre, promouvoir un membre manager, ...)
    public void resetView () {
        usersList = null;
        managersList = null;
        isUsersListLoaded = false;
        isManagersListLoaded = false;
        viewUser.findViewById(R.id.layoutAddUser).setVisibility(View.INVISIBLE);
        loadUsers();
        loadManagers();
        // On lance le rendu de l'interface dans une des méthode load... quand les données sont récupérées
    }

    // Récupère les membres de la colocation et les enregistre dans l'attribut privée usersList
    public void loadUsers () {
        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/user";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success loadUsers", response.toString());

            // On récupère la liste des membres
            usersList = new ArrayList<UserBis>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    UserBis u = new UserBis(obj.getString("email"), obj.getString("lastname"), obj.getString("firstname"));
                    usersList.add(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            isUsersListLoaded = true;
            // Lance peut être le rendu de l'interface
            if(isUsersListLoaded && isManagersListLoaded) {
                displayUi();
            }
        }, error -> {
            Log.e("error loadUsers", error.toString());
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

    // Récupère les managers de la colocation et les enregistre dans l'attribut privée managersList
    public void loadManagers () {
        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/manager";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success loadManagers", response.toString());

            // On récupère la liste des managers
            managersList = new ArrayList<UserBis>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    UserBis u = new UserBis(obj.getString("email"), obj.getString("lastname"), obj.getString("firstname"));
                    managersList.add(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            isManagersListLoaded = true;
            // Lance peut être le rendu de l'interface
            if(isUsersListLoaded && isManagersListLoaded) {
                displayUi();
            }
        }, error -> {
            Log.e("error loadManagers", error.toString());
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
        displayUserslist();
        displayAddUser();
    }

    // https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
    // On affiche la liste des membres, avec des boutons removeUser / promoteManager si manager
    public void displayUserslist () {
        ListView lvUserList = viewUser.findViewById(R.id.lvUserList);
        CustomListViewUserAdapter adapter = new CustomListViewUserAdapter(getActivity(), this, usersList, managersList);
        lvUserList.setAdapter(adapter);
    }

    // On rend visible le formulaire d'ajout de membre à la colocation si manager
    public void displayAddUser () {
        if(isManager(User.getInstance().getEmail())) {
            viewUser.findViewById(R.id.layoutAddUser).setVisibility(View.VISIBLE);
        }
    }

    // On ajoute un utilisateur à la colocation si manager
    public void clickAddUser() {
        String email = ((EditText)viewUser.findViewById(R.id.edtAddUser)).getText().toString();

        if(email.isEmpty()) {
            Toast.makeText(getContext(), "Enter a email for the user", Toast.LENGTH_SHORT).show();
            return;
        }

        ((EditText)viewUser.findViewById(R.id.edtAddUser)).setText("");

        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/user/" + email;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.PUT, url, null, response -> {
            Log.e("success AddUser", response == null ? "null" : response.toString());

            Toast.makeText(getContext(), "User " + email + " added to the flatsharing", Toast.LENGTH_SHORT).show();

            // On réactualise le fragement
            resetView();
        }, error -> {
            Log.e("error AddUser", error.toString());

            Toast.makeText(getContext(), "User email not found or user already in the flatsharing", Toast.LENGTH_SHORT).show();
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
package com.example.shareloc.ui.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.Flatsharing;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewUser = inflater.inflate(R.layout.fragment_user, container, false);

        TextView tvUserList = viewUser.findViewById(R.id.tvUserList);
        tvUserList.setText("Membres de la colocation " + ((MainActivity)getActivity()).getSelectedFlatsharing().getName());

        getUsers();

        isManager();

        viewUser.findViewById(R.id.btnAddUser).setOnClickListener(view -> clickAddUser());

        return viewUser;
    }

    // On récupère la liste des membres de la colocation et on les affichent dans le listView
    public void getUsers() {
        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/user";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success Response", response.toString());

            // La réponse est OK
            // Afficher la liste des utilisateurs dans un listView
            Toast.makeText(getContext(), "TODO : Afficher la liste des users", Toast.LENGTH_SHORT).show();

        }, error -> {
            Log.e("error Response", error.toString());

            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

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

    // On vérifie si l'utilisateur est manager, si oui on affiche le formulaire permettant d'ajouter un membre à la colocation, sinon le formulaire reste caché
    public void isManager() {
        String token = ((MainActivity)getActivity()).getToken();
        int idFlatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing().getId();
        String url = SERVER_URL + "colocation/" + idFlatsharing + "/manager";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success Response", response.toString());

            // On récupère la liste des managers
            List<UserBis> managersList = new ArrayList<UserBis>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    UserBis u = new UserBis(obj.getString("email"), obj.getString("lastname"), obj.getString("firstname"));
                    managersList.add(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // On vérifie que l'utilisateur est dans la liste
            boolean isManager = false;
            String email = User.getInstance().getEmail();
            for ( UserBis u : managersList) {
                if (u.getEmail().equals(email)) {
                    isManager = true;
                }
            }

            if(isManager) {
                Toast.makeText(getContext(), "Tu est manager :)", Toast.LENGTH_SHORT).show();

                // On affiche le formulaire d'ajout de membre
                viewUser.findViewById(R.id.layoutAddUser).setVisibility(View.VISIBLE);
            }
            else Toast.makeText(getContext(), "Tu n'est pas manager :(", Toast.LENGTH_SHORT).show();
        }, error -> {
            Log.e("error Response", error.toString());

            Toast.makeText(getContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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

    // On ajoute un utilisateur à la colocation
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
            Log.e("success Response", response == null ? "null" : response.toString());

            Toast.makeText(getContext(), "User added to the flatsharing", Toast.LENGTH_SHORT).show();

            // Met à jour la liste des membres
            getUsers();
        }, error -> {
            Log.e("error Response", error.toString());

            Toast.makeText(getContext(), "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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
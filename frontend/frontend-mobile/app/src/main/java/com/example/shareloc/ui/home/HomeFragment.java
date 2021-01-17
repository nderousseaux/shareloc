package com.example.shareloc.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.Authentication;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.Flatsharing;
import com.example.shareloc.models.User;
import com.example.shareloc.volley.JsonNoResponseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shareloc.Api.SERVER_URL;

public class HomeFragment extends Fragment {

    private View viewHome;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewHome = inflater.inflate(R.layout.fragment_home, container, false);

        // On affiche le nom et le prénom de l'utilisateur
        TextView tvUser = viewHome.findViewById(R.id.tvUser);
        tvUser.setText(User.getInstance().toString());

        // Bouton Signout
        Button btnSignout = viewHome.findViewById(R.id.btnSignout);
        btnSignout.setOnClickListener(view -> clickSignout());

        getFlatsharings();

        viewHome.findViewById(R.id.btnAddFlatsharing).setOnClickListener(view -> clickAdd());

        return viewHome;
    }

    public void clickSignout() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", MODE_PRIVATE);
        sharedPreferences.edit().putString("token", null).apply();
        User.getInstance().deleteUser();

        // On démarre l'activité d'Authentification
        Intent authenticationActivity = new Intent(getActivity(), Authentication.class);
        startActivity(authenticationActivity);
        getActivity().finish();
    }

    // On récupère la liste des colocations et on les affiche dans le spinner
    public void getFlatsharings() {
        String token = ((MainActivity)getActivity()).getToken();
        String url = SERVER_URL + "colocation";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success Response", response.toString());

            List<Flatsharing> flatsharingsList = new ArrayList<Flatsharing>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    Flatsharing f = new Flatsharing(obj.getInt("id"), obj.getString("name"));
                    flatsharingsList.add(f);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Spinner spinner = viewHome.findViewById(R.id.spinFlatsharing);

            List<String> list = new ArrayList<String>();
            for ( Flatsharing f : flatsharingsList) {
                list.add(f.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    // Récupère la colocation avec la position dans le spinner (position dans le spinner == position dans la liste flatsharingsList)
                    Flatsharing flatsharing = flatsharingsList.get(pos);
                    // Stocke la colocation séléctionné dans l'activité
                    ((MainActivity)getActivity()).setSelectedFlatsharing(flatsharing);

                    flatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing();
                    TextView tvFlatsharing = viewHome.findViewById(R.id.tvFlatsharing);
                    tvFlatsharing.setText("The flatsharing selected is " + flatsharing.getName());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
        }, error -> {
            Log.e("error Response", error.toString());

            // On affiche un message d'erreur
            Toast.makeText(getContext(), "Error : no flatsharings or could not get them", Toast.LENGTH_SHORT).show();
        }) {
            // On met le token dans le header de la requête
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        queue.add(request);
    }

    // On créer une colocation
    public void clickAdd() {
        String name = ((EditText)viewHome.findViewById(R.id.edtAddFlatsharing)).getText().toString();

        if(name.isEmpty()) {
            Toast.makeText(getContext(), "Enter a name for the flatsharing", Toast.LENGTH_SHORT).show();
            return;
        }

        ((EditText)viewHome.findViewById(R.id.edtAddFlatsharing)).setText("");

        String token = ((MainActivity)getActivity()).getToken();
        String url = SERVER_URL + "colocation";

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        JSONObject body = new JSONObject(bodyMap);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.PUT, url, body, response -> {
            Log.e("success Response", response == null ? "null" : response.toString());

            // On affiche un message pour dire c'est bon
            Toast.makeText(getContext(), "Flatsharing created, select it in the list", Toast.LENGTH_SHORT).show();

            // Met à jour la liste des colocations dans le spinner
            getFlatsharings();
        }, error -> {
            Log.e("error Response", error.toString());

            // On affiche un message d'erreur
            Toast.makeText(getContext(), "Error : network error", Toast.LENGTH_SHORT).show();
        }) {
            // On met le token dans le header de la requête
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
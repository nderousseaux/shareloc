package com.example.shareloc.ui.flatsharing;

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
import com.example.shareloc.volley.JsonNoResponseRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class FlatsharingFragment extends Fragment {

    private View viewFlatsharing;

    private List<Flatsharing> flatsharingsList = null;

    public FlatsharingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFlatsharing = inflater.inflate(R.layout.fragment_flatsharing, container, false);

        getFlatsharings();

        viewFlatsharing.findViewById(R.id.btnAddFlatsharing).setOnClickListener(view -> clickAdd());

        return viewFlatsharing;
    }

    public void getFlatsharings() {
        String token = ((MainActivity)getActivity()).getToken();
        String url = SERVER_URL + "colocation";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.e("success Response", response.toString());

            flatsharingsList = new ArrayList<Flatsharing>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    Flatsharing f = new Flatsharing(obj.getInt("id"), obj.getString("name"));
                    flatsharingsList.add(f);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Spinner spinner = viewFlatsharing.findViewById(R.id.spinFlatsharing);
            ArrayAdapter<String> adapter;
            List<String> list;

            list = new ArrayList<String>();
            for ( Flatsharing f : flatsharingsList) {
                list.add(f.getName());
            }
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, list);
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
                    TextView tvFlatsharing = viewFlatsharing.findViewById(R.id.tvFlatsharing);
                    tvFlatsharing.setText("The flatsharing selected is " + flatsharing.getName() + " / ID for debug " + flatsharing.getId());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            // La collocation est séléctionner et enregistrer dans l'activité : OK
            // Agrandir le spinner sur l'interface : A FAIRE

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

    public void clickAdd() {
        String name = ((EditText)viewFlatsharing.findViewById(R.id.edtAddFlatsharing)).getText().toString();

        if(name.isEmpty()) {
            Toast.makeText(getContext(), "Enter a name for the flatsharing", Toast.LENGTH_SHORT).show();
            return;
        }

        ((EditText)viewFlatsharing.findViewById(R.id.edtAddFlatsharing)).setText("");

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
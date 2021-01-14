package com.example.shareloc.ui.flatsharing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.volleyrequest.JsonNoResponseRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class FlatsharingFragment extends Fragment {

    private View viewFlatsharing;

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

        viewFlatsharing.findViewById(R.id.btnAddFlatsharing).setOnClickListener(view -> clickAdd());

        return viewFlatsharing;
    }

    public void clickAdd() {
        String token = ((MainActivity)getActivity()).getToken();

        String url = SERVER_URL + "colocation";

        String name = ((EditText)viewFlatsharing.findViewById(R.id.edtAddFlatsharing)).getText().toString();

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        JSONObject body = new JSONObject(bodyMap);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.PUT, url, body, response -> {
            Log.e("success Response", response == null ? "null" : response.toString());

            // On affiche un message pour dire c'est bon
            Toast.makeText(getContext(), "Flatsharing created", Toast.LENGTH_SHORT).show();

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
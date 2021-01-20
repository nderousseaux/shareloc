package com.example.shareloc.ui.profile;

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
import com.example.shareloc.models.User;
import com.example.shareloc.volley.JsonNoResponseRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class ProfileFragment extends Fragment {

    private View viewProfile;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewProfile = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvModifyProfile = viewProfile.findViewById(R.id.tvModifyProfile);
        tvModifyProfile.setText(User.getInstance().toString() + ", modify your profile here !");

        viewProfile.findViewById(R.id.btnModifyProfile).setOnClickListener(view -> clickModifyProfile());

        return viewProfile;
    }

    // Modifie les informations de l'utilisateur (sauf l'email)
    public void clickModifyProfile() {
        String firstname = ((EditText)viewProfile.findViewById(R.id.edtFirstname)).getText().toString();
        String lastname = ((EditText)viewProfile.findViewById(R.id.edtLastname)).getText().toString();
        String password = ((EditText)viewProfile.findViewById(R.id.edtPassword)).getText().toString();

        String token = ((MainActivity)getActivity()).getToken();
        String url = SERVER_URL + "user";

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("firstname", firstname);
        bodyMap.put("lastname", lastname);
        bodyMap.put("password", password);
        JSONObject body = new JSONObject(bodyMap);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.POST, url, body, response -> {
            Log.e("success ModifyProfile", response == null ? "null" : response.toString());

            Toast.makeText(getContext(), "Profile data updated", Toast.LENGTH_SHORT).show();

            // On met a jour les données dans le singleton User
            User.getInstance().setUser(User.getInstance().getEmail(), lastname, firstname);

            // On met a jour le label sur le fragment
            TextView tvModifyProfile = viewProfile.findViewById(R.id.tvModifyProfile);
            tvModifyProfile.setText(User.getInstance().toString() + ", modify your profile here !");
        }, error -> {
            Log.e("error ModifyProfile", error.toString());
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
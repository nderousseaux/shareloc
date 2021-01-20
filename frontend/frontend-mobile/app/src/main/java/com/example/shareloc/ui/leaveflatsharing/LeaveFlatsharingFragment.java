package com.example.shareloc.ui.leaveflatsharing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shareloc.FragWhoami;
import com.example.shareloc.MainActivity;
import com.example.shareloc.R;
import com.example.shareloc.models.Flatsharing;
import com.example.shareloc.models.User;
import com.example.shareloc.ui.home.HomeFragment;
import com.example.shareloc.volley.JsonNoResponseRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.shareloc.Api.SERVER_URL;

public class LeaveFlatsharingFragment extends Fragment {

    private View viewLeaveFlatsharing;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewLeaveFlatsharing =  inflater.inflate(R.layout.fragment_leave_flatsharing, container, false);

        TextView tvLeaveFlatsharing = viewLeaveFlatsharing.findViewById(R.id.tvLeaveFlatsharing);

        // On vérifie si une colocation est séléctionné, sinon on dit que y en a pas
        if(((MainActivity)getActivity()).getSelectedFlatsharing() != null)
        {
            tvLeaveFlatsharing.setText(User.getInstance().toString() + ", do you want to leave the flatsharing " + ((MainActivity)getActivity()).getSelectedFlatsharing().getName() + " ?");

            // On affiche le layout
            viewLeaveFlatsharing.findViewById(R.id.layoutLeaveFlatsharing).setVisibility(View.VISIBLE);

            viewLeaveFlatsharing.findViewById(R.id.btnLeaveFlatsharing).setOnClickListener(view -> clickLeaveFlatsharing());

            viewLeaveFlatsharing.findViewById(R.id.btnDeleteFlatsharing).setOnClickListener(view -> clickDeleteFlatsharing());
        }
        else tvLeaveFlatsharing.setText("No flatsharing found, you can't leave one");

        return viewLeaveFlatsharing;
    }

    // Quitte la colocation séléctionné sur l'accueil
    public void clickLeaveFlatsharing() {
        String token = ((MainActivity)getActivity()).getToken();
        Flatsharing flatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing();
        String url = SERVER_URL + "colocation/" + flatsharing.getId() + "/quit";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.POST, url, null, response -> {
            Log.e("success LeaveFlatsharing", response == null ? "null" : response.toString());
            Toast.makeText(getContext(), "You left the flatsharing " + flatsharing.getName(), Toast.LENGTH_SHORT).show();

            // On supprime la collocation de l'activité
            ((MainActivity)getActivity()).setSelectedFlatsharing(null);

            // On redirige sur l'accueil
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new HomeFragment());
            transaction.commit();
        }, error -> {
            Log.e("error LeaveFlatsharing", error.toString());
            Toast.makeText(getContext(), "You can't leave the flatsharing", Toast.LENGTH_SHORT).show();
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

    // Supprime la colocation séléctionné sur l'accueil
    public void clickDeleteFlatsharing() {
        String token = ((MainActivity)getActivity()).getToken();
        Flatsharing flatsharing = ((MainActivity)getActivity()).getSelectedFlatsharing();
        String url = SERVER_URL + "colocation/" + flatsharing.getId();

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest request = new JsonNoResponseRequest(Request.Method.DELETE, url, null, response -> {
            Log.e("success DeleteFlatsharing", response == null ? "null" : response.toString());
            Toast.makeText(getContext(), "You deleted the flatsharing " + flatsharing.getName(), Toast.LENGTH_SHORT).show();

            // On supprime la collocation de l'activité
            ((MainActivity)getActivity()).setSelectedFlatsharing(null);

            // On redirige sur l'accueil
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new HomeFragment());
            transaction.commit();
        }, error -> {
            Log.e("error DeleteFlatsharing", error.toString());
            Toast.makeText(getContext(), "You can't delete the flatsharing", Toast.LENGTH_SHORT).show();
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
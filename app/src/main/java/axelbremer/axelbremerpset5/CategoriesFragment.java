package axelbremer.axelbremerpset5;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends ListFragment {

    List list = new ArrayList();
    List<Dish> menu = new ArrayList<>();
    List categoryList = new ArrayList();
    ArrayAdapter adapter;
    RequestQueue queue;
    MyGlobals myGlob;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        MenuFragment menuFragment = new MenuFragment();

        String cat = (String)list.get(position);

        Bundle args = new Bundle();
        args.putString("category", cat);
        menuFragment.setArguments(args);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myGlob = new MyGlobals(getActivity().getApplicationContext());

        queue = Volley.newRequestQueue(getActivity());

        String newUrl = "https://resto.mprog.nl/menu";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        menu = myGlob.createMenu(response);
                        Log.d("RESPONSE", "onResponse: " + response);

                        for(int i = 0; i < menu.size(); i++) {
                            Dish temp = menu.get(i);
                            if (!categoryList.contains(temp.getCategory())) {
                                categoryList.add(temp.getCategory());
                            }
                        }

                        list = categoryList;

                        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);

                        updateListView();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SHIT", "onErrorResponse: wrong");
            }
        });
        queue.add(stringRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        return view;
    }

    private void updateListView() {
        this.setListAdapter(adapter);
    }

}

package axelbremer.axelbremerpset5;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class MenuFragment extends ListFragment {

    List list = new ArrayList();
    List<Dish> menu = new ArrayList<>();
    List<Dish> catMenu = new ArrayList<>();
    String category;
    ArrayAdapter adapter;
    RequestQueue queue;
    MyGlobals myGlob;
    RestoDatabase db;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = this.getArguments();

        category = arguments.getString("category");

        Log.d("MENUFRAG", "category: " + category);

        db = RestoDatabase.getInstance(getContext());

        myGlob = new MyGlobals(getActivity().getApplicationContext());

        queue = Volley.newRequestQueue(getActivity());

        String newUrl = "https://resto.mprog.nl/menu";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        menu = myGlob.createMenu(response);
                        
                        for(int i = 0; i < menu.size(); i++) {
                            Dish temp = menu.get(i);
                            Log.d("MENU", "dish: " + temp.getName());
                            if(temp.getCategory().equals(category)) {
                                catMenu.add(temp);
                            }
                        }

                        for(int i = 0; i < catMenu.size(); i++) {
                            Dish temp = catMenu.get(i);
                            Log.d("CATMENU", "dishInCat: " + temp.getName());
                            list.add(temp.getName());
                        }

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String name = (String) list.get(position);

        Dish dish = getDishByName(name);
        Double price = dish.getPrice();
        String url = dish.getUrl();


        db.addItem(name, price, url);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        OrderFragment fragment = new OrderFragment();
        fragment.show(ft, "dialog");
    }

    private Dish getDishByName(String name) {
        Dish dish = new Dish();

        for(int i = 0; i < menu.size(); i++) {
            Dish temp = menu.get(i);
            if(temp.getName().equals(name)) {
                dish = temp;
            }
        }

        return dish;
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

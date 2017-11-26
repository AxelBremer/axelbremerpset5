package axelbremer.axelbremerpset5;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment {
    ListView orderListView;
    RestoDatabase db;
    RestoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        orderListView = view.findViewById(R.id.orderListView);

        db = RestoDatabase.getInstance(getActivity().getApplicationContext());

        adapter = new RestoAdapter(getActivity().getApplicationContext(), db.selectAll());

        orderListView.setAdapter(adapter);

        return view;
    }
}

package axelbremer.axelbremerpset5;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Axel Bremer on 26-11-2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {
    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_order, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int nameIndex = cursor.getColumnIndex("name");
        int priceIndex = cursor.getColumnIndex("price");
        int amountIndex = cursor.getColumnIndex("amount");
        int urlIndex = cursor.getColumnIndex("url");

        String name = cursor.getString(nameIndex);
        Double price = cursor.getDouble(priceIndex);
        int amount = cursor.getInt(amountIndex);

        Log.d("RESTOADAPT", "name: " + name + " price: " + Double.toString(price) + " amount: " + Integer.toString(amount));

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        TextView amountTextView = view.findViewById(R.id.amountTextView);

        nameTextView.setText(name);
        priceTextView.setText(Double.toString(price));
        amountTextView.setText(Integer.toString(amount));
    }
}

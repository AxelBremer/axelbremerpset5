package axelbremer.axelbremerpset5;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axel Bremer on 16-11-2017.
 */

public class MyGlobals {
    Context mContext;

    // constructor
    public MyGlobals(Context context){
        this.mContext = context;
    }

    public List<Dish> createMenu(String response) {
        List<Dish> menu = new ArrayList<>();

        Log.d("menu", "createMenu: ");
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray("items");

            for(int i = 0; i < arr.length(); i++) {
                JSONObject dish = arr.getJSONObject(i);
                String name = dish.getString("name");
                Log.d("DISH", name);
                String cat = dish.getString("category");
                Log.d("DISH", cat);
                String desc = dish.getString("description");
                Log.d("DISH", desc);
                String url = dish.getString("image_url");
                Log.d("DISH", url);
                Double price = dish.getDouble("price");
                Integer id = dish.getInt("id");
                Dish newDish = new Dish(name, cat, desc, url, id, price);
                menu.add(newDish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menu;
    }
}

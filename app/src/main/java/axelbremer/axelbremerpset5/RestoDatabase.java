package axelbremer.axelbremerpset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Axel Bremer on 26-11-2017.
 */

public class RestoDatabase extends SQLiteOpenHelper{
    private static RestoDatabase instance;

    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void delete(long id) {
        SQLiteDatabase db = instance.getWritableDatabase();
        db.delete("order", "_id = " + id, new String[] {});
    }

    public void insert(String title, int completed) {
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("completed", completed);
        db.insert("order", null, cv);
    }

    public void update(long id, int completed) {
        Log.d("UPDATE", "update: " + id);
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("completed", completed);
        db.update("todos", cv, "_id = " + id, new String[] {});
    }

    public static RestoDatabase getInstance(Context context) {
        if(instance == null) {
            instance = new RestoDatabase(context, "name", null,1);
        }
        return instance;
    }

    public Cursor selectAll() {
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM 'order'", new String[] {});
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DATABASE", "onCreate: ");
        sqLiteDatabase.execSQL("create table 'order' (_id INTEGER PRIMARY KEY, name TEXT, price DOUBLE, amount INTEGER, url TEXT);");
        sqLiteDatabase.execSQL("INSERT INTO 'order' (name, price, amount, url) VALUES ('Spaghetti and Meatballs', 9.0, 1, 'https://resto.mprog.nl/images/spaghetti.jpg')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "todos");
    }

    public void clear() {
        SQLiteDatabase db = instance.getWritableDatabase();
        db.execSQL("DELETE FROM 'order'");
    }

    public void addItem(String name, double price, String url) {
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM 'order' WHERE name = '" + name + "'", new String[] {});

        if(cursor.getCount() <= 0) {
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("price", price);
            cv.put("amount", 1);
            cv.put("url", url);
            db.insert("'order'", null, cv);
        } else {
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("_id");
            int amountIndex = cursor.getColumnIndex("amount");
            int id = cursor.getInt(idIndex);
            int amount = cursor.getInt(amountIndex);
            ContentValues cv = new ContentValues();
            cv.put("amount", amount + 1);
            db.update("'order'", cv, "_id = " + id, new String[] {});
        }
    }
}

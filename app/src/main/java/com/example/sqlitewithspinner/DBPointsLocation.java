package com.example.sqlitewithspinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBPointsLocation extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "db.db";
    // Table name
    private static final String TABLE_NAME = "pointLocation";
    // Table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_ADRESSE = "adresse";
    private static final String COLUMN_TEL = "tel";
    private static final String COLUMN_VILLE = "ville";

    public DBPointsLocation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the table
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOM + " TEXT," + COLUMN_ADRESSE + " TEXT," + COLUMN_TEL + " TEXT," + COLUMN_VILLE + " TEXT)";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addPointsLocations() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        addLocation(db, cv, "Bab Chaaba", "adrBabChaaba", "+2125428741", "Safi");
        addLocation(db, cv, "My Youssef", "adrMyYoussef", "+2125428741", "Safi");
        addLocation(db, cv, "Sidi Bouzid", "adrSidiBouzid", "+2125428741", "Safi");
        addLocation(db, cv, "Koutoubia", "adrKoutoubia", "+2125428741", "Marrakech");
        addLocation(db, cv, "Menara", "adrMenara", "+2125428741", "Marrakech");
        addLocation(db, cv, "Bab Jdid", "adrBabJdid", "+2125428741", "Marrakech");
        addLocation(db, cv, "Bab Marrakech", "adrBabMarrakech", "+2125428741", "Casa");
        addLocation(db, cv, "Ain Diab", "adrAinDiab", "+2125428741", "Casa");
        addLocation(db, cv, "Morocco Mall", "adrMoroccoMall", "+2125428741", "Casa");

        db.close();


    }

    private void addLocation(SQLiteDatabase db, ContentValues cv, String nom, String adresse, String tel, String ville) {
        cv.put(COLUMN_NOM, nom);
        cv.put(COLUMN_ADRESSE, adresse);
        cv.put(COLUMN_TEL, tel);
        cv.put(COLUMN_VILLE, ville);
        db.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<HashMap<String, String>> getVilles() {
        ArrayList<HashMap<String, String>> villeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_VILLE + ", COUNT(*) AS nb FROM " + TABLE_NAME + " GROUP BY " + COLUMN_VILLE, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> ville = new HashMap<>();
                ville.put("ville", cursor.getString(0));
                ville.put("nb", String.valueOf(cursor.getInt(1)));
                villeList.add(ville);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return villeList;
    }

    public ArrayList<HashMap<String, String>> getPointsLocations(String ville) {
        ArrayList<HashMap<String, String>> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NOM + ", " + COLUMN_ADRESSE + ", " + COLUMN_TEL + " FROM " + TABLE_NAME + " WHERE " + COLUMN_VILLE + " = ?", new String[]{ville});
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> location = new HashMap<>();
                location.put("nom", cursor.getString(0));
                location.put("adresse", cursor.getString(1));
                location.put("tel", cursor.getString(2));
                locationList.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return locationList;
    }

    public ArrayList<HashMap<String, String>> getPLS(String v) {
        ArrayList<HashMap<String, String>> liste = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cx = db.rawQuery("select nom, adresse, tel from pointLocation where ville = ?", new String[]{v});
        cx.moveToFirst();
        HashMap<String, String> hm;
        while (!cx.isAfterLast()) {
            hm = new HashMap<>();
            hm.put("nom", cx.getString(0));
            hm.put("adresse", cx.getString(1));
            hm.put("tel", cx.getString(2));
            liste.add(hm);
            cx.moveToNext();
        }
        return liste;
    }

    public void deleteData(String ville) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_VILLE + " = ?", new String[]{ville});
        db.close();
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}

package com.example.sqlitewithspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner sp;
    ListView lv;
    DBPointsLocation mybase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);
        mybase1 = new DBPointsLocation(this);
        mybase1.addPointsLocations();
        SimpleAdapter adapter = new SimpleAdapter(this,
                mybase1.getVilles(),
                R.layout.spiner_view,
                new String[]{"ville", "nb"},
                new int[]{R.id.ville, R.id.nbVilles});
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        HashMap<String,String> list = (HashMap<String,String>) sp.getItemAtPosition(i);
        HashMap<String,String> list = (HashMap<String,String>) sp.getSelectedItem();
        String nomDeVille = list.get("ville");
        Toast.makeText(this, sp.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
        SimpleAdapter adapter2 = new SimpleAdapter(this,
                mybase1.getPointsLocations(nomDeVille),
                R.layout.list_view_layout,
                new String[]{"nom","adresse","tel"},
                new int[]{R.id.txtNom,R.id.txtAddress,R.id.txtTel});
        lv.setAdapter(adapter2);
//        mybase1.deleteData("Safi");
//        mybase1.deleteData("Casa");
//        mybase1.deleteData("Marrakech");
//        mybase1.deleteAllData();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

}


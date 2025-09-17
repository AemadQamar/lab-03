package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener, EditCityFragment.EditCityDialogListener{

    ListView cityList;
    FloatingActionButton fab;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> dataList;

    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    public void editCity(String cityName, String provinceName, String oldCityName, String oldProvinceName) {
        boolean notDone = true;
        int i = 0;
        while (notDone && i < dataList.size()) {
            if (dataList.get(i).getName().equals(oldCityName) && dataList.get(i).getProvince().equals(oldProvinceName)){
                dataList.get(i).setName(cityName);
                dataList.get(i).setProvince(provinceName);
                notDone = false;
            } else {
                i++;
            }
        }
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditCityFragment frag = new EditCityFragment();
                Bundle args = new Bundle();
                City city = dataList.get(position);
                TextView c = view.findViewById(R.id.city_text);
                TextView p = view.findViewById(R.id.province_text);
                args.putString("city", c.getText().toString());
                args.putString("province", p.getText().toString());
                frag.setArguments(args);
                frag.show(getSupportFragmentManager(), "Edit City");
            }
        });
        fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

    }
}
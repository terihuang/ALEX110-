package com.example.khor_000.testapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Khor_000 on 28/4/2016.
 */
public class ListOfFavLocations extends Activity {

    private static int count = 0;
    private TextView[] tarray = new TextView[5];
    //private ArrayAdapter<String> arrayAdapter;
    //private ArrayList<String> arrayList;
    //private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list);
/*
        tarray[0] = (TextView) findViewById(R.id.item1);
        tarray[1] = (TextView) findViewById(R.id.item2);
        tarray[2] = (TextView) findViewById(R.id.item3);
        tarray[3] = (TextView) findViewById(R.id.item4);
        tarray[4] = (TextView) findViewById(R.id.item5);
//        arrayList = new ArrayList<String>();
 //       listView = (ListView) findViewById(R.id.lv);
*/
        if (!(getIntent().getExtras().isEmpty()) && (count < 5) ) {
            tarray[count].setText(getIntent().getExtras().getString("message"));
            count++;
            //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            //listView.setAdapter(arrayAdapter);
        }
        /*
        listView = (ListView) findViewById(R.id.list_view);
        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(ListOfFavLocations.this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        */
        //arrayAdapter.notifyDataSetChanged();
    }

/*
    public static void addItem(String item){

        arrayList.add(item);

        arrayAdapter.notifyDataSetChanged();

    }
    public void add(){
        arrayList.add("A");
        arrayList.add("b");
        arrayList.add("C");
        arrayList.add("D");
        arrayAdapter.notifyDataSetChanged();
    }
    public void addItem2(String item){

        arrayList.add(item);

        arrayAdapter.notifyDataSetChanged();

    }

    public static void addItem2(){
        arrayList.add("A");
        arrayList.add("V");
        arrayList.add("C");
        arrayList.add("D");
        arrayAdapter.notifyDataSetChanged();

    }
*/
 //   public void add(String item){}
}

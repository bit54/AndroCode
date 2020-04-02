package com.example.listviewsearchview;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public SearchView mysearch;
    public ListView courseList;
    public ArrayList<String>courses=new ArrayList<String>();
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,courses);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mysearch=findViewById(R.id.mysearch);
        courseList=findViewById(R.id.courses);


        /*place data in courseList (ListVIew)*/
        courses.add("C Language");
        courses.add("C++");
        courses.add("Java");
        courses.add("Android");
        courses.add("Python");
        courses.add("C#");
        courses.add("Php");
        courses.add("CCC");
        courses.add("HTML");
        courses.add("CSS");
        courses.add("Django");
        courses.add("JavaScript");
        courses.add("ASP.NET");
        courses.add("ML");
        courses.add("AI");


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,courses);
        courseList.setAdapter(adapter);



        /*Search items*/

        mysearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (courses.contains(query))
                {
                    adapter.getFilter().filter(query);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Keyword not fount :(", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.primary_menu,menu);

        SearchView abSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.abSearchView));
        abSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (courses.contains(query))
                {
                    adapter.getFilter().filter(query);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Keyword not fount :(", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}

package com.manish.powerlift;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;

import com.manish.powerlift.adapter.MainAdapter;
import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.utils.DataConstants;
import com.manish.powerlift.utils.ExUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private static final String TAG = "MainActivity";
    MainViewModel mainViewModel;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    String mDate;
    List<Exercise> lExercises;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        mDate = ExUtils.getDateString(calendarView.getDate());
        setSupportActionBar(toolbar);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init(this, mDate);
        mainViewModel.getExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
//                Log.v(TAG, "Update observer with list" + exercises.toString());
                lExercises = exercises;
                if (lExercises!=null && lExercises.size() == 0) {
                    lExercises.add(new Exercise(-1));
                }
                mainAdapter.updateData(lExercises);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Today.class);
                intent.putExtra(DataConstants.DATE, ExUtils.getDateString(calendarView.getDate()));
                startActivity(intent);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        mainAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mainAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
        mainViewModel.setDate(ExUtils.getDateString(dayOfMonth, month, year));
    }
}

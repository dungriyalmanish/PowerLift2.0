package com.manish.powerlift;

import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.manish.powerlift.adapter.MainAdapter;
import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.utils.DataConstants;
import com.manish.powerlift.utils.ExUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private static final String TAG = "MainActivity";
    MainViewModel mainViewModel;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    String mDate, selectedDate;
    List<Exercise> lExercises;
    CalendarView calendarView;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        mDate = selectedDate = ExUtils.getDateString(calendarView.getDate());
        setSupportActionBar(toolbar);
        setProgressBar();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init(this, mDate);
        mainViewModel.getExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                lExercises = exercises;
                if (lExercises != null && lExercises.size() == 0) {
                    lExercises.add(new Exercise(-1));
                }
                mainAdapter.updateData(lExercises);
            }
        });

        mainViewModel.isChecking().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.show();
                } else {
                    progressBar.hide();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDate.compareTo(selectedDate) < 0) {
                    Toast.makeText(MainActivity.this, "You can't edit future :(", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, Today.class);
                ArrayList<Integer> exList = new ArrayList<>();
                ArrayList<String> whtList = new ArrayList<>();
                if (lExercises != null && lExercises.size() == 3) {
                    for (Exercise e : lExercises) {
                        exList.add(e.getType());
                        whtList.add(ExUtils.getWeights(e.getPart_a(), e.getPart_b()));

                    }
                } else {
                    exList = ExUtils.basicExercise();
                    whtList = ExUtils.basicWeights();
                }
                intent.putExtra(DataConstants.EXERCISES, exList);
                intent.putExtra(DataConstants.WEIGHTS, whtList);
                intent.putExtra(DataConstants.DATE, selectedDate);

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

    private void setProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Message");
        progressBar.setMessage("Updating Database");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
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
        String date = ExUtils.getDateString(dayOfMonth, month + 1, year);
        selectedDate = date;
        mainViewModel.setDate(date);


    }
}

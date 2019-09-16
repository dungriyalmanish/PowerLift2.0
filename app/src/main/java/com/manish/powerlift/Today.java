package com.manish.powerlift;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.utils.DataConstants;
import com.manish.powerlift.utils.ExUtils;

import java.util.ArrayList;

public class Today extends AppCompatActivity implements View.OnClickListener {

    TextView ex[];
    CheckBox[][] checks;
    TextInputEditText[] editText;
    Button[] btn;
    ImageButton[][] imgBtn;
    CardView[] cardView;
    int ex1 = 0, ex2 = 0, ex3 = 0;
    Exercise exercise;
    Intent intent;
    static String date;
    private TodayViewmodel tvm;
    ProgressDialog progressBar;
    ArrayList<String> whtList;
    ArrayList<Integer> exList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exercise = new Exercise();
        tvm = ViewModelProviders.of(this).get(TodayViewmodel.class);
        intent = getIntent();
        date = intent.getStringExtra(DataConstants.DATE);
        whtList = intent.getStringArrayListExtra(DataConstants.WEIGHTS);
        exList = intent.getIntegerArrayListExtra(DataConstants.EXERCISES);
        tvm.init(this, date);
        toolbar.setSubtitle(date);
        init();
        setProgressBar();
        tvm.getProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.show();
                } else {
                    progressBar.hide();
                    Toast.makeText(Today.this, "Database Updated !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Message");
        progressBar.setMessage("Updating Database");
    }

    private void init() {
        ex = new TextView[3];
        checks = new CheckBox[3][3];
        editText = new TextInputEditText[3];
        btn = new Button[3];
        imgBtn = new ImageButton[3][5];
        cardView = new CardView[3];
        ex[0] = findViewById(R.id.t_ex1_text);
        ex[1] = findViewById(R.id.t_ex2_text);
        ex[2] = findViewById(R.id.t_ex3_text);
        checks[0][0] = findViewById(R.id.t_ex1_supp);
        checks[0][1] = findViewById(R.id.t_ex1_satisfy);
        checks[0][2] = findViewById(R.id.t_ex1_repeat);
        checks[1][0] = findViewById(R.id.t_ex2_supp);
        checks[1][1] = findViewById(R.id.t_ex2_satisfy);
        checks[1][2] = findViewById(R.id.t_ex2_repeat);
        checks[2][0] = findViewById(R.id.t_ex3_supp);
        checks[2][1] = findViewById(R.id.t_ex3_satisfy);
        checks[2][2] = findViewById(R.id.t_ex3_repeat);
        editText[0] = findViewById(R.id.t_ex1_weight);
        editText[1] = findViewById(R.id.t_ex2_weight);
        editText[2] = findViewById(R.id.t_ex3_weight);
        btn[0] = findViewById(R.id.t_ex1_btn_done);
        btn[1] = findViewById(R.id.t_ex2_btn_done);
        btn[2] = findViewById(R.id.t_ex3_btn_done);
        cardView[0] = findViewById(R.id.t_ex1_cardview);
        cardView[1] = findViewById(R.id.t_ex2_cardview);
        cardView[2] = findViewById(R.id.t_ex3_cardview);
        imgBtn[0][0] = findViewById(R.id.t_ex1_set1);
        imgBtn[0][1] = findViewById(R.id.t_ex1_set2);
        imgBtn[0][2] = findViewById(R.id.t_ex1_s3);
        imgBtn[0][3] = findViewById(R.id.t_ex1_s4);
        imgBtn[0][4] = findViewById(R.id.t_ex1_s5);
        imgBtn[1][0] = findViewById(R.id.t_ex2_set1);
        imgBtn[1][1] = findViewById(R.id.t_ex2_set2);
        imgBtn[1][2] = findViewById(R.id.t_ex2_s3);
        imgBtn[1][3] = findViewById(R.id.t_ex2_s4);
        imgBtn[1][4] = findViewById(R.id.t_ex2_s5);
        imgBtn[2][0] = findViewById(R.id.t_ex3_set1);
        imgBtn[2][1] = findViewById(R.id.t_ex3_set2);
        imgBtn[2][2] = findViewById(R.id.t_ex3_s3);
        imgBtn[2][3] = findViewById(R.id.t_ex3_s4);
        imgBtn[2][4] = findViewById(R.id.t_ex3_s5);
        ex[0].setOnClickListener(this);
        ex[1].setOnClickListener(this);
        ex[2].setOnClickListener(this);
        checks[0][0].setOnClickListener(this);
        checks[0][1].setOnClickListener(this);
        checks[0][2].setOnClickListener(this);
        checks[1][0].setOnClickListener(this);
        checks[1][1].setOnClickListener(this);
        checks[1][2].setOnClickListener(this);
        checks[2][0].setOnClickListener(this);
        checks[2][1].setOnClickListener(this);
        checks[2][2].setOnClickListener(this);
        editText[0].setOnClickListener(this);
        editText[1].setOnClickListener(this);
        editText[2].setOnClickListener(this);
        btn[0].setOnClickListener(this);
        btn[1].setOnClickListener(this);
        btn[2].setOnClickListener(this);
        cardView[0].setOnClickListener(this);
        cardView[1].setOnClickListener(this);
        cardView[2].setOnClickListener(this);
        imgBtn[0][0].setOnClickListener(this);
        imgBtn[0][1].setOnClickListener(this);
        imgBtn[0][2].setOnClickListener(this);
        imgBtn[0][3].setOnClickListener(this);
        imgBtn[0][4].setOnClickListener(this);
        imgBtn[1][0].setOnClickListener(this);
        imgBtn[1][1].setOnClickListener(this);
        imgBtn[1][2].setOnClickListener(this);
        imgBtn[1][3].setOnClickListener(this);
        imgBtn[1][4].setOnClickListener(this);
        imgBtn[2][0].setOnClickListener(this);
        imgBtn[2][1].setOnClickListener(this);
        imgBtn[2][2].setOnClickListener(this);
        imgBtn[2][3].setOnClickListener(this);
        imgBtn[2][4].setOnClickListener(this);
        updateExercises();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.t_ex1_cardview:
                break;
            case R.id.t_ex1_text:
                break;
            case R.id.t_ex1_ll:
                break;
            case R.id.t_ex1_set1:
            case R.id.t_ex1_set2:
            case R.id.t_ex1_s3:
            case R.id.t_ex1_s4:
            case R.id.t_ex1_s5:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.done);
                //v.setActivated(false);
                if (++ex1 == 5) {
                    btn[0].setEnabled(true);
                }
                break;
            case R.id.t_ex1_supp:
                break;
            case R.id.t_ex1_satisfy:
                break;
            case R.id.t_ex1_repeat:
                break;
            case R.id.t_ex1_textview1:
                break;
            case R.id.t_ex1_weight:
                break;
            case R.id.t_ex1_btn_done:
                updateData(0);
                break;
            case R.id.t_ex2_cardview:
                break;
            case R.id.t_ex2_text:
                break;
            case R.id.t_ex2_ll:
                break;
            case R.id.t_ex2_set1:
            case R.id.t_ex2_set2:
            case R.id.t_ex2_s3:
            case R.id.t_ex2_s4:
            case R.id.t_ex2_s5:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.done);
                if (++ex2 == 5) {
                    btn[1].setEnabled(true);
                }
                break;
            case R.id.t_ex2_supp:
                break;
            case R.id.t_ex2_satisfy:
                break;
            case R.id.t_ex2_repeat:
                break;
            case R.id.t_ex2_textview1:
                break;
            case R.id.t_ex2_weight:
                break;
            case R.id.t_ex2_btn_done:
                updateData(1);
                break;
            case R.id.t_ex3_cardview:
                break;
            case R.id.t_ex3_text:
                break;
            case R.id.t_ex3_ll:
                break;
            case R.id.t_ex3_set1:
            case R.id.t_ex3_set2:
            case R.id.t_ex3_s3:
            case R.id.t_ex3_s4:
            case R.id.t_ex3_s5:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.done);
                if (++ex3 == 5) {
                    btn[2].setEnabled(true);
                }
                break;
            case R.id.t_ex3_supp:
                break;
            case R.id.t_ex3_satisfy:
                break;
            case R.id.t_ex3_repeat:
                break;
            case R.id.t_ex3_textview1:
                break;
            case R.id.t_ex3_weight:
                break;
            case R.id.t_ex3_btn_done:
                updateData(2);
                break;
        }
    }

    private void updateData(int ex) {
        int type = ExUtils.getExerciseType(this.ex[ex].getText().toString());
        exercise.setDate(date);
        exercise.setType(type);
        exercise.setKey(date, type);
        exercise.setSupport(checks[ex][0].isChecked());
        exercise.setSatisfied(checks[ex][1].isChecked());
        exercise.setRepeat(checks[ex][2].isChecked());
        float[] parts = ExUtils.getParts(editText[ex].getText().toString());
        if (parts[0] < 0) {
            Snackbar.make(cardView[0], "Weights text is in incorrect format. Use (12+10)kg or 15kg syntax.", Snackbar.LENGTH_LONG).show();
            return;
        }
        exercise.setPart_a(parts[0]);
        exercise.setPart_b(parts[1]);
        tvm.updateDB(ex, exercise);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_today, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.swap:
                if (exList.contains(DataConstants.OP_EX)) {
                    exList.clear();
                    exList.add(DataConstants.SQ_EX);
                    exList.add(DataConstants.BP_EX);
                    exList.add(DataConstants.BR_EX);
                } else {
                    exList.clear();
                    exList.add(DataConstants.SQ_EX);
                    exList.add(DataConstants.OP_EX);
                    exList.add(DataConstants.DL_EX);
                }
                updateExercises();
                break;
            default:
                break;
        }
        return true;
    }

    private void updateExercises() {
        for (int i = 0; i < exList.size(); i++) {
            ex[i].setText(ExUtils.getExercise(exList.get(i)));
        }
        for (int i = 0; i < whtList.size(); i++) {
            editText[i].setText(whtList.get(i));
        }
        Toast.makeText(this, "Set Changed", Toast.LENGTH_SHORT).show();
    }
}


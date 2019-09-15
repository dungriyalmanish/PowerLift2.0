package com.manish.powerlift.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.manish.powerlift.R;
import com.manish.powerlift.db.Exercise;
import com.manish.powerlift.utils.DataConstants;
import com.manish.powerlift.utils.ExUtils;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final String TAG = "MainAdapter";
    private List<Exercise> mExercise = null;
    private Context mContext;

    public MainAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.workout_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.v(TAG, "ViewHolder updating views");
        if (viewHolder != null) {
            Exercise e = mExercise.get(i);
            if (e.getType() != DataConstants.NO_DATA) {
                viewHolder.name.setText(ExUtils.getExercise(e.getType()));
                viewHolder.p1.setText(ExUtils.getWeights(e.getPart_a(), e.getPart_b()));
                viewHolder.p2.setText(ExUtils.getSupportString(e.isSupport()));
                viewHolder.p3.setText(ExUtils.getSatisfiedString(e.isSatisfied()));
                viewHolder.p4.setText(ExUtils.getRepeatString(e.isRepeat()));
            }else{
                viewHolder.name.setText(DataConstants.DEAD);
                viewHolder.p1.setText(DataConstants.DEAD);
                viewHolder.p2.setText(DataConstants.DEAD);
                viewHolder.p3.setText(DataConstants.DEAD);
                viewHolder.p4.setText(DataConstants.DEAD);
            }
        } else {
            Log.v(TAG, "ViewHolder is null");
        }
    }

    @Override
    public int getItemCount() {
        if (mExercise != null)
            Log.v(TAG, "list size = " + mExercise.size());
        else
            Log.v(TAG, "exercise is null in adapter");
        return (mExercise == null) ? 0 : mExercise.size();
    }

    public void updateData(List<Exercise> exercises) {
        Log.v(TAG, "Updating exercises notify");
        mExercise = exercises;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, p1, p2, p3, p4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ex1_text);
            p1 = itemView.findViewById(R.id.ex1_p1);
            p2 = itemView.findViewById(R.id.ex1_p2);
            p3 = itemView.findViewById(R.id.ex1_p3);
            p4 = itemView.findViewById(R.id.ex1_p4);
        }
    }
}

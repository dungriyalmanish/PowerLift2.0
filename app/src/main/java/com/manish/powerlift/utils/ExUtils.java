package com.manish.powerlift.utils;

import android.util.Log;

import com.manish.powerlift.db.Exercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExUtils {
    private final static String TAG = "ExUtils";

    public static String getExercise(int type) {
        String ret;
        switch (type) {
            case DataConstants.SQ_EX:
                ret = DataConstants.SQ;
                break;
            case DataConstants.BP_EX:
                ret = DataConstants.BP;
                break;
            case DataConstants.BR_EX:
                ret = DataConstants.BR;
                break;
            case DataConstants.DL_EX:
                ret = DataConstants.DL;
                break;
            case DataConstants.OP_EX:
                ret = DataConstants.OP;
                break;
            default:
                ret = DataConstants.DEAD;
        }
        return ret;
    }

    public static String getWeights(float parta, float partb) {
        if (parta == partb) {
            return parta + "kg";
        } else {
            return "(" + parta + "+" + partb + ")kg";
        }
    }

    public static String getSupportString(boolean b) {
        return b ? DataConstants.SUPPORTED : DataConstants.NOT_SUPPORTED;
    }

    public static String getRepeatString(boolean b) {
        return b ? DataConstants.REPEAT : DataConstants.NOT_REPEAT;
    }

    public static String getSatisfiedString(boolean b) {
        return b ? DataConstants.SATISFIED : DataConstants.NOT_SATISFIED;
    }

    public static String getDateString(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
        return simpleDateFormat.format(new Date(date));
    }

    public static String getDateString(int dd, int mm, int yyyy) {
        StringBuilder sb = new StringBuilder();
        sb.append((dd < 10) ? "0" + dd : dd);
        sb.append((mm < 10) ? "0" + mm : mm);
        sb.append(yyyy);
        return sb.toString();
    }

    public static float[] getParts(String parts) {
        float[] f = new float[2];
        String sb;
        try {
            sb = parts.substring(parts.indexOf("(") + 1, parts.indexOf(")"));
            Log.v(TAG, "String found=" + sb);
            String[] s = sb.split("\\+");
            Log.v(TAG, "String array found=" + s);
            f[0] = Float.valueOf(s[0]);
            f[1] = Float.valueOf(s[1]);
        } catch (IndexOutOfBoundsException e) {
            try {
                sb = parts.substring(0, parts.indexOf("kg"));
                f[0] = Float.valueOf(sb);
                f[1] = f[0];
            } catch (Exception e1) {
                f[0] = -1.0f;
            }

        }
        return f;
    }

    public static int getExerciseType(String s) {
        int ret;
        switch (s) {
            case DataConstants.BP:
                ret = DataConstants.BP_EX;
                break;
            case DataConstants.BR:
                ret = DataConstants.BR_EX;
                break;
            case DataConstants.DL:
                ret = DataConstants.DL_EX;
                break;
            case DataConstants.OP:
                ret = DataConstants.OP_EX;
                break;
            case DataConstants.SQ:
                ret = DataConstants.SQ_EX;
                break;
            default:
                ret = DataConstants.BP_EX;
                break;

        }
        return ret;
    }

    public static List<Exercise> getNextToDo(List<Exercise> list) {
        List<Exercise> retList = new ArrayList<>();
        for (Exercise e : list) {
            if (e.isRepeat()) {
                retList.add(e);
                continue;
            }
            float[] w = new float[2];
            w[0] = e.getPart_a();
            w[1] = e.getPart_b();
            if (w[0] != w[1]) {
                e.setPart_a(w[0]);
                e.setPart_b(w[0]);
            } else {
                e.setPart_a(w[0] + 2.5f);
                e.setPart_b(w[0]);
            }
            retList.add(e);
        }
        return retList;
    }

    public static ArrayList<Integer> basicExercise() {
        //hardcoded
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(DataConstants.SQ_EX);
        arrayList.add(DataConstants.OP_EX);
        arrayList.add(DataConstants.DL_EX);
        return arrayList;
    }

    public static ArrayList<String> basicWeights() {
//hardcoded
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("10kg");
        arrayList.add("10kg");
        arrayList.add("10kg");
        return arrayList;
    }
}

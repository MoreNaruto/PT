package tmosq.com.pt.helper;


import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.model.Exercise;

public class ExerciseSplitter {

    protected List<Exercise> exercises;
    private Context context;

    public ExerciseSplitter(Context context) {
        this.context = context;
        exercises = new ArrayList<>();
    }

    public void generateAllExercises() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.exercises);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        Type listType = new TypeToken<ArrayList<Exercise>>() {}.getType();
        exercises = new Gson().fromJson(bufferedReader, listType);
    }
}

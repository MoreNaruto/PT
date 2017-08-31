package tmosq.com.pt.helper;


import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkOutType;

public class ExerciseSplitter {
    public static final String WORK_OUT_REGIMENT = "workoutRegiment";
    public static final String WORK_OUT_LENGTH = "workoutLength";
    public static final String WORK_OUT_DIFFICULTY = "workoutDifficulty";
    public static final String LIST_OF_EXCLUDED_EQUIPMENT= "listOfExcludedEquipment";
    public static final String LIST_OF_ACTIVE_BODY_FOCUSES = "listOfActiveBodyFocuses";
    public static final String HAS_PARTNER = "hasPartner";

    private Context context;

    public ExerciseSplitter(Context context) {
        this.context = context;
    }

    public List<Exercise> generateAllExercises() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.exercises);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Type listType = new TypeToken<ArrayList<Exercise>>() {
        }.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BodyFocus.class, new BodyFocus.BodyFocusDeserializer());
        gsonBuilder.registerTypeAdapter(Difficulty.class, new Difficulty.DifficultyDeserializer());
        gsonBuilder.registerTypeAdapter(Equipment.class, new Equipment.EquipmentDeserializer());
        gsonBuilder.registerTypeAdapter(WorkOutType.class, new WorkOutType.WorkOutTypesDeserializer());

        Gson gson = gsonBuilder.create();

        return gson.fromJson(bufferedReader, listType);
    }
}

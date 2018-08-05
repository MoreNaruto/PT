package tmosq.com.pt.viewModel;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.util.ExerciseFilter;

import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;

public class TabataViewModel {

    public static final int LENGTH_OF_TABATA_SET = 4;
    private final ExerciseFilter exerciseFilter;
    List<Exercise> filteredExercises;
    private Intent intent;
    private Random random = new Random();
    private int workoutLength;

    public TabataViewModel(Context context, Intent intent) {
        this.intent = intent;

        exerciseFilter = new ExerciseFilter(intent);
        filteredExercises = exerciseFilter.filterExercises(ExerciseSplitter.generateAllExercises(context));
    }

    public List<Exercise> getTabataWorkouts() {
        workoutLength = intent.getIntExtra(WORK_OUT_LENGTH, 16);
        return createTabataExercises(workoutLength / LENGTH_OF_TABATA_SET);
    }

    private List<Exercise> createTabataExercises(int numberOfExpectedExercises) {
        List<Exercise> tabataExercises = new ArrayList<>();
        for (int exerciseIndex = 0; exerciseIndex < numberOfExpectedExercises; exerciseIndex++) {
            int filteredExerciseIndex = random.nextInt(filteredExercises.size());
            tabataExercises.add(filteredExercises.get(filteredExerciseIndex));
            filteredExercises.remove(filteredExerciseIndex);
        }
        return tabataExercises;
    }
}

package tmosq.com.pt.viewModel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import tmosq.com.pt.model.Exercise;

/**
 * Created by tmosq on 11/5/17.
 */

public class MainWorkoutExerciseViewModel {

    public ObservableField<String> exerciseRegiment = new ObservableField<>("");
    public ObservableField<String> exerciseDifficulty = new ObservableField<>("");
    public ObservableField<String> exerciseWorkout = new ObservableField<>("");

    public void setExercise(Exercise exercise) {
        exerciseWorkout.set(exercise.getWorkout());

        if (exercise.getForTime() && exercise.getAlternateSide()){
            exerciseRegiment.set("3 sets: 30 seconds for each rep (15 seconds each side)");
        } else if(exercise.getForTime() && !exercise.getAlternateSide()){
            exerciseRegiment.set("3 sets: 30 seconds for each rep");
        } else if(!exercise.getForTime() && exercise.getAlternateSide()){
            exerciseRegiment.set("3 sets: 10 reps (5 each side)");
        } else {
            exerciseRegiment.set("3 sets: 10 reps");
        }

        exerciseDifficulty.set("Difficulty: " + exercise.getDifficulty().getDifficultyNameAlias());
    }
}

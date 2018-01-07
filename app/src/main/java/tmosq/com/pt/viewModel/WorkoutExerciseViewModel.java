package tmosq.com.pt.viewModel;

import android.databinding.ObservableField;

import tmosq.com.pt.model.Exercise;

public class WorkoutExerciseViewModel {

    public ObservableField<String> exerciseRegiment = new ObservableField<>("");
    public ObservableField<String> exerciseDifficulty = new ObservableField<>("");
    public ObservableField<String> exerciseWorkout = new ObservableField<>("");

    public void setExercise(Exercise exercise, boolean isMainWorkout) {
        exerciseWorkout.set(exercise.getWorkout());

        if (exercise.getForTime() && exercise.getAlternateSide()) {
            exerciseRegiment.set(isMainWorkout ?
                    "3 sets: 30 seconds for each rep (15 seconds each side)" :
                    "2 sets: 30 seconds for each rep (15 seconds each side)");
        } else if (exercise.getForTime() && !exercise.getAlternateSide()) {
            exerciseRegiment.set(isMainWorkout ?
                    "3 sets: 30 seconds for each rep" :
                    "2 sets: 30 seconds for each rep");
        } else if (!exercise.getForTime() && exercise.getAlternateSide()) {
            exerciseRegiment.set(isMainWorkout ?
                    "3 sets: 10 reps (5 each side)" :
                    "2 sets: 10 reps (5 each side)");
        } else {
            exerciseRegiment.set(isMainWorkout ?
                    "3 sets: 10 reps" :
                    "2 sets: 10 reps");
        }

        exerciseDifficulty.set("Difficulty: " + exercise.getDifficulty().getDifficultyNameAlias());
    }
}

package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.databinding.ObservableField;

import tmosq.com.pt.activity.WorkoutDetailActivity;

import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT;
import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT_DESCRIPTION;

public class WorkoutDetailViewModel {
    private final Intent intent;
    public ObservableField<String> exerciseWorkout = new ObservableField<>("");
    public ObservableField<String> workoutDescription = new ObservableField<>("");

    public WorkoutDetailViewModel(WorkoutDetailActivity workoutDetailActivity) {
        intent = workoutDetailActivity.getIntent();

    }

    public void populateWorkoutDetail() {
        exerciseWorkout.set(intent.getStringExtra(WORKOUT));
        workoutDescription.set(intent.getStringExtra(WORKOUT_DESCRIPTION));
    }
}

package tmosq.com.pt.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityWorkoutDetailBinding;
import tmosq.com.pt.viewModel.WorkoutDetailViewModel;

public class WorkoutDetailActivity extends AppCompatActivity {
    public static final String WORKOUT = "workout";
    public static final String WORKOUT_DESCRIPTION = "workout_description";

    protected ActivityWorkoutDetailBinding binding;
    protected WorkoutDetailViewModel workoutDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_workout_detail);
        workoutDetailViewModel = new WorkoutDetailViewModel(this);
        binding.setViewModel(workoutDetailViewModel);

        setContentView(binding.getRoot());
        workoutDetailViewModel.populateWorkoutDetail();
    }
}

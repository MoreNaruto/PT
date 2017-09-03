package tmosq.com.pt.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import butterknife.ButterKnife;
import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityWorkoutBinding;
import tmosq.com.pt.viewModel.WorkoutViewModel;

public class WorkoutActivity extends Activity {

    protected ActivityWorkoutBinding binding;
    protected WorkoutViewModel workoutViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_workout);
        workoutViewModel = new WorkoutViewModel(this);
        binding.setViewModel(workoutViewModel);

        setContentView(binding.getRoot());
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

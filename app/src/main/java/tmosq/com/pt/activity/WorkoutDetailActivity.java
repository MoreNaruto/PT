package tmosq.com.pt.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityWorkoutDetailBinding;

public class WorkoutDetailActivity extends BaseActivity {
    public static final String WORKOUT = "workout";
    public static final String WORKOUT_DESCRIPTION = "workout_description";

    protected ActivityWorkoutDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_workout_detail);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.workoutTitle.setText(getIntent().getStringExtra(WORKOUT));
        binding.workoutDescription.setText(getIntent().getStringExtra(WORKOUT_DESCRIPTION));
    }
}

package tmosq.com.pt.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityWorkoutRegimentBinding;
import tmosq.com.pt.fragment.WorkoutRegimentGridFragment;

public class WorkOutRegimentActivity extends BaseActivity {
    protected ActivityWorkoutRegimentBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_workout_regiment);
        setContentView(binding.getRoot());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.workout_regiment_frame_id, new WorkoutRegimentGridFragment(), "workout_regiment_fragment");
        fragmentTransaction.commit();
    }
}

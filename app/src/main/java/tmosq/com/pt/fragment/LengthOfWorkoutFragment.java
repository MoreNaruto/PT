package tmosq.com.pt.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.LengthOfWorkoutFragmentBinding;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

public class LengthOfWorkoutFragment extends Fragment {
    public final static String TAG = LengthOfWorkoutFragment.class.getSimpleName();
    public final static String WORKOUT_REGIMENT_KEY = "WORKOUT_REGIMENT_KEY";
    public static final int MAX_VALUE = 120;
    public static final int MIN_VALUE = 0;

    public LengthOfWorkoutFragmentBinding binding;

    public static LengthOfWorkoutFragment newInstance(String workoutRegiment) {
        LengthOfWorkoutFragment fragment = new LengthOfWorkoutFragment();

        Bundle args = new Bundle();
        args.putString(WORKOUT_REGIMENT_KEY, workoutRegiment);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.length_of_workout_fragment, container, false);
        WorkoutRegiment workoutRegiment = WorkoutRegiment.valueOfWorkoutRegimentTitle(getArguments().getString(WORKOUT_REGIMENT_KEY));
        binding.workoutNumberPicker.setMaxValue(MAX_VALUE);
        binding.workoutNumberPicker.setMinValue(MIN_VALUE);
        binding.workoutNumberPicker.setWrapSelectorWheel(true);
        binding.workoutNumberPicker.setValue(workoutRegiment.getMinimumWorkoutLength());

        return binding.getRoot();
    }

    public int getLengthOfWorkout() {
        return binding.workoutNumberPicker.getValue();
    }
}

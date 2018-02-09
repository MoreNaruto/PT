package tmosq.com.pt.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.fragment.DifficultyFragment;
import tmosq.com.pt.fragment.EquipmentFragment;
import tmosq.com.pt.fragment.FocalBodyFocusFragment;
import tmosq.com.pt.fragment.LengthOfWorkoutFragment;
import tmosq.com.pt.fragment.WorkoutRegimentFragment;
import tmosq.com.pt.viewModel.PreWorkOutViewModel;

import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

public class PreWorkoutActivity extends AppCompatActivity {
    protected ActivityPreWorkoutBinding binding;
    protected PreWorkOutViewModel preWorkOutViewModel;
    protected LengthOfWorkoutFragment lengthOfWorkoutFragment;
    protected FocalBodyFocusFragment focalBodyFocusFragment;
    protected DifficultyFragment difficultyFragment;
    protected EquipmentFragment equipmentFragment;
    protected WorkoutRegimentFragment workoutRegimentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pre_workout);
        preWorkOutViewModel = new PreWorkOutViewModel();
        binding.setViewModel(preWorkOutViewModel);

        setContentView(binding.getRoot());

        lengthOfWorkoutFragment = new LengthOfWorkoutFragment();
        focalBodyFocusFragment = new FocalBodyFocusFragment();
        difficultyFragment = new DifficultyFragment();
        equipmentFragment = new EquipmentFragment();
        workoutRegimentFragment = new WorkoutRegimentFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        fragmentTransaction.add(R.id.workout_length_frame_id, lengthOfWorkoutFragment, "length_of_workout_fragment");
        fragmentTransaction.add(R.id.focal_body_point_frame_id, focalBodyFocusFragment, "focal_body_focus_fragment");
        fragmentTransaction.add(R.id.difficulty_frame_id, difficultyFragment, "difficulty_fragment");
        fragmentTransaction.add(R.id.equipment_frame_id, equipmentFragment, "equipment_fragment");
        fragmentTransaction.add(R.id.workout_regiment_frame_id, workoutRegimentFragment, "workout_regiment_fragment");
        fragmentTransaction.commit();

        preWorkOutViewModel.makeWorkoutButtonClicked.addOnPropertyChangedCallback(navigateToWorkoutActivityCallback());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private Observable.OnPropertyChangedCallback navigateToWorkoutActivityCallback() {
        final PreWorkoutActivity activity = this;
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Intent intent = new Intent(activity, WorkoutActivity.class);
                intent.putExtra(WORK_OUT_REGIMENT, workoutRegimentFragment.getWorkOutRegiment());
                intent.putExtra(WORK_OUT_LENGTH, lengthOfWorkoutFragment.getLengthOfWorkout());
                intent.putExtra(WORK_OUT_DIFFICULTY, difficultyFragment.getDifficulty());
                intent.putExtra(HAS_PARTNER, preWorkOutViewModel.getHasPartner().get());
                intent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(equipmentFragment.getExcludedEquipmentItems()));
                intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(focalBodyFocusFragment.getActiveBodyFocuses()));

                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }
        };
    }


}

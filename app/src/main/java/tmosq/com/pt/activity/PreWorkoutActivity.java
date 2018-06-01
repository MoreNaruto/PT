package tmosq.com.pt.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.gson.Gson;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.fragment.DifficultyFragment;
import tmosq.com.pt.fragment.EquipmentFragment;
import tmosq.com.pt.fragment.FocalBodyFocusFragment;
import tmosq.com.pt.fragment.LengthOfWorkoutFragment;
import tmosq.com.pt.fragment.WorkoutRegimentGridFragment;

import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

public class PreWorkoutActivity extends BaseActivity {
    protected ActivityPreWorkoutBinding binding;
    protected LengthOfWorkoutFragment lengthOfWorkoutFragment;
    protected FocalBodyFocusFragment focalBodyFocusFragment;
    protected DifficultyFragment difficultyFragment;
    protected EquipmentFragment equipmentFragment;
    protected WorkoutRegimentGridFragment workoutRegimentGridFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pre_workout);
        setContentView(binding.getRoot());

        lengthOfWorkoutFragment = LengthOfWorkoutFragment.newInstance(getIntent().getStringExtra(WORK_OUT_REGIMENT));
        focalBodyFocusFragment = new FocalBodyFocusFragment();
        difficultyFragment = new DifficultyFragment();
        equipmentFragment = new EquipmentFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.workout_length_frame, lengthOfWorkoutFragment, LengthOfWorkoutFragment.TAG);
        fragmentTransaction.add(R.id.focal_body_point_frame, focalBodyFocusFragment, FocalBodyFocusFragment.TAG);
        fragmentTransaction.add(R.id.difficulty_frame, difficultyFragment, DifficultyFragment.TAG);
        fragmentTransaction.add(R.id.equipment_frame, equipmentFragment, EquipmentFragment.TAG);
        fragmentTransaction.commit();

        final PreWorkoutActivity activity = this;

        binding.makeWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WorkoutActivity.class);
                intent.putExtra(WORK_OUT_REGIMENT, getIntent().getStringExtra(WORK_OUT_REGIMENT));
                intent.putExtra(WORK_OUT_LENGTH, lengthOfWorkoutFragment.getLengthOfWorkout());
                intent.putExtra(WORK_OUT_DIFFICULTY, difficultyFragment.getDifficulty());
                intent.putExtra(HAS_PARTNER, binding.partnerCheckbox.isChecked());
                intent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(equipmentFragment.getExcludedEquipmentItems()));
                intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(focalBodyFocusFragment.getActiveBodyFocuses()));

                activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }
        });
    }
}

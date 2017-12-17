package tmosq.com.pt.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.google.gson.Gson;

import java.util.ArrayList;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.fragment.FocalBodyFocusFragment;
import tmosq.com.pt.fragment.LengthOfWorkoutFragment;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;
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
    private LengthOfWorkoutFragment lengthOfWorkoutFragment;
    private FocalBodyFocusFragment focalBodyFocusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pre_workout);
        preWorkOutViewModel = new PreWorkOutViewModel();
        binding.setViewModel(preWorkOutViewModel);

        setContentView(binding.getRoot());

        lengthOfWorkoutFragment = new LengthOfWorkoutFragment();
        focalBodyFocusFragment = new FocalBodyFocusFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        fragmentTransaction.add(R.id.workout_length_frame_id, lengthOfWorkoutFragment, "length_of_workout_fragment");
        fragmentTransaction.add(R.id.focal_body_point_frame_id, focalBodyFocusFragment, "focal_body_focus_fragment");
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        preWorkOutViewModel.makeWorkoutButtonClicked.addOnPropertyChangedCallback(navigateToWorkoutActivityCallback());
        preWorkOutViewModel.selectAllEquipmentClicked.addOnPropertyChangedCallback(selectAllTextCallback());
        setWorkoutDifficultyDropDownMenuAdapter();
        setWorkoutRegimentDropDownMenuAdapter();
    }

    private Observable.OnPropertyChangedCallback selectAllTextCallback() {
        final PreWorkoutActivity activity = this;
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (preWorkOutViewModel.selectAllEquipmentClicked.get().equals(activity.getString(R.string.select_all))) {
                    binding.selectAllTextView.setText(activity.getString(R.string.unselect_all));
                    for (Equipment equipment : Equipment.values()) {
                        CheckBox checkBox = (CheckBox) activity.findViewById(equipment.getResourceIdCheckBox());
                        checkBox.setChecked(true);
                    }
                } else {
                    binding.selectAllTextView.setText(activity.getString(R.string.select_all));
                    for (Equipment equipment : Equipment.values()) {
                        CheckBox checkBox = (CheckBox) activity.findViewById(equipment.getResourceIdCheckBox());
                        checkBox.setChecked(false);
                    }
                }
            }
        };
    }

    private Observable.OnPropertyChangedCallback navigateToWorkoutActivityCallback() {
        final PreWorkoutActivity activity = this;
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Intent intent = new Intent(activity, WorkoutActivity.class);
                intent.putExtra(WORK_OUT_REGIMENT, preWorkOutViewModel.getWorkOutRegiment().get());
                intent.putExtra(WORK_OUT_LENGTH, lengthOfWorkoutFragment.getLengthOfWorkout());
                intent.putExtra(WORK_OUT_DIFFICULTY, preWorkOutViewModel.getWorkoutDifficulty().get());
                intent.putExtra(HAS_PARTNER, preWorkOutViewModel.getHasPartner().get());
                intent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(preWorkOutViewModel.getExcludedEquipments().get()));
                intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(focalBodyFocusFragment.getActiveBodyFocuses()));
                activity.startActivity(intent);
            }
        };
    }

    private void setWorkoutRegimentDropDownMenuAdapter() {
        final ArrayList<String> workOutRegiment = new ArrayList<>();
        for (WorkoutRegiment workoutRegiment : WorkoutRegiment.values()) {
            workOutRegiment.add(workoutRegiment.getWorkOutRegimentNameAlias());
        }

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                workOutRegiment);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.workoutRegimentDropdownMenu.setAdapter(staticAdapter);
        binding.workoutRegimentDropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preWorkOutViewModel.setWorkOutRegiment(workOutRegiment.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setWorkoutDifficultyDropDownMenuAdapter() {
        final ArrayList<String> difficultyValues = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyValues.add(difficulty.getDifficultyNameAlias());
        }

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                difficultyValues);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.workoutDifficultyDropdownMenu.setAdapter(staticAdapter);
        binding.workoutDifficultyDropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preWorkOutViewModel.setWorkOutDifficulty(difficultyValues.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

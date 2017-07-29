package tmosq.com.pt.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.viewModel.PreWorkOutViewModel;

public class PreWorkoutActivity extends Activity {
    @BindView(R.id.workout_length_dropdown_menu)
    Spinner workoutLengthDropDownMenu;

    @BindView(R.id.workout_difficulty_dropdown_menu)
    Spinner workoutDifficultyDropDownMenu;

    protected ActivityPreWorkoutBinding binding;
    protected PreWorkOutViewModel preWorkOutViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pre_workout);
        preWorkOutViewModel = new PreWorkOutViewModel(this);
        binding.setViewModel(preWorkOutViewModel);

        setContentView(binding.getRoot());
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setWorkoutLengthDropDownMenuAdapter();
        setWorkoutDifficultyDropDownMenuAdapter();
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
        workoutDifficultyDropDownMenu.setAdapter(staticAdapter);
        workoutDifficultyDropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preWorkOutViewModel.setWorkOutDifficulty(difficultyValues.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setWorkoutLengthDropDownMenuAdapter() {
        final Integer[] allottedLengthsOfTimes = {30, 45, 60, 75, 90, 105, 120};

        ArrayAdapter<Integer> staticAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                allottedLengthsOfTimes);

        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutLengthDropDownMenu.setAdapter(staticAdapter);
        workoutLengthDropDownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preWorkOutViewModel.setWorkOutLength(allottedLengthsOfTimes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

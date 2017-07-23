package tmosq.com.pt.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tmosq.com.pt.R;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.WorkOutType;

public class PreWorkoutActivity extends AppCompatActivity {
    @BindView(R.id.workout_length_dropdown_menu)
    Spinner workoutLengthDropDownMenu;

    @BindView(R.id.workout_difficulty_dropdown_menu)
    Spinner workoutDifficultyDropDownMenu;

    @BindView(R.id.abdominals_checkbox)
    CheckBox abdominalsBodyFocusCheckbox;

    @BindView(R.id.abductors_checkbox)
    CheckBox abductorsBodyFocusCheckbox;

    @BindView(R.id.adductors_checkbox)
    CheckBox adductorsBodyFocusCheckbox;

    @BindView(R.id.biceps_checkbox)
    CheckBox bicepsBodyFocusCheckbox;

    @BindView(R.id.calves_checkbox)
    CheckBox calvesBodyFocusCheckbox;

    @BindView(R.id.chest_checkbox)
    CheckBox chestBodyFocusCheckbox;

    @BindView(R.id.forearms_checkbox)
    CheckBox forearmsBodyFocusCheckbox;

    @BindView(R.id.glutes_checkbox)
    CheckBox glutesBodyFocusCheckbox;

    @BindView(R.id.hamstring_checkbox)
    CheckBox hamstringBodyFocusCheckbox;

    @BindView(R.id.lats_checkbox)
    CheckBox latsBodyFocusCheckbox;

    @BindView(R.id.lower_back_checkbox)
    CheckBox lowerBackBodyFocusCheckbox;

    @BindView(R.id.middle_back_checkbox)
    CheckBox middleBackBodyFocusCheckbox;

    @BindView(R.id.neck_checkbox)
    CheckBox neckBodyFocusCheckbox;

    @BindView(R.id.quadriceps_checkbox)
    CheckBox quadricepsBodyFocusCheckbox;

    @BindView(R.id.shoulders_checkbox)
    CheckBox shouldersBodyFocusCheckbox;

    @BindView(R.id.traps_checkbox)
    CheckBox trapsBodyFocusCheckbox;

    @BindView(R.id.triceps_checkbox)
    CheckBox tricepsBodyFocusCheckbox;

    @BindView(R.id.bands_checkbox)
    CheckBox bandsEquipmentCheckbox;

    @BindView(R.id.barbell_checkbox)
    CheckBox barbellEquipmentCheckbox;

    @BindView(R.id.bicycle_checkbox)
    CheckBox bicycleEquipmentCheckbox;

    @BindView(R.id.cable_checkbox)
    CheckBox cableEquipmentCheckbox;

    @BindView(R.id.chair_checkbox)
    CheckBox chairEquipmentCheckbox;

    @BindView(R.id.cones_checkbox)
    CheckBox conesEquipmentCheckbox;

    @BindView(R.id.dumbbell_checkbox)
    CheckBox dumbbellEquipmentCheckbox;

    @BindView(R.id.exercise_ball_checkbox)
    CheckBox exerciseBallEquipmentCheckbox;

    @BindView(R.id.ez_curl_bar_checkbox)
    CheckBox ezCurlBarEquipmentCheckbox;

    @BindView(R.id.foam_roll_checkbox)
    CheckBox foamRollEquipmentCheckbox;

    @BindView(R.id.kettlebell_checkbox)
    CheckBox kettlebellEquipmentCheckbox;

    @BindView(R.id.machine_checkbox)
    CheckBox machineEquipmentCheckbox;

    @BindView(R.id.medicine_ball_checkbox)
    CheckBox medicineBallEquipmentCheckbox;

    @BindView(R.id.plate_checkbox)
    CheckBox plateEquipmentCheckbox;

    @BindView(R.id.rickshaw_checkbox)
    CheckBox rickshawEquipmentCheckbox;

    @BindView(R.id.roller_checkbox)
    CheckBox rollerEquipmentCheckbox;

    @BindView(R.id.rope_checkbox)
    CheckBox ropeEquipmentCheckbox;

    @BindView(R.id.row_checkbox)
    CheckBox rowEquipmentCheckbox;

    @BindView(R.id.sled_checkbox)
    CheckBox sledEquipmentCheckbox;

    @BindView(R.id.stone_checkbox)
    CheckBox stoneEquipmentCheckbox;

    @BindView(R.id.toning_wheel_checkbox)
    CheckBox toningWheelEquipmentCheckbox;

    @BindView(R.id.trap_bar_checkbox)
    CheckBox trapBarEquipmentCheckbox;

    @BindView(R.id.treadmill_checkbox)
    CheckBox treadmillEquipmentCheckbox;

    @BindView(R.id.workout_box_checkbox)
    CheckBox workoutBoxEquipmentCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_workout);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setWorkoutLengthDropDownMenuAdapter();
        setWorkoutDifficultyDropDownMenuAdapter();
    }

    @OnClick(R.id.make_workout_button)
    protected void makeWorkout(){

    }

    private void setWorkoutDifficultyDropDownMenuAdapter() {
        ArrayList<String> difficultyValues = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyValues.add(difficulty.getDifficultyNameAlias());
        }

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                difficultyValues);

        workoutDifficultyDropDownMenu.setAdapter(staticAdapter);
    }

    private void setWorkoutLengthDropDownMenuAdapter() {
        Integer[] allottedLengthsOfTImes = {30, 45, 60, 75, 90, 105, 120};

        ArrayAdapter<Integer> staticAdapter = new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                allottedLengthsOfTImes);

        workoutLengthDropDownMenu.setAdapter(staticAdapter);
    }
}

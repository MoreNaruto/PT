package tmosq.com.pt.viewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

public class PreWorkOutViewModel {
    private Context context;
    private Integer workOutLength;
    private String workOutDifficulty;
    private String workOutRegiment;
    private List<String> activeBodyFocuses;
    private List<String> excludedEquipment;

    public PreWorkOutViewModel(Context context) {
        this.context = context;
        workOutRegiment = WorkoutRegiment.CARDIO.getWorkOutRegimentNameAlias();
        workOutLength = 30;
        workOutDifficulty = Difficulty.BASIC.getDifficultyNameAlias();
        activeBodyFocuses = new ArrayList<>();
        excludedEquipment = new ArrayList<>();
    }

    public void setWorkOutRegiment(String workOutRegiment) {
        this.workOutRegiment = workOutRegiment;
    }

    public void setWorkOutLength(Integer workOutLength) {
        this.workOutLength = workOutLength;
    }

    public void setWorkOutDifficulty(String workOutDifficulty) {
        this.workOutDifficulty = workOutDifficulty;
    }

    public View.OnClickListener clickBodyFocusCheckBox() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox bodyFocusCheckBox = (CheckBox) view;
                String bodyFocus = BodyFocus.fromResourceCheckBoxId(view.getId()).getBodyPartNameAlias();
                if (bodyFocusCheckBox.isChecked()) {
                    activeBodyFocuses.add(bodyFocus);
                } else {
                    activeBodyFocuses.remove(bodyFocus);
                }
            }
        };
    }

    public View.OnClickListener clickEquipmentCheckBox() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox equipmentCheckBox = (CheckBox) view;
                String equipment = Equipment.fromResourceCheckBoxId(view.getId()).getEquipmentNameAlias();
                if (equipmentCheckBox.isChecked()) {
                    excludedEquipment.add(equipment);
                } else {
                    excludedEquipment.remove(equipment);
                }
            }
        };
    }

    public View.OnClickListener clickMakeWorkOutButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkoutActivity.class);
                intent.putExtra(WORK_OUT_REGIMENT, workOutRegiment);
                intent.putExtra(WORK_OUT_LENGTH, workOutLength);
                intent.putExtra(WORK_OUT_DIFFICULTY, workOutDifficulty);
                intent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(excludedEquipment));
                intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(activeBodyFocuses));
                context.startActivity(intent);
            }
        };
    }
}

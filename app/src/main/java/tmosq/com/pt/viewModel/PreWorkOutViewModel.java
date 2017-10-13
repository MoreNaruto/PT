package tmosq.com.pt.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.activity.PreWorkoutActivity;
import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

public class PreWorkOutViewModel {
    private PreWorkoutActivity preWorkoutActivity;
    private Integer workOutLength;
    private String workOutDifficulty;
    private String workOutRegiment;
    private List<String> activeBodyFocuses;
    private List<String> excludedEquipments;
    private Boolean hasPartner;

    public PreWorkOutViewModel(PreWorkoutActivity preWorkoutActivity) {
        this.preWorkoutActivity = preWorkoutActivity;
        workOutRegiment = WorkoutRegiment.CARDIO.getWorkOutRegimentNameAlias();
        workOutLength = 30;
        workOutDifficulty = Difficulty.BASIC.getDifficultyNameAlias();
        activeBodyFocuses = new ArrayList<>();
        excludedEquipments = new ArrayList<>();
        hasPartner = false;
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
                    excludedEquipments.add(equipment);
                } else {
                    excludedEquipments.remove(equipment);
                }
            }
        };
    }

    public View.OnClickListener clickPartnerBox() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox partnerCheckBox = (CheckBox) view;
                hasPartner = partnerCheckBox.isChecked();
            }
        };
    }

    public View.OnClickListener clickMakeWorkOutButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(preWorkoutActivity, WorkoutActivity.class);
                intent.putExtra(WORK_OUT_REGIMENT, workOutRegiment);
                intent.putExtra(WORK_OUT_LENGTH, workOutLength.intValue());
                intent.putExtra(WORK_OUT_DIFFICULTY, workOutDifficulty);
                intent.putExtra(HAS_PARTNER, hasPartner);
                intent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(excludedEquipments));
                intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(activeBodyFocuses));
                preWorkoutActivity.startActivity(intent);
            }
        };
    }

    public View.OnClickListener clickSelectAllEquipmentCheckBox() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;

                if (textView.getText().equals(preWorkoutActivity.getString(R.string.select_all))) {
                    textView.setText(preWorkoutActivity.getString(R.string.unselect_all));
                    for (Equipment equipment : Equipment.values()) {
                        excludedEquipments.add(equipment.getEquipmentNameAlias());
                        CheckBox checkBox = (CheckBox) preWorkoutActivity.findViewById(equipment.getResourceIdCheckBox());
                        checkBox.setChecked(true);
                    }
                } else {
                    textView.setText(preWorkoutActivity.getString(R.string.select_all));
                    excludedEquipments.clear();
                    for (Equipment equipment : Equipment.values()) {
                        CheckBox checkBox = (CheckBox) preWorkoutActivity.findViewById(equipment.getResourceIdCheckBox());
                        checkBox.setChecked(false);
                    }
                }
            }
        };
    }
}

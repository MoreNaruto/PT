package tmosq.com.pt.viewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

public class PreWorkOutViewModel {
    @Getter
    private ObservableField<String> workoutDifficulty = new ObservableField<>(Difficulty.BASIC.getDifficultyNameAlias());
    @Getter
    private ObservableField<String> workOutRegiment = new ObservableField<>(WorkoutRegiment.CARDIO.getWorkOutRegimentNameAlias());
    @Getter
    private ObservableBoolean hasPartner = new ObservableBoolean(false);
    @Getter
    private ObservableField<List<String>> excludedEquipments = new ObservableField<>((List<String>) new ArrayList<String>());

    public BaseObservable makeWorkoutButtonClicked = new BaseObservable();
    public ObservableField<String> selectAllEquipmentClicked = new ObservableField<>("Select all");

    public PreWorkOutViewModel() {
    }

    public void setWorkOutRegiment(String workOutRegiment) {
        this.workOutRegiment.set(workOutRegiment);
    }

    public void setWorkOutDifficulty(String workOutDifficulty) {
        this.workoutDifficulty.set(workOutDifficulty);
    }

    public View.OnClickListener clickEquipmentCheckBox() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox equipmentCheckBox = (CheckBox) view;
                String equipment = Equipment.fromResourceCheckBoxId(view.getId()).getEquipmentNameAlias();
                if (equipmentCheckBox.isChecked()) {
                    excludedEquipments.get().add(equipment);
                } else {
                    excludedEquipments.get().remove(equipment);
                }
            }
        };
    }

    public View.OnClickListener clickPartnerBox() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox partnerCheckBox = (CheckBox) view;
                hasPartner.set(partnerCheckBox.isChecked());
            }
        };
    }

    public View.OnClickListener clickMakeWorkOutButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeWorkoutButtonClicked.notifyChange();
            }
        };
    }

    public View.OnClickListener clickSelectAllEquipmentCheckBox() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView selectAllTextView = (TextView) view;
                String selectAllTextViewString = selectAllTextView.getText().toString();
                if (selectAllTextViewString.equals("Select all")){
                    excludedEquipments.set(Equipment.allEquipmentNameAliases());
                } else {
                    excludedEquipments.get().clear();
                }
                selectAllEquipmentClicked.set(selectAllTextViewString);
            }
        };
    }
}

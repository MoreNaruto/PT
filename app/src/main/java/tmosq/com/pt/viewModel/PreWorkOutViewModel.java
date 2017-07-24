package tmosq.com.pt.viewModel;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Equipment;

public class PreWorkOutViewModel {
    private Context context;
    private Integer workOutLength;
    private String workOutDifficulty;
    private List<String> activeBodyFocuses;
    private List<String> excludedEquipment;

    public PreWorkOutViewModel(Context context) {
        this.context = context;
        workOutLength = 30;
        workOutDifficulty = "basic";
        activeBodyFocuses = new ArrayList<>();
        excludedEquipment = new ArrayList<>();
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

    public View.OnClickListener clickEquipmentCheckBox(){
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox equipmentCheckBox = (CheckBox) view;
                String equipment = Equipment.fromResourceCheckBoxId(view.getId()).getEquipmentNameAlias();
                if(equipmentCheckBox.isChecked()){
                    excludedEquipment.add(equipment);
                } else {
                    excludedEquipment.remove(equipment);
                }
            }
        };
    }

    public View.OnClickListener clickMakeWorkOutButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}

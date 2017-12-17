package tmosq.com.pt.viewModel;


import android.databinding.ObservableBoolean;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import tmosq.com.pt.model.exercise_support_enums.Equipment;

public class EquipmentViewModel {
    @Getter
    private List<String> excludedItems = new ArrayList<>();

    public ObservableBoolean shouldSwitchToSelectAllTextValue = new ObservableBoolean(true);

    public OnClickListener clickEquipmentCheckBox() {
        return new OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox equipmentCheckBox = (CheckBox) view;
                String bodyFocus = Equipment.fromResourceCheckBoxId(view.getId()).getEquipmentNameAlias();
                if (equipmentCheckBox.isChecked()) {
                    excludedItems.add(bodyFocus);
                } else {
                    excludedItems.remove(bodyFocus);
                }
            }
        };
    }

    public OnClickListener clickSelectAllEquipmentCheckBox() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView selectAllEquipmentTextView = (TextView) view;
                if (selectAllEquipmentTextView.getText().toString().equals("Select all")) {
                    shouldSwitchToSelectAllTextValue.set(false);
                    for (Equipment equipment : Equipment.values()) {
                        excludedItems.add(equipment.getEquipmentNameAlias());
                    }
                } else {
                    shouldSwitchToSelectAllTextValue.set(true);
                    for (Equipment equipment : Equipment.values()) {
                        excludedItems.remove(equipment.getEquipmentNameAlias());
                    }
                }
            }
        };
    }
}

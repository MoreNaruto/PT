package tmosq.com.pt.viewModel;


import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;

public class FocalBodyFocusViewModel {
    @Getter
    private List<String> activeBodyFocuses = new ArrayList<>();

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

}

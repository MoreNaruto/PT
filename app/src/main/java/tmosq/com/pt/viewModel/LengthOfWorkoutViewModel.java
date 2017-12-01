package tmosq.com.pt.viewModel;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableField;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import lombok.Getter;

public class LengthOfWorkoutViewModel {
    @Getter
    private ObservableField<Integer> lengthOfWorkoutText;

    public LengthOfWorkoutViewModel() {
        lengthOfWorkoutText = new ObservableField<>();
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner appCompatSpinner, Integer newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<Integer>) appCompatSpinner.getAdapter()).getPosition(newSelectedValue);
            appCompatSpinner.setSelection(pos, true);
        }
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static Integer captureSelectedValue(AppCompatSpinner appCompatSpinner) {
        return Integer.valueOf(appCompatSpinner.getSelectedItem().toString());
    }
}

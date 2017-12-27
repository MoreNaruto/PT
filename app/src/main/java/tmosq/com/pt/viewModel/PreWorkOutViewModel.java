package tmosq.com.pt.viewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.view.View;
import android.widget.CheckBox;

import lombok.Getter;

public class PreWorkOutViewModel {
    @Getter
    private ObservableBoolean hasPartner = new ObservableBoolean(false);

    public BaseObservable makeWorkoutButtonClicked = new BaseObservable();

    public PreWorkOutViewModel() {
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
}

package tmosq.com.pt.fragment;


import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.EquipmentFragmentBinding;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.viewModel.EquipmentViewModel;

public class EquipmentFragment extends Fragment{
    protected EquipmentFragmentBinding binding;
    protected EquipmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.equipment_fragment, container, false);
        View view = binding.getRoot();

        viewModel = new EquipmentViewModel();
        binding.setViewModel(viewModel);

        viewModel.shouldSwitchToSelectAllTextValue.addOnPropertyChangedCallback(selectSwitchToSelectAllCallback());
        return view;
    }

    private Observable.OnPropertyChangedCallback selectSwitchToSelectAllCallback(){
       return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (viewModel.shouldSwitchToSelectAllTextValue.get()){
                    binding.selectAllTextView.setText(R.string.select_all);
                    for (Equipment equipment : Equipment.values()) {
                        CheckBox equipmentCheckBox = (CheckBox) getView().findViewById(equipment.getResourceIdCheckBox());
                        equipmentCheckBox.setChecked(false);
                    }
                } else {
                    binding.selectAllTextView.setText(R.string.unselect_all);
                    for (Equipment equipment : Equipment.values()) {
                        CheckBox equipmentCheckBox = (CheckBox) getView().findViewById(equipment.getResourceIdCheckBox());
                        equipmentCheckBox.setChecked(true);
                    }
                }
            }
        };
    }

    public List<String> getExcludedEquipmentItems() {
        return viewModel.getExcludedItems();
    }
}

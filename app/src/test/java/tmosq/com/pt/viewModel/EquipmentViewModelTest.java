package tmosq.com.pt.viewModel;

import android.widget.CheckBox;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.R;
import tmosq.com.pt.model.exercise_support_enums.Equipment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.RuntimeEnvironment.application;

@RunWith(RobolectricTestRunner.class)
public class EquipmentViewModelTest {
    private EquipmentViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new EquipmentViewModel();
    }

    @Test
    public void clickEquipmentCheckBox() throws Exception {
        CheckBox barbellCheckBox = new CheckBox(application);
        barbellCheckBox.setId(R.id.barbell_checkbox);
        barbellCheckBox.setChecked(true);

        viewModel.clickEquipmentCheckBox().onClick(barbellCheckBox);
        assertEquals(viewModel.getExcludedItems().get(0), Equipment.BARBELL.getEquipmentNameAlias());
    }

    @Test
    public void clickSelectAllEquipmentCheckBox_selectAllIsClicked() throws Exception {
        TextView selectAllTextView = new TextView(application);
        selectAllTextView.setId(R.id.select_all_text_view);
        selectAllTextView.setText(R.string.select_all);

        viewModel.clickSelectAllEquipmentCheckBox().onClick(selectAllTextView);

        assertFalse(viewModel.shouldSwitchToSelectAllTextValue.get());
        assertEquals(viewModel.getExcludedItems().size(), Equipment.allEquipmentNameAliases().size());
    }

    @Test
    public void clickSelectAllEquipmentCheckBox_unselectAllIsClicked() throws Exception {
        TextView selectAllTextView = new TextView(application);
        selectAllTextView.setId(R.id.select_all_text_view);
        selectAllTextView.setText(R.string.select_all);

        viewModel.clickSelectAllEquipmentCheckBox().onClick(selectAllTextView);
        selectAllTextView.setText(R.string.unselect_all);

        viewModel.clickSelectAllEquipmentCheckBox().onClick(selectAllTextView);

        assertTrue(viewModel.shouldSwitchToSelectAllTextValue.get());
        assertEquals(viewModel.getExcludedItems().size(),0);
    }
}
package tmosq.com.pt.viewModel;

import android.widget.CheckBox;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.R;
import tmosq.com.pt.activity.PreWorkoutActivity;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.robolectric.RuntimeEnvironment.application;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BANDS;
import static tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment.POWER_LIFTING;

@RunWith(RobolectricTestRunner.class)
public class PreWorkOutViewModelTest {

    private PreWorkOutViewModel preWorkOutViewModel;
    private PreWorkoutActivity preWorkoutActivity;

    @Before
    public void setUp() throws Exception {
        preWorkoutActivity = mock(PreWorkoutActivity.class);
        preWorkOutViewModel = new PreWorkOutViewModel();
    }

    @Test
    public void setWorkOutRegiment_getWorkOutRegiment() throws Exception {
        preWorkOutViewModel.setWorkOutRegiment(POWER_LIFTING.getWorkOutRegimentNameAlias());

        assertEquals(preWorkOutViewModel.getWorkOutRegiment().get(), POWER_LIFTING.getWorkOutRegimentNameAlias());
    }


    @Test
    public void setWorkOutDifficulty_getDifficulty() throws Exception {
        preWorkOutViewModel.setWorkOutDifficulty(ADVANCED.getDifficultyNameAlias());

        assertEquals(preWorkOutViewModel.getWorkoutDifficulty().get(), ADVANCED.getDifficultyNameAlias());
    }

    @Test
    public void clickEquipmentCheckBox_getExcludedEquipment() throws Exception {
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class).create().get();
        preWorkOutViewModel = new PreWorkOutViewModel();

        CheckBox bandsCheckBox = new CheckBox(application);
        bandsCheckBox.setId(R.id.bands_checkbox);
        bandsCheckBox.setChecked(true);

        preWorkOutViewModel.clickEquipmentCheckBox().onClick(bandsCheckBox);
        assertEquals(preWorkOutViewModel.getExcludedEquipments().get().get(0), BANDS.getEquipmentNameAlias());
    }

    @Test
    public void clickMakeWorkOutButton_whenHavingAPartner_startWorkOutActivityWithPartner() throws Exception {
        CheckBox partnerCheckBox = new CheckBox(application);

        partnerCheckBox.setId(R.id.partner_checkbox);
        partnerCheckBox.setChecked(true);

        preWorkOutViewModel.clickPartnerBox().onClick(partnerCheckBox);
        assertTrue(preWorkOutViewModel.getHasPartner().get());
    }

    @Test
    public void clickSelectAllEquipmentCheckBox_whenSelectAllIsClicked_shouldHighlightAllExcludedEquipmentItems() throws Exception {
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class).create().get();
        preWorkOutViewModel = new PreWorkOutViewModel();

        TextView defaultValue = new TextView(application);
        defaultValue.setId(R.id.select_all_text_view);
        defaultValue.setText(R.string.select_all);

        preWorkOutViewModel.clickSelectAllEquipmentCheckBox().onClick(defaultValue);

        assertFalse(preWorkOutViewModel.getExcludedEquipments().get().isEmpty());
        assertEquals(preWorkOutViewModel.selectAllEquipmentClicked.get(), "Select all");
    }

    @Test
    public void clickSelectAllEquipmentCheckBox_whenUnSelectAllIsClicked_shouldUnHighlightAllExcludedEquipmentItems() throws Exception {
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class).create().get();
        preWorkOutViewModel = new PreWorkOutViewModel();

        TextView defaultValue = new TextView(application);
        defaultValue.setId(R.id.select_all_text_view);
        defaultValue.setText(R.string.unselect_all);

        preWorkOutViewModel.clickSelectAllEquipmentCheckBox().onClick(defaultValue);

        assertTrue(preWorkOutViewModel.getExcludedEquipments().get().isEmpty());
        assertEquals(preWorkOutViewModel.selectAllEquipmentClicked.get(), "Unselect all");
    }
}
package tmosq.com.pt.viewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.R;
import tmosq.com.pt.activity.PreWorkoutActivity;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.RuntimeEnvironment.application;
import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BANDS;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.CABLE;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class PreWorkOutViewModelTest {

    private PreWorkOutViewModel preWorkOutViewModel;
    private PreWorkoutActivity preWorkoutActivity;

    @Before
    public void setUp() throws Exception {
        preWorkoutActivity = mock(PreWorkoutActivity.class);
        preWorkOutViewModel = new PreWorkOutViewModel(preWorkoutActivity);
    }

    @Test
    public void clickMakeWorkOutButton_whenUserDoesNotMakeAnyChanges_startWorkOutActivityWithDefaultValues() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_REGIMENT), WorkoutRegiment.CARDIO.getWorkOutRegimentNameAlias());
        assertEquals(intentArgumentCaptor.getValue().getSerializableExtra(WORK_OUT_LENGTH), 30);
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_DIFFICULTY), Difficulty.BASIC.getDifficultyNameAlias());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT), new Gson().toJson(new ArrayList<>()));
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES), new Gson().toJson(new ArrayList<>()));
    }

    @Test
    public void clickMakeWorkOutButton_whenWorkOutRegimentIsUpdated_startWorkOutWithNewWorkOutRegiment() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        preWorkOutViewModel.setWorkOutRegiment(WorkoutRegiment.POWER_LIFTING.getWorkOutRegimentNameAlias());

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_REGIMENT), WorkoutRegiment.POWER_LIFTING.getWorkOutRegimentNameAlias());
    }

    @Test
    public void clickMakeWorkOutButton_whenWorkOutLengthIsUpdated_startWorkoutWithNewWorkOutLength() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        preWorkOutViewModel.setWorkOutLength(55);

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getSerializableExtra(WORK_OUT_LENGTH), 55);
    }

    @Test
    public void clickMakeWorkOutButton_whenWorkOutDifficultyIsUpdated_startWorkoutWithNewDifficulty() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        preWorkOutViewModel.setWorkOutDifficulty(ADVANCED.getDifficultyNameAlias());

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_DIFFICULTY), ADVANCED.getDifficultyNameAlias());
    }

    @Test
    public void clickMakeWorkOutButton_whenAddingBodyFocuses_startWorkOutActivityWithBodyFocuses() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        CheckBox defaultValue = new CheckBox(application);

        defaultValue.setChecked(true);
        defaultValue.setId(R.id.biceps_checkbox);
        preWorkOutViewModel.clickBodyFocusCheckBox().onClick(defaultValue);
        defaultValue.setId(R.id.abductors_checkbox);
        preWorkOutViewModel.clickBodyFocusCheckBox().onClick(defaultValue);

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        ArrayList<String> activeBodyFocuses = new ArrayList<>();

        activeBodyFocuses.add(BodyFocus.BICEPS.getBodyPartNameAlias());
        activeBodyFocuses.add(BodyFocus.ABDUCTORS.getBodyPartNameAlias());

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES), new Gson().toJson(activeBodyFocuses));
    }

    @Test
    public void clickMakeWorkOutButton_whenRemovingExcludedEquipment_startWorkOutActivityWithExcludedEquipment() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        CheckBox defaultValue = new CheckBox(application);

        defaultValue.setChecked(true);
        defaultValue.setId(R.id.bands_checkbox);
        preWorkOutViewModel.clickEquipmentCheckBox().onClick(defaultValue);
        defaultValue.setId(R.id.cable_checkbox);
        preWorkOutViewModel.clickEquipmentCheckBox().onClick(defaultValue);

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        ArrayList<String> excludedEquipments = new ArrayList<>();

        excludedEquipments.add(BANDS.getEquipmentNameAlias());
        excludedEquipments.add(CABLE.getEquipmentNameAlias());

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT),
                new Gson().toJson(excludedEquipments));
    }

    @Test
    public void clickMakeWorkOutButton_whenHavingAPartner_startWorkOutActivityWithPartner() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        CheckBox defaultValue = new CheckBox(application);

        defaultValue.setChecked(true);
        defaultValue.setId(R.id.partner_checkbox);

        preWorkOutViewModel.clickPartnerBox().onClick(defaultValue);
        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        verify(preWorkoutActivity).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getBooleanExtra(HAS_PARTNER, false), true);
    }

    @Test
    public void clickSelectAllEquipmentCheckBox_whenSelectAllIsClicked_shouldHighlightAllExcludedEquipmentItems() throws Exception {
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class).create().get();
        preWorkOutViewModel = new PreWorkOutViewModel(preWorkoutActivity);
        ArrayList<String> excludedEquipments = new ArrayList<>();

        for (Equipment equipment : Equipment.values()) {
            excludedEquipments.add(equipment.getEquipmentNameAlias());
        }

        TextView defaultValue = new TextView(application);
        defaultValue.setId(R.id.select_all_text_view);
        defaultValue.setText(R.string.select_all);

        preWorkOutViewModel.clickSelectAllEquipmentCheckBox().onClick(defaultValue);
        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(intent.getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT),
                new Gson().toJson(excludedEquipments));
        for (Equipment equipment : Equipment.values()) {
            CheckBox equipmentCheckBox = (CheckBox) preWorkoutActivity.findViewById(equipment.getResourceIdCheckBox());
            assertTrue(equipmentCheckBox.isChecked());
        }
        assertEquals(defaultValue.getText(), "Unselect all");
    }

    @Test
    public void clickSelectAllEquipmentCheckBox_whenUnSelectAllIsClicked_shouldUnHighlightAllExcludedEquipmentItems() throws Exception {
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class).create().get();
        preWorkOutViewModel = new PreWorkOutViewModel(preWorkoutActivity);

        TextView defaultValue = new TextView(application);
        defaultValue.setId(R.id.select_all_text_view);
        defaultValue.setText(R.string.unselect_all);

        preWorkOutViewModel.clickSelectAllEquipmentCheckBox().onClick(defaultValue);
        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(intent.getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT),
                new Gson().toJson(new ArrayList<>()));
        for (Equipment equipment : Equipment.values()) {
            CheckBox equipmentCheckBox = (CheckBox) preWorkoutActivity.findViewById(equipment.getResourceIdCheckBox());
            assertFalse(equipmentCheckBox.isChecked());
        }
        assertEquals(defaultValue.getText(), "Select all");
    }
}
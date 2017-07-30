package tmosq.com.pt.viewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.R;
import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.RuntimeEnvironment.application;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class PreWorkOutViewModelTest {

    private PreWorkOutViewModel preWorkOutViewModel;
    private Context mockContext;

    @Before
    public void setUp() throws Exception {
        mockContext = mock(Context.class);
        preWorkOutViewModel = new PreWorkOutViewModel(mockContext);
    }

    @Test
    public void clickMakeWorkOutButton_whenUserDoesNotMakeAnyChanges_startWorkOutActivityWithDefaultValues() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        verify(mockContext).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_REGIMENT), WorkoutRegiment.CARDIO.getWorkOutRegimentNameAlias());
        assertEquals(intentArgumentCaptor.getValue().getSerializableExtra(WORK_OUT_LENGTH), 30);
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_DIFFICULTY), Difficulty.BASIC.getDifficultyNameAlias());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT), new Gson().toJson(new ArrayList<>()));
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES), new Gson().toJson(new ArrayList<>()));
    }

    @Test
    public void clickMakeWorkOutButton_whenUserMakesChanges_startWorkOutActivityWithChanges() throws Exception {
        ArgumentCaptor<Intent> intentArgumentCaptor = ArgumentCaptor.forClass(Intent.class);
        CheckBox defaultValue = new CheckBox(application);
        preWorkOutViewModel.setWorkOutRegiment(WorkoutRegiment.POWER_LIFTING.getWorkOutRegimentNameAlias());
        preWorkOutViewModel.setWorkOutLength(55);
        preWorkOutViewModel.setWorkOutDifficulty(Difficulty.ADVANCED.getDifficultyNameAlias());

        defaultValue.setChecked(true);
        defaultValue.setId(R.id.biceps_checkbox);
        preWorkOutViewModel.clickBodyFocusCheckBox().onClick(defaultValue);
        defaultValue.setId(R.id.abductors_checkbox);
        preWorkOutViewModel.clickBodyFocusCheckBox().onClick(defaultValue);

        defaultValue.setId(R.id.bands_checkbox);
        preWorkOutViewModel.clickEquipmentCheckBox().onClick(defaultValue);
        defaultValue.setId(R.id.cable_checkbox);
        preWorkOutViewModel.clickEquipmentCheckBox().onClick(defaultValue);

        preWorkOutViewModel.clickMakeWorkOutButton().onClick(new View(application));

        ArrayList<String> excludedEquipments = new ArrayList<>();
        ArrayList<String> activeBodyFocuses = new ArrayList<>();

        activeBodyFocuses.add(BodyFocus.BICEPS.getBodyPartNameAlias());
        activeBodyFocuses.add(BodyFocus.ABDUCTORS.getBodyPartNameAlias());

        excludedEquipments.add(Equipment.BANDS.getEquipmentNameAlias());
        excludedEquipments.add(Equipment.CABLE.getEquipmentNameAlias());

        verify(mockContext).startActivity(intentArgumentCaptor.capture());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_REGIMENT), WorkoutRegiment.POWER_LIFTING.getWorkOutRegimentNameAlias());
        assertEquals(intentArgumentCaptor.getValue().getSerializableExtra(WORK_OUT_LENGTH), 55);
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(WORK_OUT_DIFFICULTY), Difficulty.ADVANCED.getDifficultyNameAlias());
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT), new Gson().toJson(excludedEquipments));
        assertEquals(intentArgumentCaptor.getValue().getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES), new Gson().toJson(activeBodyFocuses));
    }
}
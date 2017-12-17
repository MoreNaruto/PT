package tmosq.com.pt.activity;

import android.content.Intent;
import android.databinding.ObservableField;
import android.widget.ArrayAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.fragment.FocalBodyFocusFragment;
import tmosq.com.pt.fragment.LengthOfWorkoutFragment;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;
import tmosq.com.pt.viewModel.PreWorkOutViewModel;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

@RunWith(RobolectricTestRunner.class)
public class PreWorkoutActivityTest {
    private PreWorkoutActivity preWorkoutActivity;
    private ActivityPreWorkoutBinding binding;

    @Before
    public void setUp() throws Exception {
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class).create().start().get();
        binding = preWorkoutActivity.binding;
    }

    @Test
    public void onCreate_bindsToViewModel() throws Exception {
        assertEquals(preWorkoutActivity.binding.getViewModel(), preWorkoutActivity.preWorkOutViewModel);
    }

    @Test
    public void onCreate_addFragmentsToFragmentManager() throws Exception {
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.workout_length_frame_id) instanceof LengthOfWorkoutFragment);
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.focal_body_point_frame_id) instanceof FocalBodyFocusFragment);
    }

    @Test
    public void onStart_whenSelectAllTextCallback_andTextViewValueIsSelectAll_selectAllEquipmentCheckboxes() throws Exception {
        ObservableField<String> selectAllEquipmentClicked = preWorkoutActivity.preWorkOutViewModel.selectAllEquipmentClicked;
        selectAllEquipmentClicked.set(preWorkoutActivity.getString(R.string.select_all));

        assertEquals(binding.selectAllTextView.getText().toString(), preWorkoutActivity.getString(R.string.unselect_all));
        assertEquals(binding.bandsCheckbox.isChecked(), true);
    }

    @Test
    public void onStart_whenSelectAllTextCallback_andTextViewValueIsUnSelectAll_selectUnAllEquipmentCheckboxes() throws Exception {
        ObservableField<String> selectAllEquipmentClicked = preWorkoutActivity.preWorkOutViewModel.selectAllEquipmentClicked;
        selectAllEquipmentClicked.set(preWorkoutActivity.getString(R.string.unselect_all));

        assertEquals(binding.selectAllTextView.getText().toString(), preWorkoutActivity.getString(R.string.select_all));
        assertEquals(binding.bandsCheckbox.isChecked(), false);
    }

    @Test
    public void onStart_whenMakeWorkoutButtonClickedCallback_goToWorkOutActivity() throws Exception {
        preWorkoutActivity.preWorkOutViewModel.makeWorkoutButtonClicked.notifyChange();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(intent.getComponent().getClassName(), WorkoutActivity.class.getName());
        assertNotNull(intent.getStringExtra(WORK_OUT_REGIMENT));
        assertNotEquals(intent.getIntExtra(WORK_OUT_LENGTH, 0), 0);
        assertNotNull(intent.getStringExtra(WORK_OUT_DIFFICULTY));
        assertEquals(intent.getBooleanExtra(HAS_PARTNER, true), false);
        assertNotNull(intent.getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT));
        assertNotNull(intent.getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES));
    }

    @Test
    public void onStart_setUpWorkoutDifficultyDropDownMenuAdapter() throws Exception {
        final ArrayList<String> difficultyValues = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyValues.add(difficulty.getDifficultyNameAlias());
        }

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                preWorkoutActivity,
                R.layout.support_simple_spinner_dropdown_item,
                difficultyValues);

        assertThat(preWorkoutActivity.binding.workoutDifficultyDropdownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutDifficulty() throws Exception {
        preWorkoutActivity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        preWorkoutActivity.binding.workoutDifficultyDropdownMenu.setSelection(0);
        preWorkoutActivity.binding.workoutDifficultyDropdownMenu.setSelection(1);
        preWorkoutActivity.binding.workoutDifficultyDropdownMenu.setSelection(2);

        verify(preWorkoutActivity.preWorkOutViewModel).setWorkOutDifficulty("basic");
        verify(preWorkoutActivity.preWorkOutViewModel).setWorkOutDifficulty("intermediate");
        verify(preWorkoutActivity.preWorkOutViewModel).setWorkOutDifficulty("advanced");
    }

    @Test
    public void onStart_setWorkoutRegimentDropDownMenuAdapter() throws Exception {
        final ArrayList<String> workOutRegiment = new ArrayList<>();
        for (WorkoutRegiment workoutRegiment : WorkoutRegiment.values()) {
            workOutRegiment.add(workoutRegiment.getWorkOutRegimentNameAlias());
        }


        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                preWorkoutActivity,
                R.layout.support_simple_spinner_dropdown_item,
                workOutRegiment);

        assertThat(preWorkoutActivity.binding.workoutRegimentDropdownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutRegiment() throws Exception {
        preWorkoutActivity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        preWorkoutActivity.binding.workoutRegimentDropdownMenu.setSelection(0);
        preWorkoutActivity.binding.workoutRegimentDropdownMenu.setSelection(1);
        preWorkoutActivity.binding.workoutRegimentDropdownMenu.setSelection(2);

        verify(preWorkoutActivity.preWorkOutViewModel).setWorkOutRegiment("cardio");
        verify(preWorkoutActivity.preWorkOutViewModel).setWorkOutRegiment("cross fit");
        verify(preWorkoutActivity.preWorkOutViewModel).setWorkOutRegiment("power lifting");
    }
}
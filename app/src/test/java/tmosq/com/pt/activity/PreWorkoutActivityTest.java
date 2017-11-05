package tmosq.com.pt.activity;

import android.widget.ArrayAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.R;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;
import tmosq.com.pt.viewModel.PreWorkOutViewModel;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class PreWorkoutActivityTest {
    private ActivityController<PreWorkoutActivity> activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(PreWorkoutActivity.class);
    }

    @Test
    public void onCreate_bindsToViewModel() throws Exception {
        PreWorkoutActivity activity = activityController.create().get();

        assertEquals(activity.binding.getViewModel(), activity.preWorkOutViewModel);
    }

    @Test
    public void onStart_setUpWorkoutDifficultyDropDownMenuAdapter() throws Exception {
        final ArrayList<String> difficultyValues = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyValues.add(difficulty.getDifficultyNameAlias());
        }

        PreWorkoutActivity activity = activityController.create().start().get();

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                difficultyValues);

        assertThat(activity.binding.workoutDifficultyDropdownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutDifficulty() throws Exception {
        PreWorkoutActivity activity = activityController.create().start().get();

        activity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        activity.binding.workoutDifficultyDropdownMenu.setSelection(0);
        activity.binding.workoutDifficultyDropdownMenu.setSelection(1);
        activity.binding.workoutDifficultyDropdownMenu.setSelection(2);

        verify(activity.preWorkOutViewModel).setWorkOutDifficulty("basic");
        verify(activity.preWorkOutViewModel).setWorkOutDifficulty("intermediate");
        verify(activity.preWorkOutViewModel).setWorkOutDifficulty("advanced");
    }

    @Test
    public void onStart_setWorkoutRegimentDropDownMenuAdapter() throws Exception {
        final ArrayList<String> workOutRegiment = new ArrayList<>();
        for (WorkoutRegiment workoutRegiment : WorkoutRegiment.values()) {
            workOutRegiment.add(workoutRegiment.getWorkOutRegimentNameAlias());
        }

        PreWorkoutActivity activity = activityController.create().start().get();

        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                workOutRegiment);

        assertThat(activity.binding.workoutRegimentDropdownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutRegiment() throws Exception {
        PreWorkoutActivity activity = activityController.create().start().get();

        activity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        activity.binding.workoutRegimentDropdownMenu.setSelection(0);
        activity.binding.workoutRegimentDropdownMenu.setSelection(1);
        activity.binding.workoutRegimentDropdownMenu.setSelection(2);

        verify(activity.preWorkOutViewModel).setWorkOutRegiment("cardio");
        verify(activity.preWorkOutViewModel).setWorkOutRegiment("cross fit");
        verify(activity.preWorkOutViewModel).setWorkOutRegiment("power lifting");
    }

    @Test
    public void onStart_setUpWorkOutLengthDropDownMenuAdapter() throws Exception {
        final Integer[] allottedLengthsOfTimes = {30, 45, 60, 75, 90, 105, 120};

        PreWorkoutActivity activity = activityController.create().start().get();
        ArrayAdapter<Integer> staticAdapter = new ArrayAdapter<>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                allottedLengthsOfTimes);

        assertThat(activity.binding.workoutLengthDropdownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutLength() throws Exception {
        PreWorkoutActivity activity = activityController.create().start().get();
        activity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        activity.binding.workoutLengthDropdownMenu.setSelection(0);
        activity.binding.workoutLengthDropdownMenu.setSelection(1);
        activity.binding.workoutLengthDropdownMenu.setSelection(2);
        activity.binding.workoutLengthDropdownMenu.setSelection(3);
        activity.binding.workoutLengthDropdownMenu.setSelection(4);
        activity.binding.workoutLengthDropdownMenu.setSelection(5);
        activity.binding.workoutLengthDropdownMenu.setSelection(6);

        verify(activity.preWorkOutViewModel).setWorkOutLength(30);
        verify(activity.preWorkOutViewModel).setWorkOutLength(45);
        verify(activity.preWorkOutViewModel).setWorkOutLength(60);
        verify(activity.preWorkOutViewModel).setWorkOutLength(75);
        verify(activity.preWorkOutViewModel).setWorkOutLength(90);
        verify(activity.preWorkOutViewModel).setWorkOutLength(105);
        verify(activity.preWorkOutViewModel).setWorkOutLength(120);
    }
}
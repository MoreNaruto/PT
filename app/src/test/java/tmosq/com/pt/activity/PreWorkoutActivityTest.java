package tmosq.com.pt.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.R;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.viewModel.PreWorkOutViewModel;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class PreWorkoutActivityTest {

    private PreWorkoutActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(PreWorkoutActivity.class);
    }

    @Test
    public void onCreate_bindsToViewModel() throws Exception {
        activity.onCreate(new Bundle());

        assertEquals(activity.binding.getViewModel(), activity.preWorkOutViewModel);
    }

    @Test
    public void onStart_setUpWorkoutDifficultyDropDownMenuAdapter() throws Exception {
        final ArrayList<String> difficultyValues = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyValues.add(difficulty.getDifficultyNameAlias());
        }
        ArrayAdapter<String> staticAdapter = new ArrayAdapter<>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                difficultyValues);


        activity.onStart();

        assertThat(activity.workoutDifficultyDropDownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutDifficulty() throws Exception {
        activity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        activity.onStart();

        activity.workoutDifficultyDropDownMenu.setSelection(0);

        verify(activity.preWorkOutViewModel).setWorkOutDifficulty("basic");
    }

    @Test
    public void onStart_setUpWorkOutLengthDropDownMenuAdapter() throws Exception {
        final Integer[] allottedLengthsOfTimes = {30, 45, 60, 75, 90, 105, 120};
        ArrayAdapter<Integer> staticAdapter = new ArrayAdapter<>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                allottedLengthsOfTimes);

        activity.onStart();

        assertThat(activity.workoutLengthDropDownMenu.getAdapter().getItem(0))
                .isEqualTo(staticAdapter.getItem(0));
    }

    @Test
    public void onStart_whenItemIsSelected_setWorkOutLength() throws Exception {
        activity.preWorkOutViewModel = mock(PreWorkOutViewModel.class);

        activity.onStart();

        activity.workoutLengthDropDownMenu.setSelection(0);

        verify(activity.preWorkOutViewModel).setWorkOutLength(30);
    }
}
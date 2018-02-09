package tmosq.com.pt.activity;

import android.content.Intent;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.fragment.DifficultyFragment;
import tmosq.com.pt.fragment.EquipmentFragment;
import tmosq.com.pt.fragment.FocalBodyFocusFragment;
import tmosq.com.pt.fragment.LengthOfWorkoutFragment;
import tmosq.com.pt.fragment.WorkoutRegimentFragment;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.difficulty_frame_id) instanceof DifficultyFragment);
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.equipment_frame_id) instanceof EquipmentFragment);
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.workout_regiment_frame_id) instanceof WorkoutRegimentFragment);
    }

    @Test
    public void onStart_whenMakeWorkoutButtonClickedCallback_goToWorkOutActivity() throws Exception {
        preWorkoutActivity.workoutRegimentFragment = mock(WorkoutRegimentFragment.class);
        preWorkoutActivity.lengthOfWorkoutFragment = mock(LengthOfWorkoutFragment.class);
        preWorkoutActivity.difficultyFragment = mock(DifficultyFragment.class);
        preWorkoutActivity.equipmentFragment = mock(EquipmentFragment.class);
        preWorkoutActivity.focalBodyFocusFragment = mock(FocalBodyFocusFragment.class);

        when(preWorkoutActivity.workoutRegimentFragment.getWorkOutRegiment())
                .thenReturn("Flyer Cooks");
        when(preWorkoutActivity.lengthOfWorkoutFragment.getLengthOfWorkout())
                .thenReturn(15);
        when(preWorkoutActivity.difficultyFragment.getDifficulty())
                .thenReturn("advanced");
        when(preWorkoutActivity.equipmentFragment.getExcludedEquipmentItems())
                .thenReturn(newArrayList("chair", "barbell"));
        when(preWorkoutActivity.focalBodyFocusFragment.getActiveBodyFocuses())
                .thenReturn(newArrayList("hamstring", "lower back"));

        preWorkoutActivity.preWorkOutViewModel.makeWorkoutButtonClicked.notifyChange();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(intent.getComponent().getClassName(), WorkoutActivity.class.getName());
        assertThat(intent.getStringExtra(WORK_OUT_REGIMENT))
                .isEqualTo("Flyer Cooks");
        assertThat(intent.getIntExtra(WORK_OUT_LENGTH, 0))
                .isEqualTo(15);
        assertThat(intent.getStringExtra(WORK_OUT_DIFFICULTY))
                .isEqualTo("advanced");
        assertThat(intent.getBooleanExtra(HAS_PARTNER, true))
                .isFalse();
        assertThat(intent.getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT))
                .isEqualTo(new Gson().toJson(newArrayList("chair", "barbell")));
        assertThat(intent.getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES))
                .isEqualTo(new Gson().toJson(newArrayList("hamstring", "lower back")));
    }
}
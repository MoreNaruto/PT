package tmosq.com.pt.activity;

import android.content.Intent;
import android.os.Build;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityPreWorkoutBinding;
import tmosq.com.pt.fragment.DifficultyFragment;
import tmosq.com.pt.fragment.EquipmentFragment;
import tmosq.com.pt.fragment.FocalBodyFocusFragment;
import tmosq.com.pt.fragment.LengthOfWorkoutFragment;
import tmosq.com.pt.fragment.WorkoutRegimentGridFragment;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

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
@Config(sdk = Build.VERSION_CODES.M)
public class PreWorkoutActivityTest {
    private PreWorkoutActivity preWorkoutActivity;
    private ActivityPreWorkoutBinding binding;

    @Before
    public void setUp() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.CHIPPER.getWorkoutRegimentTitle());

        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.workoutRegimentGridFragment = mock(WorkoutRegimentGridFragment.class);
        preWorkoutActivity.lengthOfWorkoutFragment = mock(LengthOfWorkoutFragment.class);
        preWorkoutActivity.difficultyFragment = mock(DifficultyFragment.class);
        preWorkoutActivity.equipmentFragment = mock(EquipmentFragment.class);
        preWorkoutActivity.focalBodyFocusFragment = mock(FocalBodyFocusFragment.class);

        when(preWorkoutActivity.lengthOfWorkoutFragment.getLengthOfWorkout())
                .thenReturn(15);
        when(preWorkoutActivity.difficultyFragment.getDifficulty())
                .thenReturn("advanced");
        when(preWorkoutActivity.equipmentFragment.getExcludedEquipmentItems())
                .thenReturn(newArrayList("chair", "barbell"));
        when(preWorkoutActivity.focalBodyFocusFragment.getActiveBodyFocuses())
                .thenReturn(newArrayList("hamstring", "lower back"));
    }

    @Test
    public void onCreate_addFragmentsToFragmentManager() {
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.workout_length_frame) instanceof LengthOfWorkoutFragment);
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.focal_body_point_frame) instanceof FocalBodyFocusFragment);
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.difficulty_frame) instanceof DifficultyFragment);
        assertTrue(preWorkoutActivity.getSupportFragmentManager().findFragmentById(R.id.equipment_frame) instanceof EquipmentFragment);
    }

    @Test
    public void onCreate_whenMakeWorkoutButtonClickedCallback_shouldCreateIntentExtras() {
        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();

        assertThat(intent.getStringExtra(WORK_OUT_REGIMENT))
                .isEqualTo(WorkoutRegiment.CHIPPER.getWorkoutRegimentTitle());
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

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithWeightLifting_shouldNavigateToGenericWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.WEIGHT_LIFTING.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), GenericWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithCardio_shouldNavigateToGenericWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.CARDIO.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), GenericWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithTabata_shouldNavigateToTabataWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.TABATA.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), TabataWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithEmom_shouldNavigateToEmomWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.EMOM.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), EmomWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithAmrap_shouldNavigateToAmrapWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.AMRAP.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), AmrapWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithRft_shouldNavigateToRftWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.RFT.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), RftWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithChipper_shouldNavigateToChipperWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.CHIPPER.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), ChipperWorkoutActivity.class.getName());
    }

    @Test
    public void onCreate_whenMakeWorkOutButtonIsClickedWithLadder_shouldNavigateToLadderWorkoutActivity() {
        Intent intent = new Intent(RuntimeEnvironment.application.getApplicationContext(), PreWorkoutActivity.class);
        intent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.LADDER.getWorkoutRegimentTitle());
        preWorkoutActivity = Robolectric.buildActivity(PreWorkoutActivity.class, intent).create().start().get();
        binding = preWorkoutActivity.binding;

        preWorkoutActivity.binding.makeWorkoutButton.callOnClick();

        Intent activityIntent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(activityIntent.getComponent().getClassName(), LadderWorkoutActivity.class.getName());
    }
}
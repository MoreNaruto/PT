package tmosq.com.pt.fragment;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class LengthOfWorkoutFragmentTest {
    private LengthOfWorkoutFragment lengthOfWorkoutFragment;

    @Before
    public void setUp() {
        lengthOfWorkoutFragment = LengthOfWorkoutFragment.newInstance(WorkoutRegiment.CARDIO.getWorkoutRegimentTitle());

        startFragment(lengthOfWorkoutFragment);
        assertNotNull(lengthOfWorkoutFragment);
    }

    @Test
    public void newInstance_shouldArgumentValueForWorkoutRegiment() {
        assertThat(lengthOfWorkoutFragment.getArguments().getString(LengthOfWorkoutFragment.WORKOUT_REGIMENT_KEY))
                .isEqualTo(WorkoutRegiment.CARDIO.getWorkoutRegimentTitle());
    }

    @Test
    public void onCreate_setUpWorkoutLengthPicker() {
        assertThat(lengthOfWorkoutFragment.binding.workoutNumberPicker.getMaxValue()).isEqualTo(120);
        assertThat(lengthOfWorkoutFragment.binding.workoutNumberPicker.getMinValue()).isEqualTo(0);
        assertThat(lengthOfWorkoutFragment.binding.workoutNumberPicker.getValue()).isEqualTo(WorkoutRegiment.CARDIO.getMinimumWorkoutLength());
        assertThat(lengthOfWorkoutFragment.binding.workoutNumberPicker.getWrapSelectorWheel()).isEqualTo(true);
    }

    @Test
    public void getLengthOfWorkout() {
        lengthOfWorkoutFragment.binding.workoutNumberPicker.setValue(30);
        assertThat(lengthOfWorkoutFragment.getLengthOfWorkout()).isEqualTo(30);
    }
}
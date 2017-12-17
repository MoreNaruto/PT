package tmosq.com.pt.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class LengthOfWorkoutFragmentTest {
    private LengthOfWorkoutFragment lengthOfWorkoutFragment;

    @Before
    public void setUp() throws Exception {
        lengthOfWorkoutFragment = new LengthOfWorkoutFragment();

        startFragment(lengthOfWorkoutFragment);
        assertNotNull(lengthOfWorkoutFragment);
    }

    @Test
    public void getLengthOfWorkout() throws Exception {
        lengthOfWorkoutFragment.binding.workoutLengthDropdownMenu.setSelection(1);
        assertEquals(lengthOfWorkoutFragment.getLengthOfWorkout(), 45);

        lengthOfWorkoutFragment.binding.workoutLengthDropdownMenu.setSelection(2);
        assertEquals(lengthOfWorkoutFragment.getLengthOfWorkout(), 60);
    }
}
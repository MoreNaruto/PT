package tmosq.com.pt.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class WorkoutRegimentFragmentTest {

    private WorkoutRegimentFragment workoutRegimentFragment;

    @Before
    public void setUp() throws Exception {
        workoutRegimentFragment = new WorkoutRegimentFragment();

        startFragment(workoutRegimentFragment);
        assertNotNull(workoutRegimentFragment);
    }

    @Test
    public void getWorkOutRegiment() throws Exception {
        workoutRegimentFragment.binding.workoutRegimentDropdownMenu.setSelection(0);
        assertEquals(workoutRegimentFragment.getWorkOutRegiment(), "cardio");

        workoutRegimentFragment.binding.workoutRegimentDropdownMenu.setSelection(1);
        assertEquals(workoutRegimentFragment.getWorkOutRegiment(), "cross fit");
    }
}
package tmosq.com.pt.fragment;

import android.support.v7.widget.GridLayoutManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.adapter.WorkoutRegimentRecyclerViewAdapter;

import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Java6Assertions.assertThat;
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
    public void onCreate_configureAdapter() throws Exception {
        assertThat(workoutRegimentFragment.binding.workoutRegimentRecyclerView.getAdapter()).isInstanceOf(WorkoutRegimentRecyclerViewAdapter.class);
        assertThat(workoutRegimentFragment.binding.workoutRegimentRecyclerView.getLayoutManager()).isInstanceOf(GridLayoutManager.class);
    }
}
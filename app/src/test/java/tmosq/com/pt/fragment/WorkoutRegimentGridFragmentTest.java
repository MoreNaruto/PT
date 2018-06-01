package tmosq.com.pt.fragment;

import android.os.Build;
import android.support.v7.widget.GridLayoutManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import tmosq.com.pt.adapter.WorkoutRegimentRecyclerViewAdapter;

import static junit.framework.Assert.assertNotNull;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class WorkoutRegimentGridFragmentTest {

    private WorkoutRegimentGridFragment workoutRegimentGridFragment;

    @Before
    public void setUp() throws Exception {
        workoutRegimentGridFragment = new WorkoutRegimentGridFragment();

        startFragment(workoutRegimentGridFragment);
        assertNotNull(workoutRegimentGridFragment);
    }

    @Test
    public void onCreate_configureAdapter() throws Exception {
        assertThat(workoutRegimentGridFragment.binding.workoutRegimentRecyclerView.getAdapter()).isInstanceOf(WorkoutRegimentRecyclerViewAdapter.class);
        assertThat(workoutRegimentGridFragment.binding.workoutRegimentRecyclerView.getLayoutManager()).isInstanceOf(GridLayoutManager.class);
    }
}
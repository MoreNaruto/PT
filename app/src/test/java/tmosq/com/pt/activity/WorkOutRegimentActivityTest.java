package tmosq.com.pt.activity;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import tmosq.com.pt.fragment.WorkoutRegimentGridFragment;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class WorkOutRegimentActivityTest {

    private WorkOutRegimentActivity workOutRegimentActivity;

    @Before
    public void setUp() {
        workOutRegimentActivity = Robolectric.buildActivity(WorkOutRegimentActivity.class).create().get();
    }

    @Test
    public void onCreate_setFragment() {
        assertThat(workOutRegimentActivity.getSupportFragmentManager().getFragments().get(0)).isInstanceOf(WorkoutRegimentGridFragment.class);
    }
}
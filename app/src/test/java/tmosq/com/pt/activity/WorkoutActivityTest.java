package tmosq.com.pt.activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import tmosq.com.pt.BuildConfig;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class WorkoutActivityTest {
    private ActivityController<WorkoutActivity> activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(WorkoutActivity.class);
    }

    @Test
    public void onCreate_bindsToViewModel() throws Exception {
        WorkoutActivity activity = activityController.create().get();

        assertEquals(activity.binding.getViewModel(), activity.workoutViewModel);
    }

}
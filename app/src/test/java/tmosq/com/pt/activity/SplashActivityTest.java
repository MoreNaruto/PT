package tmosq.com.pt.activity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import tmosq.com.pt.BuildConfig;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class SplashActivityTest {

    private ActivityController<SplashActivity> activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(SplashActivity.class);
    }

    @Test
    public void onCreate_intentShouldGoToThePreWorkoutActivity() throws Exception {
        ShadowActivity shadowActivity = shadowOf(activityController.create().get());
        ShadowIntent shadowIntent = shadowOf(shadowActivity.getNextStartedActivity());
        assertThat(shadowIntent.getIntentClass().getName(), equalTo("tmosq.com.pt.activity.PreWorkoutActivity"));
    }
}
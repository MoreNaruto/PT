package tmosq.com.pt.activity;

import android.content.Intent;
import android.os.Build;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class WorkoutDetailActivityTest {

    private ActivityController<WorkoutDetailActivity> workoutDetailActivityActivityController;

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(WorkoutDetailActivity.WORKOUT, "Roller Jump");
        intent.putExtra(WorkoutDetailActivity.WORKOUT_DESCRIPTION, "Jump while using a roller");

        workoutDetailActivityActivityController =
                Robolectric.buildActivity(WorkoutDetailActivity.class, intent);
    }

    @Test
    public void onStart_updateWorkoutDetailView() {
        WorkoutDetailActivity workoutDetailActivity = workoutDetailActivityActivityController.create().start().get();

        Assertions.assertThat(workoutDetailActivity.binding.workoutTitle.getText()).isEqualTo("Roller Jump");
        Assertions.assertThat(workoutDetailActivity.binding.workoutDescription.getText()).isEqualTo("Jump while using a roller");
    }
}
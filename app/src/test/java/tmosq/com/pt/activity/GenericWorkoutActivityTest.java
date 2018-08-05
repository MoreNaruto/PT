package tmosq.com.pt.activity;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.List;

import tmosq.com.pt.adapter.CoolOffAdapter;
import tmosq.com.pt.adapter.WorkoutAdapter;
import tmosq.com.pt.adapter.WarmUpAdapter;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.WorkOutType;
import tmosq.com.pt.viewModel.WorkoutViewModel;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class GenericWorkoutActivityTest {
    private ActivityController<GenericWorkoutActivity> activityController;

    @Mock
    WorkoutViewModel mockWorkoutViewModel;

    @Before
    public void setUp() {
        initMocks(this);
        activityController = Robolectric.buildActivity(GenericWorkoutActivity.class, getInitialIntent());
    }

    @Test
    public void onCreate_bindsToViewModel() {
        GenericWorkoutActivity activity = activityController.create().get();

        assertThat(activity.binding.getViewModel()).isEqualTo(activity.workoutViewModel);
    }

    @Test
    public void onStart_setAdaptersForWorkout() {
        Exercise warmUpAndCoolOffExercise = Exercise.builder().workOutType(WorkOutType.WARM_UP_AND_COOL_OFF).build();
        Exercise bodyExercise = Exercise.builder().workOutType(WorkOutType.BODY).build();

        GenericWorkoutActivity activity = activityController.create().get();

        activity.workoutViewModel = mockWorkoutViewModel;
        mockWorkoutViewModel.warmUpExercises = new ObservableField<List<Exercise>>(newArrayList(warmUpAndCoolOffExercise));
        mockWorkoutViewModel.coolOffExercises = new ObservableField<List<Exercise>>(newArrayList(warmUpAndCoolOffExercise));
        mockWorkoutViewModel.mainWorkoutExercises = new ObservableField<List<Exercise>>(newArrayList(bodyExercise));
        activity.onStart();

        assertThat(activity.coolOffRecyclerView.getAdapter())
                .isExactlyInstanceOf(CoolOffAdapter.class);
        assertThat(activity.warmUpRecyclerView.getAdapter())
                .isExactlyInstanceOf(WarmUpAdapter.class);
        assertThat(activity.mainWorkoutRecyclerView.getAdapter())
                .isExactlyInstanceOf(WorkoutAdapter.class);

        assertThat(activity.coolOffRecyclerView.isNestedScrollingEnabled()).isFalse();
        assertThat(activity.warmUpRecyclerView.isNestedScrollingEnabled()).isFalse();
        assertThat(activity.mainWorkoutRecyclerView.isNestedScrollingEnabled()).isFalse();
    }

    @NonNull
    private Intent getInitialIntent() {
        Intent initialIntent = new Intent();
        initialIntent.putExtra(WORK_OUT_REGIMENT, "cross fit");
        initialIntent.putExtra(WORK_OUT_LENGTH, 50);
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "advanced");
        initialIntent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(newArrayList("bands", "barbell", "bicycle", "exercise ball")));
        initialIntent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList("abdominals", "abductors", "forearms")));
        return initialIntent;
    }

}
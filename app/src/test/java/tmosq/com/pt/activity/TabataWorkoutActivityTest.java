package tmosq.com.pt.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import tmosq.com.pt.adapter.WorkoutAdapter;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;
import tmosq.com.pt.viewModel.TabataViewModel;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class TabataWorkoutActivityTest {
    @Mock
    TabataViewModel mockViewModel;

    private TabataWorkoutActivity subject;
    private ActivityController<TabataWorkoutActivity> activityController;

    @Before
    public void setUp() {
        initMocks(this);
        activityController = Robolectric.buildActivity(TabataWorkoutActivity.class, getInitialIntent());
        subject = activityController.create().get();
        subject.viewModel = mockViewModel;
        when(mockViewModel.getTabataWorkouts()).thenReturn(newArrayList(Exercise.builder().build()));
    }

    @Test
    public void onStart_setAdapterOnRecyclerView() {
        activityController.start();

        assertThat(subject.binding.workoutRecyclerView.getAdapter())
                .isInstanceOf(WorkoutAdapter.class);
        assertThat(subject.binding.workoutRecyclerView.isNestedScrollingEnabled()).isFalse();
        assertThat(subject.binding.workoutRecyclerView.getLayoutManager()).isInstanceOf(LinearLayoutManager.class);
    }

    @NonNull
    private Intent getInitialIntent() {
        Intent initialIntent = new Intent();
        initialIntent.putExtra(WORK_OUT_REGIMENT, WorkoutRegiment.TABATA.getWorkoutRegimentTitle());
        initialIntent.putExtra(WORK_OUT_LENGTH, 20);
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "advanced");
        initialIntent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(newArrayList("bands", "barbell", "bicycle", "exercise ball")));
        initialIntent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList("abdominals", "abductors", "forearms")));
        return initialIntent;
    }
}
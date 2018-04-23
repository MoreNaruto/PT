package tmosq.com.pt.adapter;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import tmosq.com.pt.activity.WorkoutDetailActivity;
import tmosq.com.pt.databinding.WorkoutExerciseListViewItemBinding;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.viewModel.WorkoutExerciseViewModel;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDUCTORS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BARBELL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class MainWorkoutAdapterTest {
    @Mock
    MainWorkoutAdapter.ViewHolder mockViewHolder;

    @Mock
    WorkoutExerciseViewModel mockViewModel;

    @Mock
    WorkoutExerciseListViewItemBinding mockBinding;

    private MainWorkoutAdapter mainWorkoutAdapter;
    private Exercise exercise;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        exercise = Exercise.builder()
                .workOutType(WARM_UP_AND_COOL_OFF)
                .partnerNeeded(true)
                .alternateSide(true)
                .averageSecondsPerRep(3.0)
                .bodyFocus(ABDUCTORS)
                .workout("Rollers Curls")
                .forTime(true)
                .difficulty(ADVANCED)
                .equipment(BARBELL)
                .build();

        mainWorkoutAdapter = new MainWorkoutAdapter(newArrayList(exercise));
    }

    @Test
    public void onBindViewHolder_bindToViewHolder() throws Exception {
        mainWorkoutAdapter.onBindViewHolder(mockViewHolder, 0);

        verify(mockViewHolder).bind(exercise);
    }

    @Test
    public void getItemCount_getNumberOfExercises() throws Exception {
        assertThat(mainWorkoutAdapter.getItemCount()).isEqualTo(1);
    }

    @Test
    public void viewHolder_whenViewIsClicked_GoToWorkoutDetailActivity() throws Exception {
        TextView textView = new TextView(RuntimeEnvironment.application);
        when(mockBinding.getRoot()).thenReturn(textView);

        new MainWorkoutAdapter.ViewHolder(mockBinding, mockViewModel).bind(new Exercise());
        textView.callOnClick();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(intent.getComponent().getClassName(), WorkoutDetailActivity.class.getName());
    }
}
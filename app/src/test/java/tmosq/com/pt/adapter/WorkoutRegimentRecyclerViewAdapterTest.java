package tmosq.com.pt.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.WorkoutRegimentRecyclerViewItemBinding;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robolectric.Shadows.shadowOf;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class WorkoutRegimentRecyclerViewAdapterTest {
    @Mock
    WorkoutRegimentRecyclerViewAdapter.ViewHolder mockViewHolder;

    private WorkoutRegimentRecyclerViewAdapter workoutRegimentRecyclerViewAdapter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        workoutRegimentRecyclerViewAdapter = new WorkoutRegimentRecyclerViewAdapter();
    }

    @Test
    public void onBindViewHolder_bindToViewHolder() throws Exception {
        workoutRegimentRecyclerViewAdapter.onBindViewHolder(mockViewHolder, 0);

        verify(mockViewHolder).bind(WorkoutRegiment.values()[0]);
    }

    @Test
    public void getItemCount() throws Exception {
        assertThat(workoutRegimentRecyclerViewAdapter.getItemCount()).isEqualTo(WorkoutRegiment.values().length);
    }

    @Test
    public void bind_setUpBinding() {
        WorkoutRegimentRecyclerViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                RuntimeEnvironment.application.getApplicationContext()),
                R.layout.workout_regiment_recycler_view_item,
                generateViewGroup(),
                false);
        WorkoutRegimentRecyclerViewAdapter.ViewHolder viewHolder = new WorkoutRegimentRecyclerViewAdapter.ViewHolder(binding);

        workoutRegimentRecyclerViewAdapter.onBindViewHolder(viewHolder, 0);

        assertThat(binding.workoutRegimentTextView.getText()).isEqualTo(WorkoutRegiment.WEIGHT_LIFTING.getWorkoutRegimentTitle());
        assertThat(binding.workoutRegimentImageView.getContentDescription().toString())
                .isEqualTo(WorkoutRegiment.WEIGHT_LIFTING.getContentDescription());
        assertThat(((ColorDrawable) binding.workoutRegimentRelativeLayout.getBackground()).getColor())
                .isEqualTo(ContextCompat.getColor(RuntimeEnvironment.application.getApplicationContext(), WorkoutRegiment.WEIGHT_LIFTING.getColorBackgroundId()));
        assertThat(binding.workoutRegimentRelativeLayout.getId())
                .isEqualTo(WorkoutRegiment.WEIGHT_LIFTING.getImageId());
    }

    @Test
    public void bind_whenItemIsSelected_navigateToPreWorkoutActivity() {
        WorkoutRegimentRecyclerViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                RuntimeEnvironment.application.getApplicationContext()),
                R.layout.workout_regiment_recycler_view_item,
                generateViewGroup(),
                false);
        WorkoutRegimentRecyclerViewAdapter.ViewHolder viewHolder = new WorkoutRegimentRecyclerViewAdapter.ViewHolder(binding);

        workoutRegimentRecyclerViewAdapter.onBindViewHolder(viewHolder, 0);
        binding.workoutRegimentRelativeLayout.callOnClick();

        Intent intent = ShadowApplication.getInstance().getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intent);

        assertThat(intent.getStringExtra(WORK_OUT_REGIMENT)).isEqualTo(WorkoutRegiment.WEIGHT_LIFTING.getWorkoutRegimentTitle());
        assertThat(shadowIntent.getIntentClass().getName()).isEqualTo("tmosq.com.pt.activity.PreWorkoutActivity");
    }

    private ViewGroup generateViewGroup(){
        return new ViewGroup(RuntimeEnvironment.application.getApplicationContext()) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {

            }
        };
    }
}
package tmosq.com.pt.adapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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

}
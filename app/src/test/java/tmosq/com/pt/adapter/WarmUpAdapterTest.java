package tmosq.com.pt.adapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.viewModel.WarmUpAndCoolOffExerciseViewModel;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDUCTORS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BARBELL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;

public class WarmUpAdapterTest {

    @Mock
    WarmUpAdapter.ViewHolder mockViewHolder;

    @Mock
    WarmUpAndCoolOffExerciseViewModel mockViewModel;

    private WarmUpAdapter warmUpAdapter;
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

        warmUpAdapter = new WarmUpAdapter(newArrayList(exercise));
    }

    @Test
    public void onBindViewHolder_bindToViewHolder() throws Exception {
        warmUpAdapter.onBindViewHolder(mockViewHolder, 0);

        verify(mockViewHolder).bind(exercise);
    }

    @Test
    public void getItemCount_getNumberOfExercises() throws Exception {
        assertThat(warmUpAdapter.getItemCount()).isEqualTo(1);
    }
}
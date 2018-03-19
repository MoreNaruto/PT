package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class WorkoutRegimentTest {

    @Test
    public void getWorkOutRegimentNameAlias() throws Exception {
        assertThat(WorkoutRegiment.WEIGHT_LIFTING.getWorkoutRegimentTitle()).isEqualTo("WEIGHT LIFTING");
        assertThat(WorkoutRegiment.CARDIO.getWorkoutRegimentTitle()).isEqualTo("CARDIO");
        assertThat(WorkoutRegiment.EMOM.getWorkoutRegimentTitle()).isEqualTo("EMOM");
        assertThat(WorkoutRegiment.TABATA.getWorkoutRegimentTitle()).isEqualTo("TABATA");
        assertThat(WorkoutRegiment.AMRAP.getWorkoutRegimentTitle()).isEqualTo("AMRAP");
        assertThat(WorkoutRegiment.RFT.getWorkoutRegimentTitle()).isEqualTo("RFT");
        assertThat(WorkoutRegiment.CHIPPER.getWorkoutRegimentTitle()).isEqualTo("CHIPPER");
        assertThat(WorkoutRegiment.LADDER.getWorkoutRegimentTitle()).isEqualTo("LADDER");
    }

}
package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class WorkoutRegimentTest {

    @Test
    public void getWorkOutRegimentNameAlias() {
        assertThat(WorkoutRegiment.WEIGHT_LIFTING.getWorkoutRegimentTitle()).isEqualTo("WEIGHT LIFTING");
        assertThat(WorkoutRegiment.CARDIO.getWorkoutRegimentTitle()).isEqualTo("CARDIO");
        assertThat(WorkoutRegiment.EMOM.getWorkoutRegimentTitle()).isEqualTo("EMOM");
        assertThat(WorkoutRegiment.TABATA.getWorkoutRegimentTitle()).isEqualTo("TABATA");
        assertThat(WorkoutRegiment.AMRAP.getWorkoutRegimentTitle()).isEqualTo("AMRAP");
        assertThat(WorkoutRegiment.RFT.getWorkoutRegimentTitle()).isEqualTo("RFT");
        assertThat(WorkoutRegiment.CHIPPER.getWorkoutRegimentTitle()).isEqualTo("CHIPPER");
        assertThat(WorkoutRegiment.LADDER.getWorkoutRegimentTitle()).isEqualTo("LADDER");
    }

    @Test
    public void valueOfWorkoutRegimentTitle() {
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("WEIGHT LIFTING")).isEqualTo(WorkoutRegiment.WEIGHT_LIFTING);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("CARDIO")).isEqualTo(WorkoutRegiment.CARDIO);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("EMOM")).isEqualTo(WorkoutRegiment.EMOM);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("TABATA")).isEqualTo(WorkoutRegiment.TABATA);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("AMRAP")).isEqualTo(WorkoutRegiment.AMRAP);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("RFT")).isEqualTo(WorkoutRegiment.RFT);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("CHIPPER")).isEqualTo(WorkoutRegiment.CHIPPER);
        assertThat(WorkoutRegiment.valueOfWorkoutRegimentTitle("LADDER")).isEqualTo(WorkoutRegiment.LADDER);
    }
}
package tmosq.com.pt.viewModel;

import org.junit.Before;
import org.junit.Test;

import tmosq.com.pt.model.Exercise;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDUCTORS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BARBELL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;

public class WarmUpAndCoolOffExerciseViewModelTest {
    private WarmUpAndCoolOffExerciseViewModel warmUpAndCoolOffExerciseViewModel;

    @Before
    public void setUp() throws Exception {
        warmUpAndCoolOffExerciseViewModel = new WarmUpAndCoolOffExerciseViewModel();
    }

    @Test
    public void setExercise_updateCellViewWithNoAlternateSideOrForTime() throws Exception {
        Exercise exercise = Exercise.builder()
                .workOutType(WARM_UP_AND_COOL_OFF)
                .partnerNeeded(true)
                .alternateSide(false)
                .averageSecondsPerRep(3.0)
                .bodyFocus(ABDUCTORS)
                .workout("Rollers Curls")
                .forTime(false)
                .difficulty(ADVANCED)
                .equipment(BARBELL)
                .build();

        warmUpAndCoolOffExerciseViewModel.setExercise(exercise, true);
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseRegiment.get()).isEqualTo("2 sets: 10 reps");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseDifficulty.get()).isEqualTo("Difficulty: advanced");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseWorkout.get()).isEqualTo("Rollers Curls");
        assertThat(warmUpAndCoolOffExerciseViewModel.isWarmUpField.get()).isTrue();
    }

    @Test
    public void setExercise_updateCellViewWithAlternateSidesAndNoForTime() throws Exception {
        Exercise exercise = Exercise.builder()
                .workOutType(WARM_UP_AND_COOL_OFF)
                .partnerNeeded(true)
                .alternateSide(true)
                .averageSecondsPerRep(3.0)
                .bodyFocus(ABDUCTORS)
                .workout("Rollers Curls")
                .forTime(false)
                .difficulty(ADVANCED)
                .equipment(BARBELL)
                .build();

        warmUpAndCoolOffExerciseViewModel.setExercise(exercise, true);
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseRegiment.get()).isEqualTo("2 sets: 10 reps (5 each side)");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseDifficulty.get()).isEqualTo("Difficulty: advanced");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseWorkout.get()).isEqualTo("Rollers Curls");
    }

    @Test
    public void setExercise_updateCellViewWithNoAlternateSidesAndForTime() throws Exception {
        Exercise exercise = Exercise.builder()
                .workOutType(WARM_UP_AND_COOL_OFF)
                .partnerNeeded(true)
                .alternateSide(false)
                .averageSecondsPerRep(3.0)
                .bodyFocus(ABDUCTORS)
                .workout("Rollers Curls")
                .forTime(true)
                .difficulty(ADVANCED)
                .equipment(BARBELL)
                .build();

        warmUpAndCoolOffExerciseViewModel.setExercise(exercise, true);
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseRegiment.get()).isEqualTo("2 sets: 30 seconds for each rep");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseDifficulty.get()).isEqualTo("Difficulty: advanced");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseWorkout.get()).isEqualTo("Rollers Curls");
    }

    @Test
    public void setExercise_updateCellViewWithAlternateSidesAndForTime() throws Exception {
        Exercise exercise = Exercise.builder()
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

        warmUpAndCoolOffExerciseViewModel.setExercise(exercise, true);
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseRegiment.get()).isEqualTo("2 sets: 30 seconds for each rep (15 seconds each side)");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseDifficulty.get()).isEqualTo("Difficulty: advanced");
        assertThat(warmUpAndCoolOffExerciseViewModel.exerciseWorkout.get()).isEqualTo("Rollers Curls");
    }

    @Test
    public void setExercise_isCoolOff() throws Exception {
        Exercise exercise = Exercise.builder()
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

        warmUpAndCoolOffExerciseViewModel.setExercise(exercise, false);

        assertThat(warmUpAndCoolOffExerciseViewModel.isWarmUpField.get()).isFalse();

    }
}
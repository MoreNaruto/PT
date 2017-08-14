package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.view.View;

import java.math.BigDecimal;
import java.util.Collection;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;

import static java.math.BigDecimal.ZERO;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;

public class WorkoutViewModel {
    private static final Integer NUMBER_OF_REPS = 10;
    private static final Double NUMBER_OF_SETS_FOR_REGULAR_WORKOUT = 3.0;
    private static final Double NUMBER_OF_SETS_FOR_COOL_OFF_AND_WARM_UP_WORKOUT = 2.0;
    private static final Double SECONDS_TO_REST_FOR_REGULAR_WORKOUT = 30.0 / 60.0;
    private static final Double SECONDS_TO_REST_FOR_COOL_OFF_AND_WARM_UP_WORKOUT = 15.0 / 60.0;
    private static final Double PADDING_TIME = 2.0;

    protected Collection<Exercise> filteredExercises;
    private BigDecimal lengthOfWorkout;

    public WorkoutViewModel(WorkoutActivity workoutActivity) {
        Intent intent = workoutActivity.getIntent();
        lengthOfWorkout = BigDecimal.valueOf(intent.getIntExtra(WORK_OUT_LENGTH, 60));
        filteredExercises = new ExerciseFilter(intent).filterExercises(new ExerciseSplitter(workoutActivity).generateAllExercises());
    }

    public int warmUpWorkoutVisibility() {
        return View.VISIBLE;
    }

    public String warmUpRoutine() {
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal minutesForCoolOffAndWarmUpRegiment = BigDecimal.valueOf(5.0);

        for (Exercise currentExercise : filteredExercises) {
            if (currentExercise.getWorkOutType().equals(WARM_UP_AND_COOL_OFF) && minutesForCoolOffAndWarmUpRegiment.compareTo(ZERO) != -1) {
                minutesForCoolOffAndWarmUpRegiment.subtract(estimatedTimeToDoCoolOffAndWarmUpExercise(currentExercise));
                stringBuilder.append(currentExercise.getWorkout());
            }
        }

        return stringBuilder.toString();
    }

    public int coolOffWorkoutVisibility() {
        return View.VISIBLE;
    }

    public String coolOffRoutine() {
        return "";
    }

    public String mainWorkoutRoutine() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Exercise currentExercise : filteredExercises) {
            while (lengthOfWorkout.compareTo(ZERO) != -1) {
                estimatedTimeToDoWorkoutCycle(currentExercise);
                stringBuilder.append(currentExercise.getWorkout())
                        .append(": 3 sets of 10 reps\nRest for 30 seconds in between each set\n\n");
                break;
            }
        }

        return stringBuilder.toString();
    }

    private BigDecimal estimatedTimeToDoCoolOffAndWarmUpExercise(Exercise currentExercise) {
        BigDecimal timeOfExerciseRegiment = BigDecimal.valueOf(currentExercise.getAverageSecondsPerRep() * NUMBER_OF_REPS / 60.0);
        timeOfExerciseRegiment = timeOfExerciseRegiment.multiply(BigDecimal.valueOf(NUMBER_OF_SETS_FOR_COOL_OFF_AND_WARM_UP_WORKOUT));
        timeOfExerciseRegiment = timeOfExerciseRegiment.add(BigDecimal.valueOf(SECONDS_TO_REST_FOR_COOL_OFF_AND_WARM_UP_WORKOUT));
        return timeOfExerciseRegiment;
    }

    private void estimatedTimeToDoWorkoutCycle(Exercise currentExercise) {
        BigDecimal workOutCycleTime = BigDecimal.valueOf(currentExercise.getAverageSecondsPerRep() * NUMBER_OF_REPS / 60.0);
        workOutCycleTime = workOutCycleTime.add(BigDecimal.valueOf(SECONDS_TO_REST_FOR_REGULAR_WORKOUT));
        workOutCycleTime = workOutCycleTime.multiply(BigDecimal.valueOf(NUMBER_OF_SETS_FOR_REGULAR_WORKOUT));
        workOutCycleTime = workOutCycleTime.add(BigDecimal.valueOf(PADDING_TIME));
        lengthOfWorkout = lengthOfWorkout.subtract(workOutCycleTime);
    }
}

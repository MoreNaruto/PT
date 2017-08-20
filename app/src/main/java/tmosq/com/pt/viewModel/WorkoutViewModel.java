package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.view.View;

import java.math.BigDecimal;
import java.util.List;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;

import static java.math.BigDecimal.ZERO;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;

public class WorkoutViewModel {
    private static final Integer NUMBER_OF_REPS = 10;
    private static final Double NUMBER_OF_SETS_FOR_REGULAR_WORKOUT = 3.0;
    private static final Double NUMBER_OF_SETS_FOR_COOL_OFF_AND_WARM_UP_WORKOUT = 2.0;
    private static final Double SECONDS_TO_REST_FOR_REGULAR_WORKOUT = 30.0 / 60.0;
    private static final Double SECONDS_TO_REST_FOR_COOL_OFF_AND_WARM_UP_WORKOUT = 15.0 / 60.0;
    private static final Double PADDING_TIME = 2.0;
    private final ExerciseFilter exerciseFilter;

    protected List<Exercise> filteredExercises;
    private final Intent intent;

    public WorkoutViewModel(WorkoutActivity workoutActivity) {
        intent = workoutActivity.getIntent();
        exerciseFilter = new ExerciseFilter(intent);
        filteredExercises = exerciseFilter.filterExercises(new ExerciseSplitter(workoutActivity).generateAllExercises());
    }

    public int warmUpWorkoutVisibility() {
        return View.VISIBLE;
    }

    public String warmUpRoutine() {
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal lengthOfWorkout = BigDecimal.valueOf(intent.getIntExtra(WORK_OUT_LENGTH, 60));

        if (lengthOfWorkout.compareTo(BigDecimal.valueOf(40.0)) == 1) {
            BigDecimal minutesForCoolOffAndWarmUpRegiment = BigDecimal.valueOf(5.0);
            List<Exercise> filteredWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, true);

            for (Exercise currentExercise : filteredWarmUpAndCoolOffExercises) {
                if (minutesForCoolOffAndWarmUpRegiment.compareTo(ZERO) == 1) {
                    minutesForCoolOffAndWarmUpRegiment = minutesForCoolOffAndWarmUpRegiment.subtract(estimatedTimeToDoCoolOffAndWarmUpExercise(currentExercise));
                    stringBuilder.append(currentExercise.getWorkout())
                            .append(": 2 sets of 10 reps\nRest for 15 seconds in between each set\n\n");
                } else {
                    break;
                }
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

        BigDecimal lengthOfWorkout = BigDecimal.valueOf(intent.getIntExtra(WORK_OUT_LENGTH, 60));

        for (Exercise currentExercise : filteredExercises) {
            if (lengthOfWorkout.compareTo(ZERO) == 1) {
                lengthOfWorkout = lengthOfWorkout.subtract(estimatedTimeToDoWorkoutCycle(currentExercise));
                stringBuilder.append(currentExercise.getWorkout())
                        .append(": 3 sets of 10 reps\nRest for 30 seconds in between each set\n\n");
            } else {
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

    private BigDecimal estimatedTimeToDoWorkoutCycle(Exercise currentExercise) {
        BigDecimal workOutCycleTime = BigDecimal.valueOf(currentExercise.getAverageSecondsPerRep() * NUMBER_OF_REPS / 60.0);
        workOutCycleTime = workOutCycleTime.add(BigDecimal.valueOf(SECONDS_TO_REST_FOR_REGULAR_WORKOUT));
        workOutCycleTime = workOutCycleTime.multiply(BigDecimal.valueOf(NUMBER_OF_SETS_FOR_REGULAR_WORKOUT));
        workOutCycleTime = workOutCycleTime.add(BigDecimal.valueOf(PADDING_TIME));
        return workOutCycleTime;
    }
}

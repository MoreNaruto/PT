package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;

public class WorkoutViewModel {
    private static final Integer NUMBER_OF_REPS = 10;
    private static final Double NUMBER_OF_SETS_FOR_REGULAR_WORKOUT = 3.0;
    private static final Double NUMBER_OF_SETS_FOR_COOL_OFF_AND_WARM_UP_WORKOUT = 2.0;
    private static final Double SECONDS_TO_REST_FOR_REGULAR_WORKOUT = 30.0 / 60.0;
    private static final Double SECONDS_TO_REST_FOR_COOL_OFF_AND_WARM_UP_WORKOUT = 15.0 / 60.0;
    private static final Double PADDING_TIME = 2.0;
    private static final int MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF = 40;
    private final ExerciseFilter exerciseFilter;

    protected List<Exercise> filteredExercises;
    private final Intent intent;
    private final Random random;

    public WorkoutViewModel(WorkoutActivity workoutActivity) {
        intent = workoutActivity.getIntent();
        exerciseFilter = new ExerciseFilter(intent);
        filteredExercises = exerciseFilter.filterExercises(new ExerciseSplitter(workoutActivity).generateAllExercises());

        random = new Random();
    }

    public int warmUpWorkoutVisibility() {
        return intent.getIntExtra(WORK_OUT_LENGTH, 60) > MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF ? View.VISIBLE : View.GONE;
    }

    public int coolOffWorkoutVisibility() {
        return intent.getIntExtra(WORK_OUT_LENGTH, 60) > MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF ? View.VISIBLE : View.GONE;
    }

    public String warmUpRoutine() {
        return getWarmUpAndCoolOffRoutine();
    }

    public String coolOffRoutine() {
        return getWarmUpAndCoolOffRoutine();
    }

    public String mainWorkoutRoutine() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Exercise> filteredOutWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, false);
        int numberOfExercises = filteredOutWarmUpAndCoolOffExercises.size();

        if (filteredOutWarmUpAndCoolOffExercises.isEmpty()) {
            return "There are no exercises that meet this criteria";
        }

        BigDecimal lengthOfWorkout = BigDecimal.valueOf(intent.getIntExtra(WORK_OUT_LENGTH, 60)).subtract(TEN);

        while (lengthOfWorkout.compareTo(ZERO) == 1 && !filteredOutWarmUpAndCoolOffExercises.isEmpty()) {
            final int randomIndex = random.nextInt(numberOfExercises);
            Exercise currentExercise = filteredOutWarmUpAndCoolOffExercises.get(randomIndex);
            filteredOutWarmUpAndCoolOffExercises.remove(randomIndex);
            numberOfExercises--;

            lengthOfWorkout = lengthOfWorkout.subtract(estimatedTimeToDoWorkoutCycle(currentExercise));
            stringBuilder.append(currentExercise.getWorkout())
                    .append(": 3 sets of 10 reps\n\n");
        }
        return stringBuilder.toString();
    }

    @NonNull
    private String getWarmUpAndCoolOffRoutine() {
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal lengthOfWorkout = BigDecimal.valueOf(intent.getIntExtra(WORK_OUT_LENGTH, 60));

        if (lengthOfWorkout.compareTo(BigDecimal.valueOf(MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF)) == 1) {
            List<Exercise> filteredWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, true);
            int numberOfExercises = filteredWarmUpAndCoolOffExercises.size();

            if (filteredWarmUpAndCoolOffExercises.isEmpty()) {
                return "There are no cool offs/warm up exercises that meet this criteria";
            }

            BigDecimal minutesForCoolOffAndWarmUpRegiment = BigDecimal.valueOf(5.0);

            while (minutesForCoolOffAndWarmUpRegiment.compareTo(ZERO) == 1 && !filteredWarmUpAndCoolOffExercises.isEmpty()) {
                final int randomIndex = random.nextInt(numberOfExercises);
                Exercise currentExercise = filteredWarmUpAndCoolOffExercises.get(randomIndex);
                filteredWarmUpAndCoolOffExercises.remove(randomIndex);
                numberOfExercises--;

                minutesForCoolOffAndWarmUpRegiment = minutesForCoolOffAndWarmUpRegiment.subtract(estimatedTimeToDoCoolOffAndWarmUpExercise(currentExercise));
                stringBuilder.append(currentExercise.getWorkout())
                        .append(": 2 sets of 10 reps\n\n");
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

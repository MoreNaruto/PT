package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.view.View;

import java.math.BigDecimal;
import java.util.Collection;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;

import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;

public class WorkoutViewModel {
    private static final Integer NUMBER_OF_REPS = 10;
    private static final Double NUMBER_OF_SETS = 3.0;
    private static final Double SECONDS_TO_REST = 30.0 / 60.0;
    private static final Double PADDING_TIME = 2.0;

    private final Collection<Exercise> filteredExercises;
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
        return "";
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
            while (lengthOfWorkout.compareTo(BigDecimal.ZERO) != -1) {
                estimatedTimeToDoWorkoutCycle(currentExercise);
                stringBuilder.append(currentExercise.getWorkout())
                        .append(": 3 sets of 10 reps\nRest for 30 seconds in between each set\n\n");
                break;
            }
        }

        return stringBuilder.toString();
    }

    private void estimatedTimeToDoWorkoutCycle(Exercise currentExercise) {
        BigDecimal workOutCycleTime = BigDecimal.valueOf(currentExercise.getAverageSecondsPerRep() * NUMBER_OF_REPS / 60);
        workOutCycleTime = workOutCycleTime.add(BigDecimal.valueOf(SECONDS_TO_REST));
        workOutCycleTime = workOutCycleTime.multiply(BigDecimal.valueOf(NUMBER_OF_SETS));
        workOutCycleTime = workOutCycleTime.add(BigDecimal.valueOf(PADDING_TIME));
        lengthOfWorkout = lengthOfWorkout.subtract(workOutCycleTime);
    }
}

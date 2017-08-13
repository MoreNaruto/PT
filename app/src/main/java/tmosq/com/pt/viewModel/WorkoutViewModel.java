package tmosq.com.pt.viewModel;

import android.content.Intent;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkOutType;

import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.INTERMEDIATE;

public class WorkoutViewModel {
    public static final Integer NUMBER_OF_REPS = 10;
    public static final Double NUMBER_OF_SETS = 3.0;
    public static final Double SECONDS_TO_REST = 30.0 / 60.0;
    private final Intent intent;
    private BigDecimal lengthOfWorkout;
    protected WorkoutActivity workoutActivity;
    protected List<Exercise> allExercises;

    public WorkoutViewModel(WorkoutActivity workoutActivity) {
        this.workoutActivity = workoutActivity;
        intent = workoutActivity.getIntent();
        allExercises = new ExerciseSplitter(workoutActivity).generateAllExercises();
        lengthOfWorkout = BigDecimal.valueOf(intent.getIntExtra(WORK_OUT_LENGTH, 60));
    }

    public String fullWorkout() {
        StringBuilder stringBuilder = new StringBuilder();

        Collection<Exercise> exercises = Collections2.filter(allExercises, new Predicate<Exercise>() {
            @Override
            public boolean apply(Exercise exercise) {
                return filterExercises(exercise);
            }
        });

        for (Exercise currentExercise : exercises) {
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
        lengthOfWorkout = lengthOfWorkout.subtract(workOutCycleTime);
    }

    private Boolean filterExercises(Exercise exercise) {
        return filterOutDifficulty(exercise.getDifficulty()) &&
                filterOutUnavailableEquipment(exercise.getEquipment()) &&
                filterOutWorkoutsNotInTheRegiment(exercise.getWorkOutType()) &&
                filterOutBodyPartsNotFocusedOn(exercise.getBodyFocus());
    }

    private Boolean filterOutBodyPartsNotFocusedOn(BodyFocus bodyFocus) {
        if (bodyFocus == null) {
            return false;
        }

        List<String> activeBodyFocuses = new Gson().fromJson(intent.getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES), List.class);
        return activeBodyFocuses.contains(bodyFocus.getBodyPartNameAlias());
    }

    private Boolean filterOutWorkoutsNotInTheRegiment(WorkOutType workOutType) {
        if (workOutType == null) {
            return false;
        }

        String workOutRegiment = intent.getStringExtra(WORK_OUT_REGIMENT);
        return workOutType.getWorkOutRegimentNameAliases().contains(workOutRegiment);
    }

    private Boolean filterOutUnavailableEquipment(Equipment equipment) {
        if (equipment == null) {
            return false;
        }

        List<String> excludedEquipments = new Gson().fromJson(intent.getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT), List.class);
        return !excludedEquipments.contains(equipment.getEquipmentNameAlias());
    }

    private Boolean filterOutDifficulty(Difficulty difficulty) {
        Difficulty userDifficulty = Difficulty.fromString(intent.getStringExtra(WORK_OUT_DIFFICULTY));

        if (difficulty == null) {
            return false;
        }

        if (userDifficulty.equals(BASIC)) {
            return difficulty.equals(BASIC);
        }
        if (userDifficulty.equals(INTERMEDIATE)) {
            return difficulty.equals(BASIC) || difficulty.equals(INTERMEDIATE);
        }
        return true;
    }
}

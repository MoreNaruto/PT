package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.BodyFocusExercise;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.math.BigDecimal.ONE;
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
    private static final int WORKOUT_LENGTH_IS_GREATER_THAN_ZERO = 1;
    private final ExerciseFilter exerciseFilter;
    private final int workOutLength;

    List<Exercise> filteredExercises;
    private final Intent intent;
    private final Random random;

    public WorkoutViewModel(WorkoutActivity workoutActivity) {
        intent = workoutActivity.getIntent();
        exerciseFilter = new ExerciseFilter(intent);
        filteredExercises = exerciseFilter.filterExercises(new ExerciseSplitter(workoutActivity).generateAllExercises());

        random = new Random();
        workOutLength = intent.getIntExtra(WORK_OUT_LENGTH, 60);
    }

    public int warmUpWorkoutVisibility() {
        return workOutLength > MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF ? VISIBLE : GONE;
    }

    public int coolOffWorkoutVisibility() {
        return workOutLength > MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF ? VISIBLE : GONE;
    }

    public String warmUpRoutine() {
        return getWarmUpAndCoolOffRoutine();
    }

    public String coolOffRoutine() {
        return getWarmUpAndCoolOffRoutine();
    }

    public String mainWorkoutRoutine() {
        List<Exercise> filteredOutWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, false);

        if (filteredOutWarmUpAndCoolOffExercises.isEmpty()) {
            return "There are no exercises that meet this criteria";
        }

        BigDecimal lengthOfWorkout = BigDecimal.valueOf(workOutLength)
                .subtract(timeOfWarmUpOrCoolOff().multiply(BigDecimal.valueOf(2.0)));

        BodyFocusExercise bodyFocusExercise = BodyFocusExercise.builder()
                .chosenBodyFocuses(new Gson().fromJson(intent.getStringExtra(ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES), List.class))
                .bodyFocusesWithExercises(new ArrayList<String>())
                .bodyFocusExerciseRegimentMap(new HashMap<String, List<String>>())
                .bodyFocusExerciseMap(new HashMap<String, List<Exercise>>())
                .build();

        createInitialBodyExerciseMapForActiveBodyFocuses(
                filteredOutWarmUpAndCoolOffExercises,
                bodyFocusExercise
        );

        workoutRegimentForSpecificBodyFocus(
                lengthOfWorkout,
                bodyFocusExercise
        );

        return generateFullWorkout(bodyFocusExercise);
    }

    private void workoutRegimentForSpecificBodyFocus(BigDecimal lengthOfWorkout, BodyFocusExercise bodyFocusExercise) {
        int bodyFocusIndex = 0;
        while (lengthOfWorkout.compareTo(ZERO) == WORKOUT_LENGTH_IS_GREATER_THAN_ZERO) {
            if (bodyFocusIndex >= bodyFocusExercise.getChosenBodyFocuses().size()) {
                bodyFocusIndex = 0;
            }

            while (bodyFocusExercise.getBodyFocusExerciseMap()
                    .get(bodyFocusExercise.getChosenBodyFocuses().get(bodyFocusIndex)).size() == 0) {
                bodyFocusExercise.getBodyFocusExerciseMap().remove(bodyFocusExercise.getChosenBodyFocuses().get(bodyFocusIndex));
                bodyFocusExercise.getChosenBodyFocuses().remove(bodyFocusIndex);

                if (bodyFocusExercise.getBodyFocusExerciseMap().isEmpty()) {
                    break;
                }
                if (bodyFocusIndex != 0) {
                    bodyFocusIndex--;
                }
            }

            if (bodyFocusExercise.getBodyFocusExerciseMap().isEmpty()) {
                break;
            }

            Exercise exerciseForSpecificBodyFocus = generateExerciseForSpecificBodyFocus(
                    bodyFocusIndex,
                    bodyFocusExercise
            );

            lengthOfWorkout = remainingTimeOfRegiment(lengthOfWorkout, false, exerciseForSpecificBodyFocus);

            bodyFocusIndex++;
        }
    }

    private void createInitialBodyExerciseMapForActiveBodyFocuses(List<Exercise> filteredOutWarmUpAndCoolOffExercises, BodyFocusExercise bodyFocusExercise) {
        List<String> initialActiveBodyFocuses = new ArrayList<>(bodyFocusExercise.getChosenBodyFocuses());
        for (String activeBodyFocusString : initialActiveBodyFocuses) {
            List<Exercise> bodyFocusExercises = exerciseFilter.filterForSpecificBodyFocus(filteredOutWarmUpAndCoolOffExercises, BodyFocus.fromString(activeBodyFocusString));
            if (bodyFocusExercises.isEmpty()) {
                bodyFocusExercise.getChosenBodyFocuses().remove(activeBodyFocusString);
            } else {
                bodyFocusExercise.getBodyFocusExerciseMap().put(activeBodyFocusString, bodyFocusExercises);
                bodyFocusExercise.getBodyFocusExerciseRegimentMap().put(activeBodyFocusString, new ArrayList<String>());
                bodyFocusExercise.getBodyFocusesWithExercises().add(activeBodyFocusString);
            }
        }
    }

    private String generateFullWorkout(BodyFocusExercise bodyFocusExercise) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String activeBodyFocusString : bodyFocusExercise.getBodyFocusesWithExercises()) {
            List<String> allWorkoutsForSpecificBodyFocus = bodyFocusExercise.getBodyFocusExerciseRegimentMap().get(activeBodyFocusString);
            for (String workoutForSpecificBodyFocus : allWorkoutsForSpecificBodyFocus) {
                stringBuilder.append(workoutForSpecificBodyFocus);
            }
        }
        return stringBuilder.toString();
    }

    @NonNull
    private Exercise generateExerciseForSpecificBodyFocus(int bodyFocusIndex, BodyFocusExercise bodyFocusExercise) {
        Exercise currentExercise = getBodyFocusExercise(
                bodyFocusIndex,
                bodyFocusExercise.getChosenBodyFocuses(),
                bodyFocusExercise.getBodyFocusExerciseMap()
        );
        String exerciseRegiment = currentExercise.getWorkout() +
                ": 3 sets of 10 reps" +
                generateAlternateSideRepetitionString(currentExercise) +
                "\n\n";

        List<String> existingExerciseRegiment =
                bodyFocusExercise.getBodyFocusExerciseRegimentMap().get(bodyFocusExercise.getChosenBodyFocuses().get(bodyFocusIndex));
        existingExerciseRegiment.add(exerciseRegiment);

        bodyFocusExercise.getBodyFocusExerciseRegimentMap()
                .put(bodyFocusExercise.getChosenBodyFocuses().get(bodyFocusIndex), existingExerciseRegiment);
        return currentExercise;
    }

    private Exercise getBodyFocusExercise(int bodyFocusIndex, List<String> activeBodyFocuses, Map<String, List<Exercise>> bodyFocusExerciseMap) {
        List<Exercise> bodyFocusExercises = bodyFocusExerciseMap.get(activeBodyFocuses.get(bodyFocusIndex));

        final int randomIndex = random.nextInt(bodyFocusExercises.size());
        Exercise currentExercise = bodyFocusExercises.get(randomIndex);
        bodyFocusExercises.remove(randomIndex);
        bodyFocusExerciseMap.put(activeBodyFocuses.get(bodyFocusIndex), bodyFocusExercises);
        return currentExercise;
    }

    @NonNull
    private String getWarmUpAndCoolOffRoutine() {
        StringBuilder stringBuilder = new StringBuilder();

        List<Exercise> filteredWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, true);

        if (filteredWarmUpAndCoolOffExercises.isEmpty()) {
            return "There are no cool offs/warm up exercises that meet this criteria";
        }

        createWarmUpAndCoolOffWorkoutRegiment(stringBuilder, filteredWarmUpAndCoolOffExercises);
        return stringBuilder.toString();
    }

    private void createWarmUpAndCoolOffWorkoutRegiment(StringBuilder stringBuilder, List<Exercise> filteredWarmUpAndCoolOffExercises) {
        BigDecimal remainingTimeOfWarmUpAndCoolOffRegiment = timeOfWarmUpOrCoolOff();

        while (remainingTimeOfWarmUpAndCoolOffRegiment.compareTo(ZERO) == WORKOUT_LENGTH_IS_GREATER_THAN_ZERO && !filteredWarmUpAndCoolOffExercises.isEmpty()) {
            final int randomIndex = random.nextInt(filteredWarmUpAndCoolOffExercises.size());
            Exercise currentExercise = filteredWarmUpAndCoolOffExercises.get(randomIndex);
            filteredWarmUpAndCoolOffExercises.remove(randomIndex);

            remainingTimeOfWarmUpAndCoolOffRegiment = remainingTimeOfRegiment(
                    remainingTimeOfWarmUpAndCoolOffRegiment,
                    true,
                    currentExercise);

            stringBuilder
                    .append(currentExercise.getWorkout())
                    .append(": 2 sets of 10 reps")
                    .append(generateAlternateSideRepetitionString(currentExercise))
                    .append("\n\n");
        }
    }

    private BigDecimal remainingTimeOfRegiment(BigDecimal minutesForCoolOffAndWarmUpRegiment,
                                               Boolean isEstimatingTimeForCoolOff,
                                               Exercise currentExercise) {


        minutesForCoolOffAndWarmUpRegiment = minutesForCoolOffAndWarmUpRegiment
                .subtract(isEstimatingTimeForCoolOff ?
                        estimatedTimeToDoCoolOffAndWarmUpExercise(currentExercise) :
                        estimatedTimeToDoWorkoutCycle(currentExercise));

        return minutesForCoolOffAndWarmUpRegiment;
    }

    private BigDecimal timeOfWarmUpOrCoolOff() {
        if (workOutLength <= MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF) {
            return ZERO;
        }
        BigDecimal timeOfFullWorkout = BigDecimal.valueOf(workOutLength);
        BigDecimal timeOfTypicalFullWorkout = BigDecimal.valueOf(60.0);
        BigDecimal timeOfTypicalWarmUpAndCoolOff = BigDecimal.valueOf(10.0);

        return ONE.multiply(timeOfFullWorkout)
                .multiply(timeOfTypicalWarmUpAndCoolOff)
                .divide(timeOfTypicalFullWorkout, 3, 1)
                .divide(BigDecimal.valueOf(2.0), 2, 1);
    }

    @NonNull
    private String generateAlternateSideRepetitionString(Exercise currentExercise) {
        return currentExercise.getAlternateSide() ? "(5 reps each side)" : "";
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

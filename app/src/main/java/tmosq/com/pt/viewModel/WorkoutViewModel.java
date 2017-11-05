package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.BodyFocusExercise;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.common.collect.Lists.newArrayList;
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

    public ObservableField<List<Exercise>> warmUpExercises = new ObservableField<>();
    public ObservableField<List<Exercise>> coolOffExercises = new ObservableField<>();
    public ObservableField<List<Exercise>> mainWorkoutExercises = new ObservableField<>();
    public ObservableInt warmUpAndCoolOffEmptyVisibility = new ObservableInt(GONE);
    public ObservableInt mainWorkoutEmptyVisibility = new ObservableInt(GONE);
    public ObservableInt warmUpAndCoolOffListItemsVisibility = new ObservableInt(VISIBLE);
    public ObservableInt mainWorkoutListItemsVisibility = new ObservableInt(VISIBLE);

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

    public void generateAllExercises() {
        warmUpRoutine();
        coolOffRoutine();
        mainWorkoutRoutine();
    }

    private void warmUpRoutine() {
        getWarmUpOrCoolOffExercises(true);
    }

    private void coolOffRoutine() {
        getWarmUpOrCoolOffExercises(false);
    }

    private void mainWorkoutRoutine() {
        List<Exercise> filteredOutWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, false);

        if (filteredOutWarmUpAndCoolOffExercises.isEmpty()) {
            mainWorkoutEmptyVisibility.set(VISIBLE);
            mainWorkoutListItemsVisibility.set(GONE);
            mainWorkoutExercises.set(new ArrayList<Exercise>());
            return;
        }
        mainWorkoutEmptyVisibility.set(GONE);
        mainWorkoutListItemsVisibility.set(VISIBLE);

        BigDecimal lengthOfWorkout = BigDecimal.valueOf(workOutLength)
                .subtract(timeOfWarmUpOrCoolOff().multiply(BigDecimal.valueOf(2.0)));

        BodyFocusExercise bodyFocusExercise = BodyFocusExercise.builder()
                .chosenBodyFocuses(new Gson().fromJson(intent.getStringExtra(ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES), List.class))
                .bodyFocusesWithExercises(new ArrayList<String>())
                .bodyFocusExercisesForWorkout(new HashMap<String, List<Exercise>>())
                .bodyFocusExerciseMap(new HashMap<String, List<Exercise>>())
                .build();

        createInitialBodyExerciseMapForActiveBodyFocuses(
                filteredOutWarmUpAndCoolOffExercises,
                bodyFocusExercise
        );

        addMainWorkoutToList(lengthOfWorkout, bodyFocusExercise);

    }

    private void createInitialBodyExerciseMapForActiveBodyFocuses(List<Exercise> filteredOutWarmUpAndCoolOffExercises, BodyFocusExercise bodyFocusExercise) {
        List<String> initialActiveBodyFocuses = new ArrayList<>(bodyFocusExercise.getChosenBodyFocuses());
        for (String activeBodyFocusString : initialActiveBodyFocuses) {
            List<Exercise> bodyFocusExercises = exerciseFilter.filterForSpecificBodyFocus(filteredOutWarmUpAndCoolOffExercises, BodyFocus.fromString(activeBodyFocusString));
            if (bodyFocusExercises.isEmpty()) {
                bodyFocusExercise.getChosenBodyFocuses().remove(activeBodyFocusString);
            } else {
                bodyFocusExercise.getBodyFocusExerciseMap().put(activeBodyFocusString, bodyFocusExercises);
                bodyFocusExercise.getBodyFocusExercisesForWorkout().put(activeBodyFocusString, new ArrayList<Exercise>());
                bodyFocusExercise.getBodyFocusesWithExercises().add(activeBodyFocusString);
            }
        }
    }

    private void getWarmUpOrCoolOffExercises(boolean isWarmUp) {
        if (workOutLength < MINIMUM_MINUTES_TO_INCLUDE_WARM_UP_AND_COOL_OFF) {
            warmUpAndCoolOffEmptyVisibility.set(VISIBLE);
            warmUpAndCoolOffListItemsVisibility.set(GONE);
            warmUpExercises.set(new ArrayList<Exercise>());
            coolOffExercises.set(new ArrayList<Exercise>());
            return;
        }

        List<Exercise> filteredWarmUpAndCoolOffExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(filteredExercises, true);

        if (filteredWarmUpAndCoolOffExercises.isEmpty()) {
            warmUpAndCoolOffEmptyVisibility.set(VISIBLE);
            warmUpAndCoolOffListItemsVisibility.set(GONE);
            warmUpExercises.set(new ArrayList<Exercise>());
            coolOffExercises.set(new ArrayList<Exercise>());
            return;
        }

        warmUpAndCoolOffEmptyVisibility.set(GONE);
        warmUpAndCoolOffListItemsVisibility.set(VISIBLE);
        addWarmUpExercisesToList(isWarmUp, filteredWarmUpAndCoolOffExercises);
    }

    private void addWarmUpExercisesToList(boolean isWarmUp, List<Exercise> filteredWarmUpAndCoolOffExercises) {
        BigDecimal remainingTimeOfWarmUpAndCoolOffRegiment = timeOfWarmUpOrCoolOff();
        List<Exercise> exercises = new ArrayList<>();

        while (remainingTimeOfWarmUpAndCoolOffRegiment.compareTo(ZERO) == WORKOUT_LENGTH_IS_GREATER_THAN_ZERO && !filteredWarmUpAndCoolOffExercises.isEmpty()) {
            final int randomIndex = random.nextInt(filteredWarmUpAndCoolOffExercises.size());
            Exercise currentExercise = filteredWarmUpAndCoolOffExercises.get(randomIndex);
            filteredWarmUpAndCoolOffExercises.remove(randomIndex);

            remainingTimeOfWarmUpAndCoolOffRegiment = remainingTimeOfRegiment(
                    remainingTimeOfWarmUpAndCoolOffRegiment,
                    true,
                    currentExercise);

            exercises.add(currentExercise);
        }
        if (isWarmUp) {
            warmUpExercises.set(exercises);
        } else {
            coolOffExercises.set(exercises);
        }
    }

    private void addMainWorkoutToList(BigDecimal lengthOfWorkout, BodyFocusExercise bodyFocusExercise) {
        if (bodyFocusExercise.getBodyFocusExerciseMap().isEmpty()) {
            mainWorkoutEmptyVisibility.set(VISIBLE);
            mainWorkoutListItemsVisibility.set(GONE);
            mainWorkoutExercises.set(new ArrayList<Exercise>());
            return;
        }

        mainWorkoutEmptyVisibility.set(GONE);
        mainWorkoutListItemsVisibility.set(VISIBLE);

        pullingOutExercisesForWorkout(bodyFocusExercise, lengthOfWorkout);

        List<Exercise> orderedExercisesByBodyFocus = new ArrayList<>();
        for (String bodyFocus : bodyFocusExercise.getChosenBodyFocuses()) {
            orderedExercisesByBodyFocus.addAll(bodyFocusExercise.getBodyFocusExercisesForWorkout().get(bodyFocus));
        }

        mainWorkoutExercises.set(orderedExercisesByBodyFocus);
    }

    private void pullingOutExercisesForWorkout(BodyFocusExercise bodyFocusExercise, BigDecimal lengthOfWorkout) {
        BigDecimal remainingTimeForMainWorkoutRegiment = lengthOfWorkout;
        int numberOfActiveBodyFocuses = bodyFocusExercise.getBodyFocusExerciseMap().size();
        int currentActiveBodyFocusIndex = 0;

        while (remainingTimeForMainWorkoutRegiment.compareTo(ZERO) == WORKOUT_LENGTH_IS_GREATER_THAN_ZERO && !bodyFocusExercise.getBodyFocusExerciseMap().isEmpty()) {
            if (currentActiveBodyFocusIndex == numberOfActiveBodyFocuses) {
                currentActiveBodyFocusIndex = 0;
            }

            String currentBodyFocus = bodyFocusExercise.getBodyFocusesWithExercises().get(currentActiveBodyFocusIndex);
            List<Exercise> currentBodyFocusExercises = bodyFocusExercise.getBodyFocusExerciseMap()
                    .get(currentBodyFocus);

            if (currentBodyFocusExercises.isEmpty()) {
                bodyFocusExercise.getBodyFocusExerciseMap()
                        .remove(currentBodyFocus);
                bodyFocusExercise.getBodyFocusesWithExercises().remove(currentActiveBodyFocusIndex);
                numberOfActiveBodyFocuses--;
                currentActiveBodyFocusIndex--;
            } else {
                final int randomIndex = random.nextInt(currentBodyFocusExercises.size());

                List<Exercise> currentBodyFocusExercisesForWorkout = bodyFocusExercise.getBodyFocusExercisesForWorkout().get(currentBodyFocus);
                Exercise currentExercise = currentBodyFocusExercises.get(randomIndex);

                currentBodyFocusExercises.remove(randomIndex);
                currentBodyFocusExercisesForWorkout.add(currentExercise);

                bodyFocusExercise.getBodyFocusExerciseMap().put(currentBodyFocus, currentBodyFocusExercises);
                bodyFocusExercise.getBodyFocusExercisesForWorkout().put(currentBodyFocus, currentBodyFocusExercisesForWorkout);

                remainingTimeForMainWorkoutRegiment = remainingTimeOfRegiment(remainingTimeForMainWorkoutRegiment, false, currentExercise);
            }
            currentActiveBodyFocusIndex++;
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

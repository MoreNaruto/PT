package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.Exercise;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;
import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDOMINALS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDUCTORS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.BICEPS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.CALVES;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.FOREARMS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.INTERMEDIATE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BANDS;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BICYCLE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.CHAIR;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.FOAM_ROLL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.BODY;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.POWER_WEIGHT;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WEIGHTED_MOVEMENTS;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class ExerciseFilterTest {
    private ExerciseFilter exerciseFilter;

    @Before
    public void setUp() throws Exception {
        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(getInitialIntent()).create().get();

        exerciseFilter = new ExerciseFilter(workoutActivity.getIntent());
    }

    @Test
    public void filterExercises_whenDifficultyIsAdvanced_allDifficultiesAreValid() throws Exception {
        Exercise basicExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise intermediateExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise advancedExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(basicExercise, intermediateExercise, advancedExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertTrue(filteredExercises.contains(basicExercise));
        assertTrue(filteredExercises.contains(intermediateExercise));
        assertTrue(filteredExercises.contains(advancedExercise));
    }

    @Test
    public void filterExercises_whenDifficultyIsIntermediate_onlyIntermediateAndBasicDifficultiesAreValid() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_DIFFICULTY, "intermediate");

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        exerciseFilter = new ExerciseFilter(workoutActivity.getIntent());

        Exercise basicExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise intermediateExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise advancedExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(basicExercise, intermediateExercise, advancedExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertTrue(filteredExercises.contains(basicExercise));
        assertTrue(filteredExercises.contains(intermediateExercise));
        assertFalse(filteredExercises.contains(advancedExercise));
    }

    @Test
    public void filterExercises_whenDifficultyIsBasic_onlyBasicDifficultiesAreValid() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_DIFFICULTY, "basic");

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        exerciseFilter = new ExerciseFilter(workoutActivity.getIntent());

        Exercise basicExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise intermediateExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise advancedExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(basicExercise, intermediateExercise, advancedExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertTrue(filteredExercises.contains(basicExercise));
        assertFalse(filteredExercises.contains(intermediateExercise));
        assertFalse(filteredExercises.contains(advancedExercise));
    }

    @Test
    public void filterExercises_filterOutExercisesThatRequireExcludedEquipment() throws Exception {
        Exercise bandExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise chairExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise foamRollExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise bicycleExercise = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(bandExercise, chairExercise, foamRollExercise, bicycleExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertFalse(filteredExercises.contains(bandExercise));
        assertTrue(filteredExercises.contains(chairExercise));
        assertTrue(filteredExercises.contains(foamRollExercise));
        assertFalse(filteredExercises.contains(bicycleExercise));
    }

    @Test
    public void filterExercises_filterOutWorkOutTypeNotIncludedInTheWorkOutRegiment() throws Exception {
        Exercise bodyExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise powerWeightExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(POWER_WEIGHT).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise weightedMovementExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WEIGHTED_MOVEMENTS).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise warmUpAndCoolOffMovementExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(bodyExercise, powerWeightExercise, weightedMovementExercise, warmUpAndCoolOffMovementExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertTrue(filteredExercises.contains(bodyExercise));
        assertFalse(filteredExercises.contains(powerWeightExercise));
        assertTrue(filteredExercises.contains(weightedMovementExercise));
        assertTrue(filteredExercises.contains(warmUpAndCoolOffMovementExercise));
    }

    @Test
    public void filterExercises_filterOutExercisesThatAreNotFocusedOnTheListedBodyParts() throws Exception {
        Exercise abdominalExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise bicepExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(BICEPS).averageSecondsPerRep(5.0).build();

        Exercise abductorExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).build();

        Exercise calvesExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(CALVES).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(abdominalExercise, abductorExercise, bicepExercise, calvesExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertTrue(filteredExercises.contains(abdominalExercise));
        assertFalse(filteredExercises.contains(bicepExercise));
        assertTrue(filteredExercises.contains(abductorExercise));
        assertFalse(filteredExercises.contains(calvesExercise));
    }

    @Test
    public void filterWarmUpAndCoolOffExercises_filterOutNonWarmUpAndCoolOffExercises() throws Exception {
        Exercise abdominalExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise bicepExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(POWER_WEIGHT).forTime(false)
                .bodyFocus(BICEPS).averageSecondsPerRep(5.0).build();

        Exercise abductorExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(POWER_WEIGHT).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).build();

        Exercise calvesExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(CALVES).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(abdominalExercise, abductorExercise, bicepExercise, calvesExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(exercises, true);

        assertTrue(filteredExercises.contains(abdominalExercise));
        assertFalse(filteredExercises.contains(bicepExercise));
        assertFalse(filteredExercises.contains(abductorExercise));
        assertTrue(filteredExercises.contains(calvesExercise));
    }

    @Test
    public void filterWarmUpAndCoolOffExercises_filterOutWarmUpAndCoolOffExercises() throws Exception {
        Exercise abdominalExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).build();

        Exercise bicepExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(POWER_WEIGHT).forTime(false)
                .bodyFocus(BICEPS).averageSecondsPerRep(5.0).build();

        Exercise abductorExercise = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(POWER_WEIGHT).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).build();

        Exercise calvesExercise = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(CALVES).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(abdominalExercise, abductorExercise, bicepExercise, calvesExercise);

        List<Exercise> filteredExercises = exerciseFilter.filterWarmUpAndCoolOffExercises(exercises, false);

        assertFalse(filteredExercises.contains(abdominalExercise));
        assertTrue(filteredExercises.contains(bicepExercise));
        assertTrue(filteredExercises.contains(abductorExercise));
        assertFalse(filteredExercises.contains(calvesExercise));
    }

    @Test
    public void filterPartnerExercises_whenUserDoesNotHavePartner_filterOutPartnerExercises() throws Exception {
        Exercise abdominalExerciseWithPartnerNeeded = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).partnerNeeded(true).averageSecondsPerRep(5.0).build();

        Exercise forearmsExerciseWithoutPartnerNeeded = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(FOREARMS).partnerNeeded(false).averageSecondsPerRep(5.0).build();

        Exercise abductorExerciseWithPartnerNeeded = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDUCTORS).partnerNeeded(true).averageSecondsPerRep(5.0).build();

        Exercise abdominalExerciseWithOutPartnerNeeded = Exercise.builder().equipment(CHAIR).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).partnerNeeded(false).averageSecondsPerRep(5.0).build();

        ArrayList<Exercise> exercises = newArrayList(abdominalExerciseWithPartnerNeeded, abductorExerciseWithPartnerNeeded,
                forearmsExerciseWithoutPartnerNeeded, abdominalExerciseWithOutPartnerNeeded);

        List<Exercise> filteredExercises = exerciseFilter.filterExercises(exercises);

        assertFalse(filteredExercises.contains(abdominalExerciseWithPartnerNeeded));
        assertTrue(filteredExercises.contains(forearmsExerciseWithoutPartnerNeeded));
        assertFalse(filteredExercises.contains(abductorExerciseWithPartnerNeeded));
        assertTrue(filteredExercises.contains(abdominalExerciseWithOutPartnerNeeded));
    }

    @NonNull
    private Intent getInitialIntent() {
        Intent initialIntent = new Intent();
        initialIntent.putExtra(WORK_OUT_REGIMENT, "cross fit");
        initialIntent.putExtra(WORK_OUT_LENGTH, 75);
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "advanced");
        initialIntent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(newArrayList("bands", "barbell", "bicycle", "exercise ball")));
        initialIntent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList("abdominals", "abductors", "forearms")));
        initialIntent.putExtra(HAS_PARTNER, false);
        return initialIntent;
    }
}
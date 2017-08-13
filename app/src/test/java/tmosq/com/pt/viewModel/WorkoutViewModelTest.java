package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.Exercise;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDOMINALS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDUCTORS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.CALVES;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.CHEST;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.FOREARMS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.INTERMEDIATE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BANDS;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BARBELL;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.CHAIR;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.FOAM_ROLL;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.KETTLEBELL;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.MACHINE;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.BODY;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.POWER_WEIGHT;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WEIGHTED_MOVEMENTS;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class WorkoutViewModelTest {
    private WorkoutViewModel workoutViewModel;

    @Test
    public void fullWorkout_whenAllWorkoutsPertainToCriteria_getAllWorkout() throws Exception {
        Intent initialIntent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";

        Exercise firstExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(KETTLEBELL).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(rockers).build();

        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise);

        assertTrue(workoutViewModel.fullWorkout().contains(monkeyBars));
        assertTrue(workoutViewModel.fullWorkout().contains(kickUps));
        assertTrue(workoutViewModel.fullWorkout().contains(rockers));
    }

    @Test
    public void fullWorkout_whenBasicWorkoutIsUsed_onlyRetrieveBasicWorkouts() throws Exception {
        Intent initialIntent = getInitialIntent();
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "basic");

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";
        String jobbers = "jobbers";

        Exercise firstExercise = Exercise.builder().equipment(CHAIR).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(KETTLEBELL).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(rockers).build();
        Exercise fourthExercise = Exercise.builder().equipment(MACHINE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(jobbers).build();
        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise, fourthExercise);

        String filteredWorkoutStrings = workoutViewModel.fullWorkout();

        assertTrue(filteredWorkoutStrings.contains(monkeyBars));
        assertFalse(filteredWorkoutStrings.contains(kickUps));
        assertTrue(filteredWorkoutStrings.contains(rockers));
        assertFalse(filteredWorkoutStrings.contains(jobbers));
    }

    @Test
    public void fullWorkout_whenEquipmentIsNotAvailable_onlyRetrieveWorkoutsWithEquipment() throws Exception {
        Intent initialIntent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";
        String jobbers = "jobbers";

        Exercise firstExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(BARBELL).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(rockers).build();
        Exercise fourthExercise = Exercise.builder().equipment(MACHINE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(jobbers).build();
        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise, fourthExercise);

        String filteredWorkoutStrings = workoutViewModel.fullWorkout();

        assertFalse(filteredWorkoutStrings.contains(monkeyBars));
        assertTrue(filteredWorkoutStrings.contains(kickUps));
        assertFalse(filteredWorkoutStrings.contains(rockers));
        assertTrue(filteredWorkoutStrings.contains(jobbers));
    }

    @Test
    public void fullWorkout_whenWorkoutIsNotIncludedInTheRegiment_onlyRetrieveWorkoutsInThatRegiment() throws Exception {
        Intent initialIntent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";
        String jobbers = "jobbers";

        Exercise firstExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(BASIC).workOutType(POWER_WEIGHT).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDUCTORS).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(MACHINE).difficulty(BASIC).workOutType(WEIGHTED_MOVEMENTS).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(rockers).build();
        Exercise fourthExercise = Exercise.builder().equipment(MACHINE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(jobbers).build();
        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise, fourthExercise);

        String filteredWorkoutStrings = workoutViewModel.fullWorkout();

        assertFalse(filteredWorkoutStrings.contains(monkeyBars));
        assertTrue(filteredWorkoutStrings.contains(kickUps));
        assertTrue(filteredWorkoutStrings.contains(rockers));
        assertTrue(filteredWorkoutStrings.contains(jobbers));
    }

    @Test
    public void fullWorkout_whenWorkoutBodyPartIsFocused_onlyRetrieveWorkoutsForThoseBodyParts() throws Exception {
        Intent initialIntent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";
        String jobbers = "jobbers";

        Exercise firstExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(CALVES).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(MACHINE).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(CHEST).averageSecondsPerRep(5.0).workout(rockers).build();
        Exercise fourthExercise = Exercise.builder().equipment(MACHINE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(jobbers).build();
        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise, fourthExercise);

        String filteredWorkoutStrings = workoutViewModel.fullWorkout();

        assertTrue(filteredWorkoutStrings.contains(monkeyBars));
        assertFalse(filteredWorkoutStrings.contains(kickUps));
        assertFalse(filteredWorkoutStrings.contains(rockers));
        assertTrue(filteredWorkoutStrings.contains(jobbers));
    }

    @Test
    public void fullWorkout_createFullWorkout_withRepsAndSets() throws Exception {
        Intent initialIntent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";
        String jobbers = "jobbers";

        Exercise firstExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(MACHINE).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(rockers).build();
        Exercise fourthExercise = Exercise.builder().equipment(MACHINE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(jobbers).build();
        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise, fourthExercise);

        String filteredWorkoutStrings = workoutViewModel.fullWorkout();

        assertTrue(filteredWorkoutStrings.contains("3 sets of 10 reps\nRest for 30 seconds in between each set\n\n"));
    }

    @Test
    public void fullWorkout_whenTimeLimitIsExceeded_shouldOnlyHaveSomeOfTheExercises() throws Exception {
        Intent initialIntent = getInitialIntent();
        initialIntent.putExtra(WORK_OUT_LENGTH, 5);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(initialIntent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String monkeyBars = "monkey bars";
        String kickUps = "kick ups";
        String rockers = "rockers";
        String jobbers = "jobbers";

        Exercise firstExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(monkeyBars).build();
        Exercise secondExercise = Exercise.builder().equipment(FOAM_ROLL).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(kickUps).build();
        Exercise thirdExercise = Exercise.builder().equipment(MACHINE).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(rockers).build();
        Exercise fourthExercise = Exercise.builder().equipment(MACHINE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(FOREARMS).averageSecondsPerRep(5.0).workout(jobbers).build();
        workoutViewModel.allExercises = newArrayList(firstExercise, secondExercise, thirdExercise, fourthExercise);

        String filteredWorkoutStrings = workoutViewModel.fullWorkout();

        assertFalse(filteredWorkoutStrings.contains(monkeyBars) &&
                filteredWorkoutStrings.contains(kickUps) &&
                filteredWorkoutStrings.contains(rockers) &&
                filteredWorkoutStrings.contains(jobbers));
    }

    @NonNull
    private Intent getInitialIntent() {
        Intent initialIntent = new Intent();
        initialIntent.putExtra(WORK_OUT_REGIMENT, "cross fit");
        initialIntent.putExtra(WORK_OUT_LENGTH, 75);
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "advanced");
        initialIntent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(newArrayList("bands", "barbell", "bicycle", "exercise ball")));
        initialIntent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList("abdominals", "abductors", "forearms")));
        return initialIntent;
    }
}
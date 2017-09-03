package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import tmosq.com.pt.BuildConfig;
import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.Exercise;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDOMINALS;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.ADVANCED;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.INTERMEDIATE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BANDS;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.BICYCLE;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.CHAIR;
import static tmosq.com.pt.model.exercise_support_enums.Equipment.FOAM_ROLL;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.BODY;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "src/main/AndroidManifest.xml", sdk = 21)
public class WorkoutViewModelTest {
    private WorkoutViewModel workoutViewModel;

    @Before
    public void setUp() throws Exception {
        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(getInitialIntent()).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);
    }

    @Test
    public void warmUpRoutine_onlyGetCoolOffAndWarmUpExercises() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(lowTides).build();

        Exercise nonWarmUpExerciseOne = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(jetPacks).build();

        Exercise nonWarmUpExerciseTwo = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(morningKicks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo, nonWarmUpExerciseOne, nonWarmUpExerciseTwo);

        assertTrue(workoutViewModel.warmUpRoutine().contains(hyperRocks));
        assertTrue(workoutViewModel.warmUpRoutine().contains(lowTides));
        assertFalse(workoutViewModel.warmUpRoutine().contains(jetPacks));
        assertFalse(workoutViewModel.warmUpRoutine().contains(morningKicks));
    }

    @Test
    public void warmUpRoutine_shouldNotHaveAnyMoreWarmUpAndCoolOffExercisesAfterFiveMinutes() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";
        String starDays = "star days";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(hyperRocks).build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(lowTides).build();

        Exercise warmUpExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(jetPacks).build();

        Exercise warmUpExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(morningKicks).build();

        Exercise warmUpExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(starDays).build();


        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo,
                warmUpExerciseThree, warmUpExerciseFour, warmUpExerciseFive);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();
        assertFalse(warmUpRoutine.contains(hyperRocks) &&
                warmUpRoutine.contains(lowTides) &&
                warmUpRoutine.contains(jetPacks) &&
                warmUpRoutine.contains(morningKicks) &&
                warmUpRoutine.contains(starDays));
    }

    @Test
    public void warmUpRoutine_shouldHaveAllWarmUpAndCoolOffExercisesAfterSevenMinutes() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";
        String starDays = "star days";

        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 90);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise coolOffExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(hyperRocks).build();

        Exercise coolOffExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(lowTides).build();

        Exercise coolOffExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(jetPacks).build();

        Exercise coolOffExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(morningKicks).build();

        Exercise coolOffExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(starDays).build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExerciseOne, coolOffExerciseTwo,
                coolOffExerciseThree, coolOffExerciseFour, coolOffExerciseFive);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();
        assertTrue(warmUpRoutine.contains(hyperRocks) &&
                warmUpRoutine.contains(lowTides) &&
                warmUpRoutine.contains(jetPacks) &&
                warmUpRoutine.contains(morningKicks) &&
                warmUpRoutine.contains(starDays));
    }

    @Test
    public void warmRoutine_shouldHaveAStringContainingTheAmountForWarmUp() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();

        assertTrue(warmUpRoutine.contains(": 2 sets of 10 reps"));
    }

    @Test
    public void warmUpRoutine_ifNoCoolOffOrWarmUpRoutineExists_thenDisplayCoolOffAndWarmUpRoutineAreNotAvailableMessage() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise nonCoolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(nonCoolOffExercise);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();

        assertTrue(warmUpRoutine.contains("There are no cool offs/warm up exercises that meet this criteria"));
    }

    @Test
    public void warmUpWorkoutVisibility_ifWorkoutRoutineIsLessThanOrEqualTo40Minutes_thenWarmUpWorkoutShouldBeGone() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 40);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        assertEquals(workoutViewModel.warmUpWorkoutVisibility(), View.GONE);
    }

    @Test
    public void warmUpWorkoutVisibility_ifWorkoutRoutineIsMoreThan40Minutes_thenWarmUpWorkoutShouldBeVisible() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 50);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        assertEquals(workoutViewModel.warmUpWorkoutVisibility(), View.VISIBLE);
    }

    @Test
    public void warmUpRoutine_noDuplicateWorkouts() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise warmUpExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExercise);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();

        assertFalse(warmUpRoutine.replaceFirst(hyperRocks, "").contains(hyperRocks));
    }

    @Test
    public void warmUpRoutine_alternateSides() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(true).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();

        assertTrue(warmUpRoutine.contains("(5 reps each side)"));
    }

    @Test
    public void coolOffWorkoutVisibility_ifWorkoutRoutineIsLessThanOrEqualTo40Minutes_thenCoolOffWorkoutShouldBeGone() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 40);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        assertEquals(workoutViewModel.coolOffWorkoutVisibility(), View.GONE);
    }

    @Test
    public void coolOffWorkoutVisibility_ifWorkoutRoutineIsMoreThan40Minutes_thenCoolOffWorkoutShouldBeVisible() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 50);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        assertEquals(workoutViewModel.coolOffWorkoutVisibility(), View.VISIBLE);
    }

    @Test
    public void coolOffRoutine_onlyGetCoolOffAndWarmUpExercises() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";

        Exercise coolOffExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        Exercise coolOffExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(lowTides).build();

        Exercise nonCoolOffExerciseOne = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(jetPacks).build();

        Exercise nonCoolOffExerciseTwo = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(morningKicks).build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExerciseOne, coolOffExerciseTwo, nonCoolOffExerciseOne, nonCoolOffExerciseTwo);

        assertTrue(workoutViewModel.coolOffRoutine().contains(hyperRocks));
        assertTrue(workoutViewModel.coolOffRoutine().contains(lowTides));
        assertFalse(workoutViewModel.coolOffRoutine().contains(jetPacks));
        assertFalse(workoutViewModel.coolOffRoutine().contains(morningKicks));
    }

    @Test
    public void coolOffRoutine_shouldNotHaveAnyMoreWarmUpAndCoolOffExercisesAfterFiveMinutes() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";
        String starDays = "star days";

        Exercise coolOffExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(hyperRocks).build();

        Exercise coolOffExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(lowTides).build();

        Exercise coolOffExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(jetPacks).build();

        Exercise coolOffExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(morningKicks).build();

        Exercise coolOffExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(starDays).build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExerciseOne, coolOffExerciseTwo,
                coolOffExerciseThree, coolOffExerciseFour, coolOffExerciseFive);

        String coolOffRoutine = workoutViewModel.coolOffRoutine();
        assertFalse(coolOffRoutine.contains(hyperRocks) &&
                coolOffRoutine.contains(lowTides) &&
                coolOffRoutine.contains(jetPacks) &&
                coolOffRoutine.contains(morningKicks) &&
                coolOffRoutine.contains(starDays));
    }

    @Test
    public void coolOffRoutine_shouldHaveAllWarmUpAndCoolOffExercisesAfterSevenMinutes() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";
        String starDays = "star days";

        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 90);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise coolOffExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(hyperRocks).build();

        Exercise coolOffExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(lowTides).build();

        Exercise coolOffExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(jetPacks).build();

        Exercise coolOffExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(morningKicks).build();

        Exercise coolOffExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(starDays).build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExerciseOne, coolOffExerciseTwo,
                coolOffExerciseThree, coolOffExerciseFour, coolOffExerciseFive);

        String coolOffRoutine = workoutViewModel.coolOffRoutine();
        assertTrue(coolOffRoutine.contains(hyperRocks) &&
                coolOffRoutine.contains(lowTides) &&
                coolOffRoutine.contains(jetPacks) &&
                coolOffRoutine.contains(morningKicks) &&
                coolOffRoutine.contains(starDays));
    }

    @Test
    public void coolOffRoutine_shouldHaveAStringContainingTheAmountForWarmUp() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String coolOffRoutine = workoutViewModel.coolOffRoutine();

        assertTrue(coolOffRoutine.contains(": 2 sets of 10 reps"));
    }

    @Test
    public void coolOffRoutine_ifNoCoolOffOrWarmUpRoutineExists_thenDisplayCoolOffAndWarmUpRoutineAreNotAvailableMessage() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise nonCoolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(nonCoolOffExercise);

        String coolOffRoutine = workoutViewModel.coolOffRoutine();

        assertTrue(coolOffRoutine.contains("There are no cool offs/warm up exercises that meet this criteria"));
    }

    @Test
    public void coolOffRoutine_noDuplicateWorkouts() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise warmUpExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExercise);

        String coolOffRoutine = workoutViewModel.coolOffRoutine();

        assertFalse(coolOffRoutine.replaceFirst(hyperRocks, "").contains(hyperRocks));
    }

    @Test
    public void coolOffRoutine_alternateSides() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(true).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String coolOffRoutine = workoutViewModel.coolOffRoutine();

        assertTrue(coolOffRoutine.contains("(5 reps each side)"));
    }

    @Test
    public void mainWorkoutRoutine_shouldContainTheAmountOfWorkouts() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String mainWorkoutRoutine = workoutViewModel.mainWorkoutRoutine();

        assertTrue(mainWorkoutRoutine.contains(": 3 sets of 10 reps"));
    }

    @Test
    public void mainWorkoutRoutine_shouldRemoveAnyWarmUpOrCoolOffWorkouts() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(lowTides).build();

        Exercise nonWarmUpExerciseOne = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(jetPacks).build();

        Exercise nonWarmUpExerciseTwo = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(morningKicks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo, nonWarmUpExerciseOne, nonWarmUpExerciseTwo);

        assertFalse(workoutViewModel.mainWorkoutRoutine().contains(hyperRocks));
        assertFalse(workoutViewModel.mainWorkoutRoutine().contains(lowTides));
        assertTrue(workoutViewModel.mainWorkoutRoutine().contains(jetPacks));
        assertTrue(workoutViewModel.mainWorkoutRoutine().contains(morningKicks));
    }

    @Test
    public void mainWorkoutRoutine_ifAllAvailableWorkoutsAreUnder40Minutes_thenMakeAllWorkout() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";
        String starDays = "star days";
        String uberWalks = "uber walks";
        String flybys = "flybys";
        String yolkers = "yolkers";

        Exercise mainExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(hyperRocks).build();

        Exercise mainExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(lowTides).build();

        Exercise mainExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(jetPacks).build();

        Exercise mainExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(morningKicks).build();

        Exercise mainExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(starDays).build();

        Exercise mainExerciseSix = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(uberWalks).build();

        Exercise mainExerciseSeven = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(flybys).build();

        Exercise mainExerciseEight = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(yolkers).build();

        workoutViewModel.filteredExercises = newArrayList(mainExerciseOne, mainExerciseTwo,
                mainExerciseThree, mainExerciseFour, mainExerciseFive, mainExerciseSix, mainExerciseSeven, mainExerciseEight);

        String mainWorkoutRoutine = workoutViewModel.mainWorkoutRoutine();
        assertTrue(mainWorkoutRoutine.contains(hyperRocks) &&
                mainWorkoutRoutine.contains(lowTides) &&
                mainWorkoutRoutine.contains(jetPacks) &&
                mainWorkoutRoutine.contains(morningKicks) &&
                mainWorkoutRoutine.contains(starDays) &&
                mainWorkoutRoutine.contains(uberWalks) &&
                mainWorkoutRoutine.contains(flybys) &&
                mainWorkoutRoutine.contains(yolkers));
    }

    @Test
    public void mainWorkoutRoutine_ifAllAvailableWorkoutsAreOver40Minutes_thenAllWorkoutShouldNotBeInTheWorkout() throws Exception {
        String hyperRocks = "hyper rocks";
        String lowTides = "low tides";
        String jetPacks = "jet packs";
        String morningKicks = "morning kicks";
        String starDays = "star days";
        String uberWalks = "uber walks";
        String flybys = "flybys";
        String yolkers = "yolkers";
        String fryers = "fryers";

        Exercise mainExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(hyperRocks).build();

        Exercise mainExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(lowTides).build();

        Exercise mainExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(jetPacks).build();

        Exercise mainExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(morningKicks).build();

        Exercise mainExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(starDays).build();

        Exercise mainExerciseSix = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(uberWalks).build();

        Exercise mainExerciseSeven = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout(flybys).build();

        Exercise mainExerciseEight = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(6.0).workout(yolkers).build();

        Exercise mainExerciseNine = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(6.0).workout(fryers).build();

        workoutViewModel.filteredExercises = newArrayList(mainExerciseOne, mainExerciseTwo,
                mainExerciseThree, mainExerciseFour, mainExerciseFive, mainExerciseSix, mainExerciseSeven, mainExerciseEight, mainExerciseNine);

        String mainWorkoutRoutine = workoutViewModel.mainWorkoutRoutine();
        assertFalse(mainWorkoutRoutine.contains(hyperRocks) &&
                mainWorkoutRoutine.contains(lowTides) &&
                mainWorkoutRoutine.contains(jetPacks) &&
                mainWorkoutRoutine.contains(morningKicks) &&
                mainWorkoutRoutine.contains(starDays) &&
                mainWorkoutRoutine.contains(uberWalks) &&
                mainWorkoutRoutine.contains(flybys) &&
                mainWorkoutRoutine.contains(yolkers) &&
                mainWorkoutRoutine.contains(fryers));
    }

    @Test
    public void mainWorkoutRoutine_ifNoNonWarmUpAndCoolOffWorkoutRoutineExists_thenDisplayNoWorkoutRoutineAreNotAvailableMessage() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise nonCoolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(nonCoolOffExercise);

        String mainWorkoutRoutine = workoutViewModel.mainWorkoutRoutine();

        assertTrue(mainWorkoutRoutine.contains("There are no exercises that meet this criteria"));
    }

    @Test
    public void mainWorkoutRoutine_noDuplicateWorkouts() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise nonCoolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(nonCoolOffExercise);

        String mainWorkoutRoutine = workoutViewModel.mainWorkoutRoutine();

        assertFalse(mainWorkoutRoutine.replaceFirst(hyperRocks, "").contains(hyperRocks));
    }

    @Test
    public void mainWorkout_alternateSides() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(true).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String mainWorkoutRoutine = workoutViewModel.mainWorkoutRoutine();

        assertTrue(mainWorkoutRoutine.contains("(5 reps each side)"));
    }

    @NonNull
    private Intent getInitialIntent() {
        Intent initialIntent = new Intent();
        initialIntent.putExtra(WORK_OUT_REGIMENT, "cross fit");
        initialIntent.putExtra(WORK_OUT_LENGTH, 50);
        initialIntent.putExtra(WORK_OUT_DIFFICULTY, "advanced");
        initialIntent.putExtra(LIST_OF_EXCLUDED_EQUIPMENT, new Gson().toJson(newArrayList("bands", "barbell", "bicycle", "exercise ball")));
        initialIntent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList("abdominals", "abductors", "forearms")));
        return initialIntent;
    }
}
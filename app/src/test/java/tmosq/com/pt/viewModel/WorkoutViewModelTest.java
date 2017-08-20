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
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(lowTides).build();

        Exercise nonWarmUpExerciseOne = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(jetPacks).build();

        Exercise nonWarmUpExerciseTwo = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(morningKicks).build();

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
        String uberWalks = "uber walks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(10.0).workout(hyperRocks).build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(10.0).workout(lowTides).build();

        Exercise warmUpExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(10.0).workout(jetPacks).build();

        Exercise warmUpExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(10.0).workout(morningKicks).build();

        Exercise warmUpExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(10.0).workout(starDays).build();

        Exercise warmUpExerciseSix = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(10.0).workout(uberWalks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo,
                warmUpExerciseThree, warmUpExerciseFour, warmUpExerciseFive, warmUpExerciseSix);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();
        assertFalse(warmUpRoutine.contains(hyperRocks) &&
                warmUpRoutine.contains(lowTides) &&
                warmUpRoutine.contains(jetPacks) &&
                warmUpRoutine.contains(morningKicks) &&
                warmUpRoutine.contains(starDays) &&
                warmUpRoutine.contains(uberWalks));
    }

    @Test
    public void warmRoutine_shouldHaveAStringContainingTheAmountForWarmUp() throws Exception {
        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();

        assertTrue(warmUpRoutine.contains(": 2 sets of 10 reps\nRest for 15 seconds in between each set\n\n"));
    }

    @Test
    public void warmRoutine_ifWarmUpRoutineIsLessThanOrEqualTo45Minutes_thenWarmUpRoutineShouldNotExist() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 40);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        String hyperRocks = "hyper rocks";

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout(hyperRocks).build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        String warmUpRoutine = workoutViewModel.warmUpRoutine();

        assertFalse(warmUpRoutine.contains(": 2 sets of 10 reps\nRest for 15 seconds in between each set\n\n"));
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
package tmosq.com.pt.viewModel;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import tmosq.com.pt.activity.WorkoutActivity;
import tmosq.com.pt.model.Exercise;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_EXCLUDED_EQUIPMENT;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_LENGTH;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDOMINALS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.ABDUCTORS;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.MIDDLE_BACK;
import static tmosq.com.pt.model.exercise_support_enums.BodyFocus.SHOULDERS;
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
public class WorkoutViewModelTest {
    private WorkoutViewModel workoutViewModel;

    @Before
    public void setUp() throws Exception {
        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(getInitialIntent()).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_ifWarmUpAndCoolOffExists_setVisibilityOfListItems() throws Exception {
        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.warmUpAndCoolOffEmptyVisibility.get()).isEqualTo(GONE);
        assertThat(workoutViewModel.warmUpAndCoolOffListItemsVisibility.get()).isEqualTo(VISIBLE);
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_shouldFilterOutAllNonWarmUpExercises() throws Exception {
        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("low tides").build();

        Exercise nonWarmUpExerciseOne = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("jet packs").build();

        Exercise nonWarmUpExerciseTwo = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("morning kicks").build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo, nonWarmUpExerciseOne, nonWarmUpExerciseTwo);

        workoutViewModel.generateAllExercises();

        assertTrue(workoutViewModel.warmUpExercises.get().contains(warmUpExerciseOne));
        assertTrue(workoutViewModel.warmUpExercises.get().contains(warmUpExerciseTwo));
        assertTrue(workoutViewModel.coolOffExercises.get().contains(warmUpExerciseTwo));
        assertTrue(workoutViewModel.coolOffExercises.get().contains(warmUpExerciseTwo));
        assertFalse(workoutViewModel.warmUpExercises.get().contains(nonWarmUpExerciseOne));
        assertFalse(workoutViewModel.warmUpExercises.get().contains(nonWarmUpExerciseTwo));
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_shouldNotHaveAnyMoreWarmUpAndCoolOffExercisesAfterFiveMinutes() throws Exception {
        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("hyper rocks").build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("low tides").build();

        Exercise warmUpExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("jet packs").build();

        Exercise warmUpExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("morning kicks").build();

        Exercise warmUpExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("star days").build();


        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo,
                warmUpExerciseThree, warmUpExerciseFour, warmUpExerciseFive);

        workoutViewModel.generateAllExercises();

        assertFalse(workoutViewModel.warmUpExercises.get().contains(warmUpExerciseOne) &&
                workoutViewModel.warmUpExercises.get().contains(warmUpExerciseTwo) &&
                workoutViewModel.warmUpExercises.get().contains(warmUpExerciseThree) &&
                workoutViewModel.warmUpExercises.get().contains(warmUpExerciseFour) &&
                workoutViewModel.warmUpExercises.get().contains(warmUpExerciseFive));

        assertFalse(workoutViewModel.coolOffExercises.get().contains(warmUpExerciseOne) &&
                workoutViewModel.coolOffExercises.get().contains(warmUpExerciseTwo) &&
                workoutViewModel.coolOffExercises.get().contains(warmUpExerciseThree) &&
                workoutViewModel.coolOffExercises.get().contains(warmUpExerciseFour) &&
                workoutViewModel.coolOffExercises.get().contains(warmUpExerciseFive));
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_shouldHaveAllWarmUpAndCoolOffExercisesAfterSevenMinutes() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 90);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise coolOffExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("hyper rocks").build();

        Exercise coolOffExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("low tides").build();

        Exercise coolOffExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("jet packs").build();

        Exercise coolOffExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("morning kicks").build();

        Exercise coolOffExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("star days").build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExerciseOne, coolOffExerciseTwo,
                coolOffExerciseThree, coolOffExerciseFour, coolOffExerciseFive);

        workoutViewModel.generateAllExercises();
        assertTrue(workoutViewModel.warmUpExercises.get().contains(coolOffExerciseOne) &&
                workoutViewModel.warmUpExercises.get().contains(coolOffExerciseTwo) &&
                workoutViewModel.warmUpExercises.get().contains(coolOffExerciseThree) &&
                workoutViewModel.warmUpExercises.get().contains(coolOffExerciseFour) &&
                workoutViewModel.warmUpExercises.get().contains(coolOffExerciseFive));

        assertTrue(workoutViewModel.coolOffExercises.get().contains(coolOffExerciseOne) &&
                workoutViewModel.coolOffExercises.get().contains(coolOffExerciseTwo) &&
                workoutViewModel.coolOffExercises.get().contains(coolOffExerciseThree) &&
                workoutViewModel.coolOffExercises.get().contains(coolOffExerciseFour) &&
                workoutViewModel.coolOffExercises.get().contains(coolOffExerciseFive));
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_ifNoCoolOffOrWarmUpRoutineExists_shouldNotHaveAnyExercises() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise nonCoolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(nonCoolOffExercise);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.warmUpAndCoolOffEmptyVisibility.get()).isEqualTo(VISIBLE);
        assertThat(workoutViewModel.warmUpAndCoolOffListItemsVisibility.get()).isEqualTo(GONE);
        assertThat(workoutViewModel.warmUpExercises.get()).isEqualTo(new ArrayList<>());
        assertThat(workoutViewModel.coolOffExercises.get()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_noDuplicateWorkouts() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise warmUpExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExercise);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.warmUpExercises.get().size()).isEqualTo(1);
        assertThat(workoutViewModel.coolOffExercises.get().size()).isEqualTo(1);
    }

    @Test
    public void generateAllExercises_forWarmUpAndCoolOffRoutine_whenWorkoutLengthIsLessThanOrEqualToForty_shouldNotHaveAnyExercises() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(WORK_OUT_LENGTH, 35);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(true).averageSecondsPerRep(5.0).workout("hyperRocks").build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.warmUpAndCoolOffEmptyVisibility.get()).isEqualTo(VISIBLE);
        assertThat(workoutViewModel.warmUpAndCoolOffListItemsVisibility.get()).isEqualTo(GONE);
        assertThat(workoutViewModel.warmUpExercises.get()).isEqualTo(new ArrayList<>());
        assertThat(workoutViewModel.coolOffExercises.get()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_shouldContainTheAmountOfWorkouts() throws Exception {
        Exercise exercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(exercise);

        workoutViewModel.generateAllExercises();

        assertTrue(workoutViewModel.mainWorkoutExercises.get().contains(exercise));
    }

    @Test
    public void mainWorkoutRoutine_shouldRemoveAnyWarmUpOrCoolOffWorkouts() throws Exception {
        Exercise warmUpExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        Exercise warmUpExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("low tides").build();

        Exercise nonWarmUpExerciseOne = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("jet packs").build();

        Exercise nonWarmUpExerciseTwo = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("morning kicks").build();

        workoutViewModel.filteredExercises = newArrayList(warmUpExerciseOne, warmUpExerciseTwo, nonWarmUpExerciseOne, nonWarmUpExerciseTwo);

        workoutViewModel.generateAllExercises();

        assertFalse(workoutViewModel.mainWorkoutExercises.get().contains(warmUpExerciseOne));
        assertFalse(workoutViewModel.mainWorkoutExercises.get().contains(warmUpExerciseTwo));
        assertTrue(workoutViewModel.mainWorkoutExercises.get().contains(nonWarmUpExerciseOne));
        assertTrue(workoutViewModel.mainWorkoutExercises.get().contains(nonWarmUpExerciseTwo));
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_ifAllAvailableWorkoutsAreUnder40Minutes_thenMakeAllWorkout() throws Exception {
        Exercise mainExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("hyper rocks").build();

        Exercise mainExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("low tides").build();

        Exercise mainExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("jet packs").build();

        Exercise mainExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("morning kicks").build();

        Exercise mainExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("star days").build();

        Exercise mainExerciseSix = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("uber walks").build();

        Exercise mainExerciseSeven = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("flybys").build();

        Exercise mainExerciseEight = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("yolkers").build();

        workoutViewModel.filteredExercises = newArrayList(mainExerciseOne, mainExerciseTwo,
                mainExerciseThree, mainExerciseFour, mainExerciseFive, mainExerciseSix, mainExerciseSeven, mainExerciseEight);

        workoutViewModel.generateAllExercises();

        assertTrue(workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseOne) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseTwo) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseThree) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseFour) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseFive) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseSix) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseSeven) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseEight));
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_shouldGenerateWorkoutWithCorrectBodyFocuses_whenTImeIsExceeded() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList(
                ABDOMINALS.getBodyPartNameAlias())));
        intent.putExtra(WORK_OUT_LENGTH, 15);

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise exerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        Exercise exerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("low tides").build();

        Exercise exerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("jet packs").build();

        Exercise exerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("morning kicks").build();

        workoutViewModel.filteredExercises = newArrayList(exerciseOne, exerciseTwo, exerciseThree, exerciseFour);

        workoutViewModel.generateAllExercises();
        assertTrue(
                workoutViewModel.mainWorkoutExercises.get().contains(exerciseOne) ||
                        workoutViewModel.mainWorkoutExercises.get().contains(exerciseTwo) ||
                        workoutViewModel.mainWorkoutExercises.get().contains(exerciseThree) ||
                        workoutViewModel.mainWorkoutExercises.get().contains(exerciseFour)
        );
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_arrangeWorkOutRoutineByBodyFocus() throws Exception {
        Intent intent = getInitialIntent();
        intent.putExtra(LIST_OF_ACTIVE_BODY_FOCUSES, new Gson().toJson(newArrayList(
                ABDOMINALS.getBodyPartNameAlias(),
                ABDUCTORS.getBodyPartNameAlias(),
                SHOULDERS.getBodyPartNameAlias(),
                MIDDLE_BACK.getBodyPartNameAlias())));

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise mainExerciseAbdomianls = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("hyper rocks").build();

        Exercise mainExerciseAbductos = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDUCTORS).alternateSide(false).averageSecondsPerRep(3.0).workout("low tides").build();

        Exercise mainExerciseShoulders = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(SHOULDERS).alternateSide(false).averageSecondsPerRep(3.0).workout("jet packs").build();

        Exercise mainExerciseMiddleBack = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(MIDDLE_BACK).alternateSide(false).averageSecondsPerRep(3.0).workout("morning kicks").build();

        Exercise mainExerciseSecondMiddleBack = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(MIDDLE_BACK).alternateSide(false).averageSecondsPerRep(3.0).workout("fixers").build();

        workoutViewModel.filteredExercises = newArrayList(
                mainExerciseAbdomianls,
                mainExerciseMiddleBack,
                mainExerciseShoulders,
                mainExerciseAbductos,
                mainExerciseSecondMiddleBack);

        workoutViewModel.generateAllExercises();

        assertTrue(workoutViewModel.mainWorkoutExercises.get().get(0).equals(mainExerciseAbdomianls));
        assertTrue(workoutViewModel.mainWorkoutExercises.get().get(1).equals(mainExerciseAbductos));
        assertTrue(workoutViewModel.mainWorkoutExercises.get().get(2).equals(mainExerciseShoulders));
        assertTrue(workoutViewModel.mainWorkoutExercises.get().get(3).equals(mainExerciseMiddleBack) ||
                workoutViewModel.mainWorkoutExercises.get().get(3).equals(mainExerciseSecondMiddleBack));
        assertTrue(workoutViewModel.mainWorkoutExercises.get().get(4).equals(mainExerciseSecondMiddleBack) ||
                workoutViewModel.mainWorkoutExercises.get().get(4).equals(mainExerciseMiddleBack));
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_ifAllAvailableWorkoutsAreOver40Minutes_thenAllWorkoutShouldNotBeInTheWorkout() throws Exception {
        Exercise mainExerciseOne = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("hyper rocks").build();

        Exercise mainExerciseTwo = Exercise.builder().equipment(CHAIR).difficulty(INTERMEDIATE).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("low tides").build();

        Exercise mainExerciseThree = Exercise.builder().equipment(FOAM_ROLL).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("jet packs").build();

        Exercise mainExerciseFour = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("morning kicks").build();

        Exercise mainExerciseFive = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("star days").build();

        Exercise mainExerciseSix = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("uber walks").build();

        Exercise mainExerciseSeven = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(3.0).workout("flybys").build();

        Exercise mainExerciseEight = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(6.0).workout("yolkers").build();

        Exercise mainExerciseNine = Exercise.builder().equipment(BICYCLE).difficulty(ADVANCED).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(6.0).workout("fryers").build();

        workoutViewModel.filteredExercises = newArrayList(mainExerciseOne, mainExerciseTwo,
                mainExerciseThree, mainExerciseFour, mainExerciseFive, mainExerciseSix, mainExerciseSeven, mainExerciseEight, mainExerciseNine);

        workoutViewModel.generateAllExercises();

        assertFalse(workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseOne) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseTwo) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseThree) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseFour) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseFive) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseSix) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseSeven) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseEight) &&
                workoutViewModel.mainWorkoutExercises.get().contains(mainExerciseNine));
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_ifNoNonWarmUpAndCoolOffWorkoutRoutineExists_shouldNotShowList() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise coolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(WARM_UP_AND_COOL_OFF).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExercise);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.mainWorkoutEmptyVisibility.get()).isEqualTo(VISIBLE);
        assertThat(workoutViewModel.mainWorkoutListItemsVisibility.get()).isEqualTo(GONE);
        assertThat(workoutViewModel.mainWorkoutExercises.get()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_ifNonWarmUpAndCoolOffWorkoutRoutineExists_shouldShowList() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise coolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(coolOffExercise);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.mainWorkoutEmptyVisibility.get()).isEqualTo(GONE);
        assertThat(workoutViewModel.mainWorkoutListItemsVisibility.get()).isEqualTo(VISIBLE);
    }

    @Test
    public void generateAllExercises_mainWorkoutRoutine_noDuplicateWorkouts() throws Exception {
        Intent intent = getInitialIntent();

        WorkoutActivity workoutActivity = Robolectric.buildActivity(WorkoutActivity.class).withIntent(intent).create().get();

        workoutViewModel = new WorkoutViewModel(workoutActivity);

        Exercise nonCoolOffExercise = Exercise.builder().equipment(BANDS).difficulty(BASIC).workOutType(BODY).forTime(false)
                .bodyFocus(ABDOMINALS).alternateSide(false).averageSecondsPerRep(5.0).workout("hyper rocks").build();

        workoutViewModel.filteredExercises = newArrayList(nonCoolOffExercise);

        workoutViewModel.generateAllExercises();

        assertThat(workoutViewModel.mainWorkoutExercises.get().size()).isEqualTo(1);
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
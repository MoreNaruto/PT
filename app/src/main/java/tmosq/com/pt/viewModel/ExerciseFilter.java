package tmosq.com.pt.viewModel;

import android.content.Intent;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tmosq.com.pt.helper.ExerciseSplitter;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.model.exercise_support_enums.BodyFocus;
import tmosq.com.pt.model.exercise_support_enums.Difficulty;
import tmosq.com.pt.model.exercise_support_enums.Equipment;
import tmosq.com.pt.model.exercise_support_enums.WorkOutType;

import static tmosq.com.pt.helper.ExerciseSplitter.*;
import static tmosq.com.pt.helper.ExerciseSplitter.HAS_PARTNER;
import static tmosq.com.pt.helper.ExerciseSplitter.LIST_OF_ACTIVE_BODY_FOCUSES;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_DIFFICULTY;
import static tmosq.com.pt.helper.ExerciseSplitter.WORK_OUT_REGIMENT;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.BASIC;
import static tmosq.com.pt.model.exercise_support_enums.Difficulty.INTERMEDIATE;
import static tmosq.com.pt.model.exercise_support_enums.WorkOutType.WARM_UP_AND_COOL_OFF;

public class ExerciseFilter {
    private Intent intent;
    private Gson gson;

    ExerciseFilter(Intent intent) {
        this.intent = intent;
        this.gson = new Gson();
    }

    List<Exercise> filterExercises(List<Exercise> exercises) {
        Collection<Exercise> filteredExercises = Collections2.filter(exercises, new Predicate<Exercise>() {
            @Override
            public boolean apply(Exercise exercise) {
                return filterOutDifficulty(exercise.getDifficulty()) &&
                        filterOutUnavailableEquipment(exercise.getEquipment()) &&
                        filterOutWorkoutsNotInTheRegiment(exercise.getWorkOutType()) &&
                        filterOutBodyPartsNotFocusedOn(exercise.getBodyFocus()) &&
                        filterOutPartnerNeeded(exercise.getPartnerNeeded());
            }
        });
        return new ArrayList<>(filteredExercises);
    }

    List<Exercise> filterWarmUpAndCoolOffExercises(List<Exercise> exercises, final Boolean isFilteringInWarmUpsAndCoolOffs) {
        Collection<Exercise> filteredExercises = Collections2.filter(exercises, new Predicate<Exercise>() {
            @Override
            public boolean apply(Exercise exercise) {
                return filteringWarmUpsAndCoolOffs(exercise.getWorkOutType(), isFilteringInWarmUpsAndCoolOffs);
            }
        });
        return new ArrayList<>(filteredExercises);
    }

    List<Exercise> filterForSpecificBodyFocus(List<Exercise> exercises, final BodyFocus bodyFocus) {
        Collection<Exercise> filteredExercises = Collections2.filter(exercises, new Predicate<Exercise>() {
            @Override
            public boolean apply(Exercise exercise) {
                return exercise.getBodyFocus().equals(bodyFocus);
            }
        });
        return new ArrayList<>(filteredExercises);
    }

    private Boolean filteringWarmUpsAndCoolOffs(WorkOutType workOutType, Boolean isFilteringInWarmUpsAndCoolOffs) {
        if (workOutType.equals(WARM_UP_AND_COOL_OFF)) {
            return isFilteringInWarmUpsAndCoolOffs;
        } else
            return !isFilteringInWarmUpsAndCoolOffs;
    }

    private Boolean filterOutDifficulty(Difficulty difficulty) {
        Difficulty userDifficulty = Difficulty.fromString(intent.getStringExtra(WORK_OUT_DIFFICULTY));

        if (difficulty == null) {
            return false;
        }

        if (userDifficulty.equals(BASIC)) {
            return difficulty.equals(BASIC);
        }
        return !userDifficulty.equals(INTERMEDIATE) || difficulty.equals(BASIC) || difficulty.equals(INTERMEDIATE);
    }

    private Boolean filterOutUnavailableEquipment(Equipment equipment) {
        if (equipment == null) {
            return false;
        }

        List<String> excludedEquipments = gson.fromJson(intent.getStringExtra(LIST_OF_EXCLUDED_EQUIPMENT), List.class);
        return !excludedEquipments.contains(equipment.getEquipmentNameAlias());
    }

    private Boolean filterOutWorkoutsNotInTheRegiment(WorkOutType workOutType) {
        if (workOutType == null) {
            return false;
        }

        String workOutRegiment = intent.getStringExtra(WORK_OUT_REGIMENT);
        return workOutType.getWorkOutRegimentNameAliases().contains(workOutRegiment);
    }

    private Boolean filterOutBodyPartsNotFocusedOn(BodyFocus bodyFocus) {
        if (bodyFocus == null) {
            return false;
        }

        List activeBodyFocuses = gson.fromJson(intent.getStringExtra(LIST_OF_ACTIVE_BODY_FOCUSES), List.class);
        return activeBodyFocuses.contains(bodyFocus.getBodyPartNameAlias());
    }

    private Boolean filterOutPartnerNeeded(Boolean needsPartner) {
        return intent.getBooleanExtra(HAS_PARTNER, false) || !needsPartner;
    }
}
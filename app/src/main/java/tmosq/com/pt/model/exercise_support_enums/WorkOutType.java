package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment.TABATA;

public enum WorkOutType {
    BODY("body", newArrayList(TABATA.getWorkoutRegimentTitle())),
    POWER_WEIGHT("power weight", newArrayList(TABATA.getWorkoutRegimentTitle())),
    WARM_UP_AND_COOL_OFF("warm-up/cool-off", newArrayList(TABATA.getWorkoutRegimentTitle())),
    WEIGHTED_MOVEMENTS("weighted movements", newArrayList(TABATA.getWorkoutRegimentTitle()));

    private String workOutTypesNameAlias;
    private List<String> workOutRegimentNameAliases;

    WorkOutType(String workOutTypesNameAlias, List<String> workOutRegimentNameAliases) {
        this.workOutTypesNameAlias = workOutTypesNameAlias;
        this.workOutRegimentNameAliases = workOutRegimentNameAliases;
    }

    public String getWorkOutTypesNameAlias() {
        return workOutTypesNameAlias;
    }

    public static WorkOutType fromString(String workOutTypesNameAlias) {
        if (workOutTypesNameAlias != null) {
            for (WorkOutType workOutType : WorkOutType.values()) {
                if (workOutTypesNameAlias.equalsIgnoreCase(workOutType.workOutTypesNameAlias))
                    return workOutType;
            }
        }
        return null;
    }

    public List<String> getWorkOutRegimentNameAliases() {
        return workOutRegimentNameAliases;
    }

    public static class WorkOutTypesDeserializer implements JsonDeserializer<WorkOutType> {

        @Override
        public WorkOutType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

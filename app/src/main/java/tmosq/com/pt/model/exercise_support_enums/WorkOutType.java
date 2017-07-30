package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import static tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment.CARDIO;
import static tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment.CROSS_FIT;
import static tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment.POWER_LIFTING;

public enum WorkOutType {
    BODY("body", new String[]{CROSS_FIT.getWorkOutRegimentNameAlias(), CARDIO.getWorkOutRegimentNameAlias()}),
    POWER_WEIGHT("power weight", new String[]{POWER_LIFTING.getWorkOutRegimentNameAlias()}),
    WARM_UP_AND_COOL_OFF("warm-up/cool-off", new String[]{POWER_LIFTING.getWorkOutRegimentNameAlias(), CROSS_FIT.getWorkOutRegimentNameAlias(), CARDIO.getWorkOutRegimentNameAlias()}),
    WEIGHTED_MOVEMENTS("weighted movements", new String[]{CROSS_FIT.getWorkOutRegimentNameAlias(), POWER_LIFTING.getWorkOutRegimentNameAlias()});

    private String workOutTypesNameAlias;
    private String[] workOutRegimentNameAliases;

    WorkOutType(String workOutTypesNameAlias, String[] workOutRegimentNameAliases) {
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

    public String[] getWorkOutRegimentNameAliases() {
        return workOutRegimentNameAliases;
    }

    public static class WorkOutTypesDeserializer implements JsonDeserializer<WorkOutType> {

        @Override
        public WorkOutType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public enum WorkOutType {
    BODY("body"),
    POWER_WEIGHT("power weight"),
    WEIGHTED_MOVEMENTS("weighted movements"),
    WARM_UP_AND_COOL_OFF("warm-up/cool-off");

    private String workOutTypesNameAlias;

    WorkOutType(String workOutTypesNameAlias) {
        this.workOutTypesNameAlias = workOutTypesNameAlias;
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

    public static class WorkOutTypesDeserializer implements JsonDeserializer<WorkOutType> {

        @Override
        public WorkOutType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

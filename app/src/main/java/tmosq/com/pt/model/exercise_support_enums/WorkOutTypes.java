package tmosq.com.pt.model.exercise_support_enums;

public enum WorkOutTypes {
    BODY("body"),
    POWER_WEIGHT("power weight"),
    WEIGHTED_MOVEMENTS("weighted movements"),
    WARM_UP_AND_COOL_OFF("warm-up/cool-off");

    private String workOutTypesNameAlias;

    WorkOutTypes(String workOutTypesNameAlias) {

        this.workOutTypesNameAlias = workOutTypesNameAlias;
    }

    public static WorkOutTypes fromString(String workOutTypesNameAlias) {
        if (workOutTypesNameAlias != null) {
            for (WorkOutTypes workOutTypes : WorkOutTypes.values()) {
                if (workOutTypesNameAlias.equalsIgnoreCase(workOutTypes.workOutTypesNameAlias))
                    return workOutTypes;
            }
        }
        return null;
    }
}

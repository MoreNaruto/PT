package tmosq.com.pt.model.exercise_support_enums;

public enum Equipment {
    MEDICINE_BALL("medicine ball"),
    BARBELL("barbell"),
    PLATE("plate"),
    RICKSHAW("rickshaw"),
    DUMBBELL("dumbbell"),
    NONE("none"),
    CABLE("cable"),
    KETTLEBELL("kettlebell"),
    BANDS("bands"),
    FOAM_ROLL("foam roll"),
    EXERCISE_BALL("exercise ball"),
    MACHINE("machine"),
    WORKOUT_BOX("workout box"),
    CHAIR("chair"),
    ROLLER("roller"),
    EZ_CURL_BAR("E-Z Curl Bar"),
    CONES("cones"),
    TONING_WHEEL("toning wheel"),
    ROPE("rope"),
    STONE("stone"),
    SLED("sled"),
    BICYCLE("bicycle"),
    TREADMILL("treadmill"),
    ROW("row"),
    TRAP_BAR("trap bar");

    private String equipmentNameAlias;

    Equipment(String equipmentNameAlias) {

        this.equipmentNameAlias = equipmentNameAlias;
    }

    public static Equipment fromString(String equipmentNameAlias) {
        if (equipmentNameAlias != null) {
            for (Equipment equipment : Equipment.values()) {
                if (equipmentNameAlias.equalsIgnoreCase(equipment.equipmentNameAlias))
                    return equipment;
            }
        }
        return null;
    }
}

package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public enum Equipment {
    BANDS("bands"),
    BARBELL("barbell"),
    BICYCLE("bicycle"),
    CABLE("cable"),
    CHAIR("chair"),
    CONES("cones"),
    DUMBBELL("dumbbell"),
    EXERCISE_BALL("exercise ball"),
    EZ_CURL_BAR("E-Z Curl Bar"),
    FOAM_ROLL("foam roll"),
    KETTLEBELL("kettlebell"),
    MACHINE("machine"),
    MEDICINE_BALL("medicine ball"),
    NONE("none"),
    PLATE("plate"),
    RICKSHAW("rickshaw"),
    ROLLER("roller"),
    ROPE("rope"),
    ROW("row"),
    SLED("sled"),
    STONE("stone"),
    TONING_WHEEL("toning wheel"),
    TRAP_BAR("trap bar"),
    TREADMILL("treadmill"),
    WORKOUT_BOX("workout box");

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

    public static class EquipmentDeserializer implements JsonDeserializer<Equipment> {

        @Override
        public Equipment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

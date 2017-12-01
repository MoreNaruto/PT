package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import tmosq.com.pt.R;

@Getter
public enum Equipment {
    BANDS("bands", R.id.bands_checkbox),
    BARBELL("barbell", R.id.barbell_checkbox),
    BICYCLE("bicycle", R.id.bicycle_checkbox),
    BOSU_BALL("bosu ball", R.id.bosu_ball),
    CABLE("cable", R.id.cable_checkbox),
    CHAIR("chair", R.id.chair_checkbox),
    CONES("cones", R.id.cones_checkbox),
    DUMBBELL("dumbbell", R.id.dumbbell_checkbox),
    EXERCISE_BALL("exercise ball", R.id.exercise_ball_checkbox),
    EZ_CURL_BAR("E-Z Curl Bar", R.id.ez_curl_bar_checkbox),
    FOAM_ROLL("foam roll", R.id.foam_roll_checkbox),
    JUMPING_ROPE("jumping rope", R.id.jumping_rope_checkbox),
    KETTLEBELL("kettlebell", R.id.kettlebell_checkbox),
    MACHINE("machine", R.id.machine_checkbox),
    MEDICINE_BALL("medicine ball", R.id.medicine_ball_checkbox),
    PLATE("plate", R.id.plate_checkbox),
    RICKSHAW("rickshaw", R.id.rickshaw_checkbox),
    ROLLER("roller", R.id.roller_checkbox),
    ROPE("rope", R.id.rope_checkbox),
    ROW("row", R.id.row_checkbox),
    SLED("sled", R.id.sled_checkbox),
    STONE("stone", R.id.stone_checkbox),
    STRAPS("straps", R.id.straps_checkbox),
    T_BAR("t-bar", R.id.t_bar_checkbox),
    TONING_WHEEL("toning wheel", R.id.toning_wheel_checkbox),
    TRAP_BAR("trap bar", R.id.trap_bar_checkbox),
    TREADMILL("treadmill", R.id.treadmill_checkbox),
    WORKOUT_BOX("workout box", R.id.workout_box_checkbox),
    V_BAR("v-bar", R.id.v_bar_checkbox);

    private String equipmentNameAlias;
    private Integer resourceIdCheckBox;

    Equipment(String equipmentNameAlias, Integer resourceIdCheckBox) {
        this.equipmentNameAlias = equipmentNameAlias;
        this.resourceIdCheckBox = resourceIdCheckBox;
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

    public static Equipment fromResourceCheckBoxId(Integer resourceCheckBoxId) {
        if (resourceCheckBoxId != null) {
            for (Equipment equipment : Equipment.values()) {
                if (resourceCheckBoxId.equals(equipment.resourceIdCheckBox)) {
                    return equipment;
                }
            }
        }
        return null;
    }

    public static List<String> allEquipmentNameAliases(){
        List<String> allEquipmentNames = new ArrayList<>();

        for (Equipment equipment : Equipment.values()) {
            allEquipmentNames.add(equipment.getEquipmentNameAlias());
        }

        return allEquipmentNames;
    }


    public static class EquipmentDeserializer implements JsonDeserializer<Equipment> {

        @Override
        public Equipment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

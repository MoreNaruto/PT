package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import tmosq.com.pt.R;

public enum BodyFocus {
    ABDOMINALS("abdominals", R.id.abdominals_checkbox),
    ABDUCTORS("abductors", R.id.abductors_checkbox),
    ADDUCTORS("adductors", R.id.adductors_checkbox),
    BICEPS("biceps", R.id.biceps_checkbox),
    CALVES("calves", R.id.calves_checkbox),
    CHEST("chest", R.id.chest_checkbox),
    FOREARMS("forearms", R.id.forearms_checkbox),
    GLUTES("glutes", R.id.glutes_checkbox),
    HAMSTRING("hamstring", R.id.hamstring_checkbox),
    LATS("lats", R.id.lats_checkbox),
    LOWER_BACK("lower back", R.id.lower_back_checkbox),
    MIDDLE_BACK("middle back", R.id.middle_back_checkbox),
    NECK("neck", R.id.neck_checkbox),
    QUADRICEPS("quadriceps", R.id.quadriceps_checkbox),
    SHOULDERS("shoulders", R.id.shoulders_checkbox),
    TRAPS("traps", R.id.traps_checkbox),
    TRICEPS("triceps", R.id.triceps_checkbox);

    private String bodyPartNameAlias;

    private Integer resourceCheckBoxId;
    BodyFocus(String bodyPartNameAlias, Integer resourceCheckBoxId) {
        this.bodyPartNameAlias = bodyPartNameAlias;
        this.resourceCheckBoxId = resourceCheckBoxId;
    }

    public static BodyFocus fromString(String bodyPartNameAlias) {
        if (bodyPartNameAlias != null) {
            for (BodyFocus bodyFocus : BodyFocus.values()) {
                if (bodyPartNameAlias.equalsIgnoreCase(bodyFocus.bodyPartNameAlias))
                    return bodyFocus;
            }
        }
        return null;
    }

    public static BodyFocus fromResourceCheckBoxId(Integer resourceCheckBoxId){
        if (resourceCheckBoxId != null) {
            for (BodyFocus bodyFocus : BodyFocus.values()) {
                if (resourceCheckBoxId.equals(bodyFocus.resourceCheckBoxId)) {
                    return bodyFocus;
                }
            }
        }
        return null;
    }

    public String getBodyPartNameAlias() {
        return bodyPartNameAlias;
    }

    public static class BodyFocusDeserializer implements JsonDeserializer<BodyFocus> {

        public BodyFocusDeserializer() {
        }

        @Override
        public BodyFocus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

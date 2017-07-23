package tmosq.com.pt.model.exercise_support_enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public enum BodyFocus {
    ABDOMINALS("abdominals"),
    ABDUCTORS("abductors"),
    ADDUCTORS("adductors"),
    BICEPS("biceps"),
    CALVES("calves"),
    CHEST("chest"),
    FOREARMS("forearms"),
    GLUTES("glutes"),
    HAMSTRING("hamstring"),
    LATS("lats"),
    LOWER_BACK("lower back"),
    MIDDLE_BACK("middle back"),
    NECK("neck"),
    QUADRICEPS("quadriceps"),
    SHOULDERS("shoulders"),
    TRAPS("traps"),
    TRICEPS("triceps");

    private String bodyPartNameAlias;

    BodyFocus(String bodyPartNameAlias) {

        this.bodyPartNameAlias = bodyPartNameAlias;
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

    public static class BodyFocusDeserializer implements JsonDeserializer<BodyFocus> {

        public BodyFocusDeserializer() {
        }

        @Override
        public BodyFocus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return fromString(json.getAsString());
        }
    }
}

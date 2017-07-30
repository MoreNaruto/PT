package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Before;
import org.junit.Test;

import tmosq.com.pt.R;

import static org.junit.Assert.*;

public class BodyFocusTest {
    @Test
    public void fromString_whenStringIsValid_getTheBodyFocus() throws Exception {
        assertEquals(BodyFocus.fromString("abdominals"), BodyFocus.ABDOMINALS);
        assertEquals(BodyFocus.fromString("abductors"), BodyFocus.ABDUCTORS);
        assertEquals(BodyFocus.fromString("adductors"), BodyFocus.ADDUCTORS);
        assertEquals(BodyFocus.fromString("biceps"), BodyFocus.BICEPS);
        assertEquals(BodyFocus.fromString("calves"), BodyFocus.CALVES);
        assertEquals(BodyFocus.fromString("chest"), BodyFocus.CHEST);
        assertEquals(BodyFocus.fromString("forearms"), BodyFocus.FOREARMS);
        assertEquals(BodyFocus.fromString("glutes"), BodyFocus.GLUTES);
        assertEquals(BodyFocus.fromString("hamstring"), BodyFocus.HAMSTRING);
        assertEquals(BodyFocus.fromString("lats"), BodyFocus.LATS);
        assertEquals(BodyFocus.fromString("lower back"), BodyFocus.LOWER_BACK);
        assertEquals(BodyFocus.fromString("middle back"), BodyFocus.MIDDLE_BACK);
        assertEquals(BodyFocus.fromString("neck"), BodyFocus.NECK);
        assertEquals(BodyFocus.fromString("quadriceps"), BodyFocus.QUADRICEPS);
        assertEquals(BodyFocus.fromString("shoulders"), BodyFocus.SHOULDERS);
        assertEquals(BodyFocus.fromString("traps"), BodyFocus.TRAPS);
        assertEquals(BodyFocus.fromString("triceps"), BodyFocus.TRICEPS);
    }

    @Test
    public void fromString_whenStringIsInvalid_returnNull() throws Exception {
        assertEquals(BodyFocus.fromString("keep"), null);
    }

    @Test
    public void fromString_whenStringIsNull_returnNull() throws Exception {
        assertEquals(BodyFocus.fromString(null), null);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsValid_getBodyFocus() throws Exception {
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.abdominals_checkbox), BodyFocus.ABDOMINALS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.abductors_checkbox), BodyFocus.ABDUCTORS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.adductors_checkbox), BodyFocus.ADDUCTORS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.biceps_checkbox), BodyFocus.BICEPS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.calves_checkbox), BodyFocus.CALVES);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.chest_checkbox), BodyFocus.CHEST);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.forearms_checkbox), BodyFocus.FOREARMS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.glutes_checkbox), BodyFocus.GLUTES);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.hamstring_checkbox), BodyFocus.HAMSTRING);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.lats_checkbox), BodyFocus.LATS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.lower_back_checkbox), BodyFocus.LOWER_BACK);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.middle_back_checkbox), BodyFocus.MIDDLE_BACK);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.neck_checkbox), BodyFocus.NECK);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.quadriceps_checkbox), BodyFocus.QUADRICEPS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.shoulders_checkbox), BodyFocus.SHOULDERS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.traps_checkbox), BodyFocus.TRAPS);
        assertEquals(BodyFocus.fromResourceCheckBoxId(R.id.triceps_checkbox), BodyFocus.TRICEPS);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsInvalid_returnNull() throws Exception {
        assertEquals(BodyFocus.fromResourceCheckBoxId(32), null);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsNull_returnNull() throws Exception {
        assertEquals(BodyFocus.fromResourceCheckBoxId(null), null);
    }

    @Test
    public void getBodyPartNameAlias() throws Exception {
        assertEquals(BodyFocus.ABDOMINALS.getBodyPartNameAlias(), "abdominals");
        assertEquals(BodyFocus.ABDUCTORS.getBodyPartNameAlias(), "abductors");
        assertEquals(BodyFocus.ADDUCTORS.getBodyPartNameAlias(), "adductors");
        assertEquals(BodyFocus.BICEPS.getBodyPartNameAlias(), "biceps");
        assertEquals(BodyFocus.CALVES.getBodyPartNameAlias(), "calves");
        assertEquals(BodyFocus.CHEST.getBodyPartNameAlias(), "chest");
        assertEquals(BodyFocus.FOREARMS.getBodyPartNameAlias(), "forearms");
        assertEquals(BodyFocus.GLUTES.getBodyPartNameAlias(), "glutes");
        assertEquals(BodyFocus.HAMSTRING.getBodyPartNameAlias(), "hamstring");
        assertEquals(BodyFocus.LATS.getBodyPartNameAlias(), "lats");
        assertEquals(BodyFocus.LOWER_BACK.getBodyPartNameAlias(), "lower back");
        assertEquals(BodyFocus.MIDDLE_BACK.getBodyPartNameAlias(), "middle back");
        assertEquals(BodyFocus.NECK.getBodyPartNameAlias(), "neck");
        assertEquals(BodyFocus.QUADRICEPS.getBodyPartNameAlias(), "quadriceps");
        assertEquals(BodyFocus.SHOULDERS.getBodyPartNameAlias(), "shoulders");
        assertEquals(BodyFocus.TRAPS.getBodyPartNameAlias(), "traps");
        assertEquals(BodyFocus.TRICEPS.getBodyPartNameAlias(), "triceps");
    }

}
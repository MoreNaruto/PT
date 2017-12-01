package tmosq.com.pt.model.exercise_support_enums;

import org.junit.Test;

import java.util.List;

import tmosq.com.pt.R;

import static org.junit.Assert.*;

public class EquipmentTest {
    @Test
    public void fromString_whenStringIsValid_returnEquipment() throws Exception {
        assertEquals(Equipment.fromString("bands"), Equipment.BANDS);
        assertEquals(Equipment.fromString("barbell"), Equipment.BARBELL);
        assertEquals(Equipment.fromString("bicycle"), Equipment.BICYCLE);
        assertEquals(Equipment.fromString("cable"), Equipment.CABLE);
        assertEquals(Equipment.fromString("chair"), Equipment.CHAIR);
        assertEquals(Equipment.fromString("cones"), Equipment.CONES);
        assertEquals(Equipment.fromString("dumbbell"), Equipment.DUMBBELL);
        assertEquals(Equipment.fromString("exercise ball"), Equipment.EXERCISE_BALL);
        assertEquals(Equipment.fromString("E-Z Curl Bar"), Equipment.EZ_CURL_BAR);
        assertEquals(Equipment.fromString("foam roll"), Equipment.FOAM_ROLL);
        assertEquals(Equipment.fromString("kettlebell"), Equipment.KETTLEBELL);
        assertEquals(Equipment.fromString("machine"), Equipment.MACHINE);
        assertEquals(Equipment.fromString("medicine ball"), Equipment.MEDICINE_BALL);
        assertEquals(Equipment.fromString("plate"), Equipment.PLATE);
        assertEquals(Equipment.fromString("rickshaw"), Equipment.RICKSHAW);
        assertEquals(Equipment.fromString("roller"), Equipment.ROLLER);
        assertEquals(Equipment.fromString("rope"), Equipment.ROPE);
        assertEquals(Equipment.fromString("row"), Equipment.ROW);
        assertEquals(Equipment.fromString("sled"), Equipment.SLED);
        assertEquals(Equipment.fromString("stone"), Equipment.STONE);
        assertEquals(Equipment.fromString("toning wheel"), Equipment.TONING_WHEEL);
        assertEquals(Equipment.fromString("trap bar"), Equipment.TRAP_BAR);
        assertEquals(Equipment.fromString("treadmill"), Equipment.TREADMILL);
        assertEquals(Equipment.fromString("workout box"), Equipment.WORKOUT_BOX);
    }

    @Test
    public void fromString_whenStringIsInvalid_returnNull() throws Exception {
        assertEquals(Equipment.fromString("dog"), null);
    }

    @Test
    public void fromString_whenStringIsNull_returnNull() throws Exception {
        assertEquals(Equipment.fromString(null), null);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsValid_returnEquipment() throws Exception {
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.bands_checkbox), Equipment.BANDS);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.barbell_checkbox), Equipment.BARBELL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.bicycle_checkbox), Equipment.BICYCLE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.cable_checkbox), Equipment.CABLE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.chair_checkbox), Equipment.CHAIR);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.cones_checkbox), Equipment.CONES);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.dumbbell_checkbox), Equipment.DUMBBELL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.exercise_ball_checkbox), Equipment.EXERCISE_BALL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.ez_curl_bar_checkbox), Equipment.EZ_CURL_BAR);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.foam_roll_checkbox), Equipment.FOAM_ROLL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.kettlebell_checkbox), Equipment.KETTLEBELL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.machine_checkbox), Equipment.MACHINE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.medicine_ball_checkbox), Equipment.MEDICINE_BALL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.plate_checkbox), Equipment.PLATE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.rickshaw_checkbox), Equipment.RICKSHAW);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.roller_checkbox), Equipment.ROLLER);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.rope_checkbox), Equipment.ROPE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.row_checkbox), Equipment.ROW);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.sled_checkbox), Equipment.SLED);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.stone_checkbox), Equipment.STONE);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.toning_wheel_checkbox), Equipment.TONING_WHEEL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.trap_bar_checkbox), Equipment.TRAP_BAR);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.treadmill_checkbox), Equipment.TREADMILL);
        assertEquals(Equipment.fromResourceCheckBoxId(R.id.workout_box_checkbox), Equipment.WORKOUT_BOX);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsInvalid_returnNull() throws Exception {
        assertEquals(Equipment.fromResourceCheckBoxId(43), null);
    }

    @Test
    public void fromResourceCheckBoxId_whenIdIsNull_returnNull() throws Exception {
        assertEquals(Equipment.fromResourceCheckBoxId(null), null);
    }

    @Test
    public void getEquipmentNameAlias() throws Exception {
        assertEquals(Equipment.BANDS.getEquipmentNameAlias(), "bands");
        assertEquals(Equipment.BARBELL.getEquipmentNameAlias(), "barbell");
        assertEquals(Equipment.BICYCLE.getEquipmentNameAlias(), "bicycle");
        assertEquals(Equipment.CABLE.getEquipmentNameAlias(), "cable");
        assertEquals(Equipment.CHAIR.getEquipmentNameAlias(), "chair");
        assertEquals(Equipment.CONES.getEquipmentNameAlias(), "cones");
        assertEquals(Equipment.DUMBBELL.getEquipmentNameAlias(), "dumbbell");
        assertEquals(Equipment.EXERCISE_BALL.getEquipmentNameAlias(), "exercise ball");
        assertEquals(Equipment.EZ_CURL_BAR.getEquipmentNameAlias(), "E-Z Curl Bar");
        assertEquals(Equipment.FOAM_ROLL.getEquipmentNameAlias(), "foam roll");
        assertEquals(Equipment.KETTLEBELL.getEquipmentNameAlias(), "kettlebell");
        assertEquals(Equipment.MACHINE.getEquipmentNameAlias(), "machine");
        assertEquals(Equipment.MEDICINE_BALL.getEquipmentNameAlias(), "medicine ball");
        assertEquals(Equipment.PLATE.getEquipmentNameAlias(), "plate");
        assertEquals(Equipment.RICKSHAW.getEquipmentNameAlias(), "rickshaw");
        assertEquals(Equipment.ROLLER.getEquipmentNameAlias(), "roller");
        assertEquals(Equipment.ROPE.getEquipmentNameAlias(), "rope");
        assertEquals(Equipment.ROW.getEquipmentNameAlias(), "row");
        assertEquals(Equipment.SLED.getEquipmentNameAlias(), "sled");
        assertEquals(Equipment.STONE.getEquipmentNameAlias(), "stone");
        assertEquals(Equipment.TONING_WHEEL.getEquipmentNameAlias(), "toning wheel");
        assertEquals(Equipment.TRAP_BAR.getEquipmentNameAlias(), "trap bar");
        assertEquals(Equipment.TREADMILL.getEquipmentNameAlias(), "treadmill");
        assertEquals(Equipment.WORKOUT_BOX.getEquipmentNameAlias(), "workout box");
    }

    @Test
    public void getAllEquipmentNameAliases() throws Exception {
        List<String> allEquipmentNameAliases = Equipment.allEquipmentNameAliases();

        assertEquals(allEquipmentNameAliases.get(0), "bands");
        assertEquals(allEquipmentNameAliases.get(1), "barbell");
        assertEquals(allEquipmentNameAliases.get(2), "bicycle");
        assertEquals(allEquipmentNameAliases.get(3), "bosu ball");
        assertEquals(allEquipmentNameAliases.get(4), "cable");
        assertEquals(allEquipmentNameAliases.get(5), "chair");
        assertEquals(allEquipmentNameAliases.get(6), "cones");
        assertEquals(allEquipmentNameAliases.get(7), "dumbbell");
        assertEquals(allEquipmentNameAliases.get(8), "exercise ball");
        assertEquals(allEquipmentNameAliases.get(9), "E-Z Curl Bar");
        assertEquals(allEquipmentNameAliases.get(10), "foam roll");
        assertEquals(allEquipmentNameAliases.get(11), "jumping rope");
        assertEquals(allEquipmentNameAliases.get(12), "kettlebell");
        assertEquals(allEquipmentNameAliases.get(13), "machine");
        assertEquals(allEquipmentNameAliases.get(14), "medicine ball");
        assertEquals(allEquipmentNameAliases.get(15), "plate");
        assertEquals(allEquipmentNameAliases.get(16), "rickshaw");
        assertEquals(allEquipmentNameAliases.get(17), "roller");
        assertEquals(allEquipmentNameAliases.get(18), "rope");
        assertEquals(allEquipmentNameAliases.get(19), "row");
        assertEquals(allEquipmentNameAliases.get(20), "sled");
        assertEquals(allEquipmentNameAliases.get(21), "stone");
        assertEquals(allEquipmentNameAliases.get(22), "straps");
        assertEquals(allEquipmentNameAliases.get(23), "t-bar");
        assertEquals(allEquipmentNameAliases.get(24), "toning wheel");
        assertEquals(allEquipmentNameAliases.get(25), "trap bar");
        assertEquals(allEquipmentNameAliases.get(26), "treadmill");
        assertEquals(allEquipmentNameAliases.get(27), "workout box");
        assertEquals(allEquipmentNameAliases.get(28), "v-bar");
    }
}
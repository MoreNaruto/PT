package tmosq.com.pt.fragment;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M)
public class DifficultyFragmentTest {
    private DifficultyFragment difficultyFragment;

    @Before
    public void setUp() throws Exception {
        difficultyFragment = new DifficultyFragment();

        startFragment(difficultyFragment);
        assertNotNull(difficultyFragment);
    }

    @Test
    public void getDifficulty() throws Exception {
        difficultyFragment.binding.workoutDifficultyDropdownMenu.setSelection(0);
        assertEquals(difficultyFragment.getDifficulty(), "basic");

        difficultyFragment.binding.workoutDifficultyDropdownMenu.setSelection(1);
        assertEquals(difficultyFragment.getDifficulty(), "intermediate");
    }

}
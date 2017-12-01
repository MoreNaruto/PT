package tmosq.com.pt.viewModel;

import android.databinding.InverseBindingListener;
import android.support.v7.widget.AppCompatSpinner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.fragment.LengthOfWorkoutFragment;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class LengthOfWorkoutViewModelTest {
    private LengthOfWorkoutViewModel lengthOfWorkoutViewModel;
    private AppCompatSpinner lengthOfWorkoutSpinner;

    @Mock
    InverseBindingListener mockInverseBindingListener;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        LengthOfWorkoutFragment fragment = new LengthOfWorkoutFragment();
        startFragment(fragment);

        lengthOfWorkoutSpinner = fragment.binding.workoutLengthDropdownMenu;
        lengthOfWorkoutViewModel = new LengthOfWorkoutViewModel();
    }

    @Test
    public void bindSpinnerData() throws Exception {
        LengthOfWorkoutViewModel.bindSpinnerData(lengthOfWorkoutSpinner, 30, mockInverseBindingListener);
        lengthOfWorkoutSpinner.setSelection(0);
        verify(mockInverseBindingListener).onChange();
    }

    @Test
    public void captureSelectedValue() throws Exception {
        lengthOfWorkoutSpinner.setSelection(0);
        assertEquals(LengthOfWorkoutViewModel.captureSelectedValue(lengthOfWorkoutSpinner).intValue(), 30);

        lengthOfWorkoutSpinner.setSelection(1);
        assertEquals(LengthOfWorkoutViewModel.captureSelectedValue(lengthOfWorkoutSpinner).intValue(), 45);
    }
}
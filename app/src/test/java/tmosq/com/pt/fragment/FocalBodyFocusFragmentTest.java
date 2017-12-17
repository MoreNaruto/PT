package tmosq.com.pt.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import tmosq.com.pt.viewModel.FocalBodyFocusViewModel;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class FocalBodyFocusFragmentTest {
    private FocalBodyFocusFragment focalBodyFocusFragment;

    @Before
    public void setUp() throws Exception {
        focalBodyFocusFragment = new FocalBodyFocusFragment();

        startFragment(focalBodyFocusFragment);
        assertNotNull(focalBodyFocusFragment);
    }

    @Test
    public void onCreateView_shouldBindToViewModel() throws Exception {
        assertTrue(focalBodyFocusFragment.binding.getViewModel().equals(focalBodyFocusFragment.viewModel));
    }

    @Test
    public void getActiveBodyFocuses() throws Exception {
        focalBodyFocusFragment.viewModel = mock(FocalBodyFocusViewModel.class);
        focalBodyFocusFragment.getActiveBodyFocuses();
        verify(focalBodyFocusFragment.viewModel).getActiveBodyFocuses();
    }
}
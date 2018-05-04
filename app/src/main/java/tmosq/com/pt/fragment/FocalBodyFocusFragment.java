package tmosq.com.pt.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.FocalBodyFocusFragmentBinding;
import tmosq.com.pt.viewModel.FocalBodyFocusViewModel;

public class FocalBodyFocusFragment extends Fragment{
    public static final String TAG = FocalBodyFocusFragment.class.getSimpleName();
    protected FocalBodyFocusViewModel viewModel;
    protected FocalBodyFocusFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.focal_body_focus_fragment, container, false);
        View view = binding.getRoot();

        viewModel = new FocalBodyFocusViewModel();
        binding.setViewModel(viewModel);
        return view;
    }

    public List<String> getActiveBodyFocuses() {
        return viewModel.getActiveBodyFocuses();
    }
}

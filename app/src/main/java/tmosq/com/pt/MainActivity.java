package tmosq.com.pt;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tmosq.com.pt.databinding.MainActivityBinding;
import tmosq.com.pt.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        MainViewModel mainViewModel = new MainViewModel(this);
        binding.setViewModel(mainViewModel);
    }
}

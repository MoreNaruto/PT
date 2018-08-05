package tmosq.com.pt.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.ActivityTabataWorkoutBinding;

public class TabataWorkoutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTabataWorkoutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tabata_workout);

        setContentView(binding.getRoot());
    }
}

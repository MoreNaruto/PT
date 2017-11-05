package tmosq.com.pt.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.WarmUpAndCoolOffExerciseListItemLayoutBinding;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.viewModel.WarmUpAndCoolOffExerciseViewModel;

public class CoolOffAdapter extends RecyclerView.Adapter<CoolOffAdapter.ViewHolder> {
    private List<Exercise> exercises;

    public CoolOffAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public CoolOffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WarmUpAndCoolOffExerciseListItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.warm_up_and_cool_off_exercise_list_item_layout, parent, false);
        return new CoolOffAdapter.ViewHolder(
                binding,
                new WarmUpAndCoolOffExerciseViewModel()
        );
    }

    @Override
    public void onBindViewHolder(CoolOffAdapter.ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private WarmUpAndCoolOffExerciseListItemLayoutBinding binding;
        private WarmUpAndCoolOffExerciseViewModel viewModel;

        public ViewHolder(WarmUpAndCoolOffExerciseListItemLayoutBinding binding, WarmUpAndCoolOffExerciseViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(final Exercise exercise) {
            viewModel.setExercise(exercise, false);
            binding.setViewModel(viewModel);
        }
    }
}

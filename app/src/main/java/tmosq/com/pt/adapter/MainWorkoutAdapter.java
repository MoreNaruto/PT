package tmosq.com.pt.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.databinding.MainWorkoutExerciseListViewItemBinding;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.viewModel.MainWorkoutExerciseViewModel;


public class MainWorkoutAdapter extends RecyclerView.Adapter<MainWorkoutAdapter.ViewHolder> {
    private List<Exercise> exercises;

    public MainWorkoutAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public MainWorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainWorkoutExerciseListViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.main_workout_exercise_list_view_item, parent, false);
        return new MainWorkoutAdapter.ViewHolder(
                binding,
                new MainWorkoutExerciseViewModel()
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private MainWorkoutExerciseListViewItemBinding binding;
        private MainWorkoutExerciseViewModel viewModel;

        public ViewHolder(MainWorkoutExerciseListViewItemBinding binding, MainWorkoutExerciseViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(final Exercise exercise) {
            viewModel.setExercise(exercise);
            binding.setViewModel(viewModel);
        }
    }
}
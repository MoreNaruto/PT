package tmosq.com.pt.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tmosq.com.pt.R;
import tmosq.com.pt.activity.WorkoutDetailActivity;
import tmosq.com.pt.databinding.WorkoutExerciseListViewItemBinding;
import tmosq.com.pt.model.Exercise;
import tmosq.com.pt.viewModel.WorkoutExerciseViewModel;

import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT;
import static tmosq.com.pt.activity.WorkoutDetailActivity.WORKOUT_DESCRIPTION;

public class CoolOffAdapter extends RecyclerView.Adapter<CoolOffAdapter.ViewHolder> {
    private List<Exercise> exercises;

    public CoolOffAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public CoolOffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkoutExerciseListViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.workout_exercise_list_view_item, parent, false);
        return new CoolOffAdapter.ViewHolder(
                binding,
                new WorkoutExerciseViewModel()
        );
    }

    @Override
    public void onBindViewHolder(CoolOffAdapter.ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private WorkoutExerciseListViewItemBinding binding;
        private WorkoutExerciseViewModel viewModel;

        public ViewHolder(WorkoutExerciseListViewItemBinding binding, WorkoutExerciseViewModel viewModel) {
            super(binding.getRoot());
            this.binding = binding;
            this.viewModel = viewModel;
        }

        public void bind(final Exercise exercise) {
            viewModel.setExercise(exercise, false);
            binding.setViewModel(viewModel);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, WorkoutDetailActivity.class);
                    intent.putExtra(WORKOUT, exercise.getWorkout());
                    intent.putExtra(WORKOUT_DESCRIPTION, exercise.getDescription());
                    context.startActivity(intent);
                }
            });
        }
    }
}

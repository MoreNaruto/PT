package tmosq.com.pt.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import lombok.NoArgsConstructor;
import tmosq.com.pt.R;
import tmosq.com.pt.databinding.WorkoutRegimentRecyclerViewItemBinding;
import tmosq.com.pt.model.exercise_support_enums.WorkoutRegiment;

@NoArgsConstructor
public class WorkoutRegimentRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRegimentRecyclerViewAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkoutRegimentRecyclerViewItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.workout_regiment_recycler_view_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkoutRegiment workoutRegiment = WorkoutRegiment.values()[position];

        holder.bind(workoutRegiment);
    }

    @Override
    public int getItemCount() {
        return WorkoutRegiment.values().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private WorkoutRegimentRecyclerViewItemBinding binding;

        ViewHolder(WorkoutRegimentRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(WorkoutRegiment workoutRegiment){
            binding.workoutRegimentTextView.setText(workoutRegiment.getWorkoutRegimentTitle());
            binding.workoutRegimentImageView.setImageResource(workoutRegiment.getImageId());
            binding.workoutRegimentImageView.setContentDescription(workoutRegiment.getContentDescription());
            binding.workoutRegimentRelativeLayout.setBackgroundColor(workoutRegiment.getColorBackgroundId());
        }
    }
}

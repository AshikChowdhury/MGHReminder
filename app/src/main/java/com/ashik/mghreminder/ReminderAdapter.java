package com.ashik.mghreminder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ashik.mghreminder.data.Reminder;

public class ReminderAdapter extends ListAdapter<Reminder, ReminderAdapter.ReminderHolder> {
    private OnItemClickListener listener;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;
    public ReminderAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Reminder> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reminder>() {
        @Override
        public boolean areItemsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reminder oldItem, @NonNull Reminder newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getAddress().equals(newItem.getAddress()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTime().equals(newItem.getTime());
        }
    };

    @NonNull
    @Override
    public ReminderAdapter.ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_items, parent, false);
        return new ReminderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ReminderHolder holder, int position) {
        Reminder currentReminder = getItem(position);
        String title = currentReminder.getTitle();
        String active = currentReminder.getActive();
        String letter = "A";
        if(title != null && !title.isEmpty()) {
            letter = currentReminder.getTitle().substring(0, 1);
        }
        int color = mColorGenerator.getRandomColor();
        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);

        holder.textViewTitle.setText(currentReminder.getTitle());
        holder.textViewDateTime.setText((currentReminder.getDate()+" | "+currentReminder.getTime()));
        holder.textViewLocation.setText(currentReminder.getAddress());
        holder.mThumbnailImage.setImageDrawable(mDrawableBuilder);
        if(active.equals("false")){
            holder.mActiveImage.setImageResource(R.drawable.ic_notifications_off);
        }else if (active.equals("True")) {
            holder.mActiveImage.setImageResource(R.drawable.ic_notifications_active_black_24dp);
        }
    }

    public Reminder getNoteAt(int position) {
        return getItem(position);
    }

    class ReminderHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDateTime;
        private TextView textViewLocation;
        private ImageView mActiveImage , mThumbnailImage;

        public ReminderHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.recycle_title);
            textViewDateTime = itemView.findViewById(R.id.recycle_date_time);
            textViewLocation = itemView.findViewById(R.id.recycle_location_info);
            mActiveImage = itemView.findViewById(R.id.active_image);
            mThumbnailImage = itemView.findViewById(R.id.thumbnail_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Reminder reminder);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

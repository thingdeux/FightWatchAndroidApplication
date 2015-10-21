package watch.fight.android.fightbrowser.Events;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import watch.fight.android.fightbrowser.Brackets.BracketActivity;
import watch.fight.android.fightbrowser.Events.models.Bracket;
import watch.fight.android.fightbrowser.Events.models.Event;
import watch.fight.android.fightbrowser.Events.models.DB.EventDB;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.DateParser;

/**
 * Created by josh on 9/16/15.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    private List<Event> mEvents;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mEventName;
        public TextView mEventDetails;
        public TextView mEventDateRange;
        public ImageView mHeaderImage;
        public Button mBracketsButton;
        public Button mViewWebPageButton;

        public ViewHolder(View v) {
            super(v);
            mEventName = (TextView) v.findViewById(R.id.event_name);
            mEventDetails = (TextView) v.findViewById(R.id.event_details);
            mEventDateRange = (TextView) v.findViewById(R.id.event_date_range);
            mBracketsButton = (Button) v.findViewById(R.id.event_brackets_button);
            mViewWebPageButton = (Button) v.findViewById(R.id.event_webpage_button);
            mHeaderImage = (ImageView) v.findViewById(R.id.event_header_image);
        }

    }

    public EventsAdapter(Context c) {
        mContext = c;
        mEvents = EventDB.getInstance(c.getApplicationContext()).getAllUpcomingEvents();
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Event event = mEvents.get(position);
        final Uri ImageHeader = event.getHeaderImageAsUri();
        final Date startDate = event.getStartDateObj();
        final Date endDate = event.getEndDateObj();
        final Uri eventSite = event.getWebsiteAsUri();

        holder.mEventName.setText(event.getEventName());
        if (event.getFlavorText() != null) {
            holder.mEventDetails.setText(event.getFlavorText());
        }

        // TODO : Use String resources with placeholders
        holder.mEventDateRange.setText(
                DateParser.dateToSimpleDateStr(startDate) + " - " +
                        DateParser.dateToSimpleDateStr(endDate)
        );

        holder.mBracketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(BracketActivity.NewInstance(mContext, event.getId()));
            }
        });

        if (eventSite != null && !eventSite.toString().isEmpty()) {
            holder.mViewWebPageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(eventSite);
                    v.getContext().startActivity(intent);
                    }
                });
        } else {
            holder.mViewWebPageButton.setEnabled(false);
        }

        Picasso.with(this.mContext).load(ImageHeader)
                .into(holder.mHeaderImage);
    }

    @Override
    public int getItemCount() { return mEvents.size(); }

}

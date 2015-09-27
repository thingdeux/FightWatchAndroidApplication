package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.InformationFeeds.models.StoryDB;
import watch.fight.android.fightbrowser.R;

/**
 * Created by josh on 9/25/15.
 */
public class InformationFeedsAdapter extends RecyclerView.Adapter<InformationFeedsAdapter.ViewHolder> {
    private List<Story> mStories;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mStorySiteName;
        public TextView mStoryTitle;
        public Uri mLinkUri;
        public Button mReadMoreButton;
        public Button mShareButton;

        public ViewHolder(View v) {
            super(v);
            mStoryTitle = (TextView) v.findViewById(R.id.feed_title);
            mStorySiteName = (TextView) v.findViewById(R.id.feed_site_name);
            mReadMoreButton = (Button) v.findViewById(R.id.feed_read_more_button);
            mShareButton = (Button) v.findViewById(R.id.feed_share_button);
        }

    }

    public InformationFeedsAdapter(Context c) {
        mContext = c;
        mStories = StoryDB.getInstance(c.getApplicationContext()).getAllStories();
    }

    @Override
    public InformationFeedsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_feed_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Story story = mStories.get(position);
        final Uri storyUrl = story.getUrl();
        final String storyTitle = story.getTitle();

        holder.mStorySiteName.setText(story.getSiteName());
        holder.mStoryTitle.setText(story.getTitle());
        holder.mLinkUri = story.getUrl();

        holder.mReadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyUrl != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(storyUrl);
                    v.getContext().startActivity(intent);
                }
            }
        });

        holder.mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyTitle != null && storyUrl != null) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_SUBJECT, storyTitle);
                    intent.putExtra(Intent.EXTRA_TEXT, storyUrl.toString());
                    intent.setType("text/plain");
                    v.getContext().startActivity(Intent.createChooser(intent, "Share Story With ..."));
                }
            }
        });
    }

    @Override
    public int getItemCount() { return mStories.size(); }
}

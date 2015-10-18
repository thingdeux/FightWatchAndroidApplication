package watch.fight.android.fightbrowser.InformationFeeds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryTracker;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryTrackerDB;
import watch.fight.android.fightbrowser.InformationFeeds.models.Story;
import watch.fight.android.fightbrowser.InformationFeeds.models.DB.StoryDB;
import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.Utils.DateParser;
import watch.fight.android.fightbrowser.Utils.SharedPreferences;

/**
 * Created by josh on 9/25/15.
 */
public class InformationFeedsAdapter extends RecyclerView.Adapter<InformationFeedsAdapter.ViewHolder> {
    private List<Story> mStories;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mStorySiteName;
        public TextView mStoryTitle;
        public TextView mStoryPublishedDate;
        public ImageView mPreviewImage;
        public Uri mLinkUri;
        public Button mReadMoreButton;
        public Button mShareButton;
        public Button mMarkAsReadButton;

        public ViewHolder(View v) {
            super(v);
            mStoryTitle = (TextView) v.findViewById(R.id.feed_title);
            mStorySiteName = (TextView) v.findViewById(R.id.feed_site_name);
            mStoryPublishedDate = (TextView) v.findViewById(R.id.feed_published_date);
            mReadMoreButton = (Button) v.findViewById(R.id.feed_read_more_button);
            mShareButton = (Button) v.findViewById(R.id.feed_share_button);
            mMarkAsReadButton = (Button) v.findViewById(R.id.mark_as_read);
            mPreviewImage = (ImageView) v.findViewById(R.id.feed_icon);
        }

    }

    public InformationFeedsAdapter(Context c) {
        mContext = c;
        refreshStories();
    }

    public void refreshStories() {
        if (mStories != null) {
            mStories.clear();
        } else {
            mStories = new ArrayList<>();
        }

        List<Story> latestStories = StoryDB.getInstance(mContext.getApplicationContext()).getAllUnfilteredStories();

        if (!SharedPreferences.getShowFilteredFeeds(mContext)) {
            HashSet<String> markedRead = StoryTrackerDB.getInstance(mContext.getApplicationContext()).getAllTrackers();
            for (int i = 0; i < latestStories.size(); i++) {
                if (latestStories.get(i) != null && latestStories.get(i).getUrl() != null) {
                    if (!markedRead.contains(latestStories.get(i).getUrl().toString())) {
                        mStories.add(latestStories.get(i));
                    }
                }
            }
        } else {
            mStories = latestStories;
        }

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
        final String mThumbnail = story.getThumbnail();

        holder.mStorySiteName.setText(story.getSiteName());
        holder.mStoryTitle.setText(story.getTitle());
        holder.mLinkUri = story.getUrl();
        holder.mStoryPublishedDate.setText(DateParser.dateToSimpleDateStr(story.getPublishedDate()));

        holder.mReadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storyUrl != null) {
                    if (SharedPreferences.getToWebViewOrNotToWebView(mContext)) {
                        Intent i = InformationFeedsWebViewActivity.NewInstance(mContext, storyUrl.toString());
                        mContext.startActivity(i);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(storyUrl);
                        v.getContext().startActivity(intent);
                    }

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
                    v.getContext().startActivity(Intent.createChooser(intent, v.getResources().getText(R.string.intent_text_share_feed)));
                }
            }
        });

        holder.mMarkAsReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (story.getUrl() != null) {
                    StoryTrackerDB.getInstance(mContext.getApplicationContext())
                            .addStoryTracker(new StoryTracker(story.getUrl().toString()));
                    mStories.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            }
        });

        if (mThumbnail != null && !mThumbnail.isEmpty()) {
            Picasso.with(this.mContext).load(mThumbnail)
                    .placeholder(R.mipmap.fist_icon)
                    .resize(200, 200)
                    .centerCrop()
                    .into(holder.mPreviewImage);
        } else {
            Picasso.with(this.mContext).load(R.mipmap.fist_icon)
                    .resize(200, 200)
                    .centerCrop()
                    .into(holder.mPreviewImage);
        }

    }

    @Override
    public int getItemCount() { return mStories.size(); }
}

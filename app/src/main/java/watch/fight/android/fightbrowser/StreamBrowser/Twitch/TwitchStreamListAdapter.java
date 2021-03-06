package watch.fight.android.fightbrowser.StreamBrowser.Twitch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.UUID;

import watch.fight.android.fightbrowser.R;
import watch.fight.android.fightbrowser.StreamBrowser.Twitch.models.TwitchStreamInfo;
import watch.fight.android.fightbrowser.Utils.IntentUtils;

/**
 * Created by josh on 9/11/15.
 */
public class TwitchStreamListAdapter extends RecyclerView.Adapter<TwitchStreamListAdapter.ViewHolder> {
    private TwitchStreamHolder mStreamHolder;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mCardView;
        public TextView mTextViewStreamInfo;
        public TextView mTextViewStreamChannelName;
        public ImageView mImageView;
        public String mStreamUrl;
        public String mStreamWebUrl;
        public Context mContext;

        public ViewHolder(View v) {
            super(v);
            mContext = v.getContext();
            mTextViewStreamChannelName = (TextView) v.findViewById(R.id.twitch_stream_broadcaster_name);
            mTextViewStreamInfo = (TextView) v.findViewById(R.id.twitch_stream_title);
            mImageView = (ImageView) v.findViewById(R.id.twitch_stream_header);
            mCardView = v.findViewById(R.id.card_view);
        }
    }

    // Provide constructor for the dataset to use in the recycler view
    public TwitchStreamListAdapter(@NonNull UUID uniqueFragmentId) {
        mStreamHolder = TwitchStreamHolder.getInstance(uniqueFragmentId);
    }

    @Override
    public TwitchStreamListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new View
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.browser_list_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (called by layout manager
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Get element from the dataset and replace the current contents of the viewholder with it
        holder.mImageView.setImageResource(R.drawable.twitch_stream_default);

//        Picasso.with(holder.mContext).setIndicatorsEnabled(true);
        Picasso.with(holder.mContext)
                .load(mStreamHolder.getStream(position).getPreview().getMediumImage())
                .placeholder(R.drawable.twitch_stream_default)
                .into(holder.mImageView);

        TwitchStreamInfo stream = mStreamHolder.getStream(position);
        // Lop off the game name if it's greater than 12 Characters and replace with ...
        String gameTitle = stream.getGame();
        if (gameTitle != null) {
            if (gameTitle.length() >= 12) {
                gameTitle = gameTitle.substring(0, 11) + "...";
            }
        } else {
            gameTitle = " ??";
        }

        // TODO : Update this to use string resource replacement
        holder.mTextViewStreamInfo.setText("Playing " + gameTitle +
                " for " + stream.getViewers() + " viewers");

        holder.mTextViewStreamChannelName.setText(stream.getChannel().getName());

        // Set Click Listener to get twitch url
        holder.mStreamUrl = "twitch://open?stream=" + mStreamHolder.getStream(position).getChannel().getName();
        holder.mStreamWebUrl = mStreamHolder.getStream(position).getChannel().getUrl();

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TwitchStreamList", "Opening Stream " + holder.mStreamUrl);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(holder.mStreamUrl));
                if (IntentUtils.isIntentSupported(v.getContext(), i)) {
                    v.getContext().startActivity(Intent.createChooser(i,
                            v.getResources().getText(R.string.intent_text_open_stream)));
                } else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    webIntent.setData(Uri.parse(holder.mStreamWebUrl));
                    v.getContext().startActivity(Intent.createChooser(webIntent,
                            v.getResources().getText(R.string.intent_text_open_stream)));
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        if (mStreamHolder != null && mStreamHolder.getStreams() != null) {
            return mStreamHolder.getStreams().length;
        } else {
            return 0;
        }
    }

    private boolean isTwitchAppInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}

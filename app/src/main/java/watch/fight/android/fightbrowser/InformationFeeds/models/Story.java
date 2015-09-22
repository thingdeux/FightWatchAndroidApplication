package watch.fight.android.fightbrowser.InformationFeeds.models;

import android.net.Uri;

import java.util.Date;

/**
 * Created by josh on 9/22/15.
 */
public class Story {
    public String Title;
    public String Description;
    public Uri Url;
    public String Author;
    public Date PublishedDate;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Uri getUrl() {
        return Url;
    }

    public void setUrl(Uri url) {
        Url = url;
    }

    public Date getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        PublishedDate = publishedDate;
    }
}

package watch.fight.android.fightbrowser.Utils;

import android.net.Uri;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by josh on 9/21/15.
 */
public class NetworkUtils {
    private static String TAG = NetworkUtils.class.getSimpleName();

    public static String InputStreamToString(InputStream stream) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (IOException ioe) {
            Log.e(TAG, "Unable to convert inputStreamToString");
        }
        return null;
    }

    public static String parseRssFeed(String rssURL) {
        try {
            URL url = new URL(rssURL);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.parse(new InputSource(url.openStream()));
            Log.v(TAG, "Xr Parser Response: xr.toString()");

        } catch (ParserConfigurationException pce) {
            return null;
        } catch (SAXException saxe) {
            return null;
        } catch (MalformedURLException mue) {
            Log.e(TAG, "Malformed URL for RSS Parser: " + rssURL);
        } catch (IOException ioe) {
            Log.e(TAG, "IO Exception Error " + "for URL (" + rssURL + "): " + ioe);
        }
        return null;
    }
}

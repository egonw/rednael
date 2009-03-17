import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jibble.pircbot.PircBot;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.impl.FeedFetcherCache;
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;

public class LeanderBot extends PircBot {

    /**
     * Keeps track of the latest entries.
     */
    private Queue<String> queue;

    public LeanderBot() {
        this.setName("rednael");
        queue = new LinkedList<String>();
    }
    
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
        try {
            URL feedUrl = new URL("http://cdk.git.sourceforge.net/git/gitweb.cgi?p=cdk;a=rss;h=refs/heads/cdk-1.2.x");
            FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance();
            FeedFetcher fetcher = new HttpURLFeedFetcher(feedInfoCache);

            //                FetcherEventListenerImpl listener = new FetcherEventListenerImpl(this, channel);
            //                fetcher.addFetcherEventListener(listener);

            SyndFeed feed = null;
            int maxItems = 2;
            boolean firstboot = true;
            boolean done = false;
            while (!done) {
                feed = fetcher.retrieveFeed(feedUrl);
                int itemCount = 0;
                List<SyndEntry> entries = feed.getEntries();
                for (SyndEntry entry : entries) {
                    itemCount++;
                    String title = entry.getTitle();
                    if (!queue.contains(title)) {
                        queue.add(title);
                        if (!firstboot) {
                            if (queue.size() > 0) queue.remove();
                            if (itemCount <= maxItems) {
                                sendMessage(channel, title);
                                sendMessage(channel, "  " + entry.getPublishedDate());
                                sendMessage(channel, "  " + entry.getAuthor());
                                sendMessage(channel, "  " + entry.getLink());
                            }
                        }
                    }
                }
                firstboot = false;
                Thread.sleep(15000);
            }
        } catch (Exception ex) {
            sendMessage(channel, "Error while fetching blog: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        
        // Now start our bot up.
        LeanderBot bot = new LeanderBot();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.freenode.net");

        // Join the #pircbot channel.
        bot.joinChannel("#cdk");
        
    }

//    static class FetcherEventListenerImpl implements FetcherListener {
//        
//        LeanderBot bot;
//        String channel;
//        
//        public FetcherEventListenerImpl(LeanderBot bot, String channel) {
//            this.bot = bot;
//            this.channel = channel;
//        }
//        
//        /**
//         * @see com.sun.syndication.fetcher.FetcherListener#fetcherEvent(com.sun.syndication.fetcher.FetcherEvent)
//         */
//        public void fetcherEvent(FetcherEvent event) {
//            String eventType = event.getEventType();
//            if (FetcherEvent.EVENT_TYPE_FEED_POLLED.equals(eventType)) {
////                bot.sendMessage(channel, "Feed Polled. URL = " + event.getUrlString());
//            } else if (FetcherEvent.EVENT_TYPE_FEED_RETRIEVED.equals(eventType)) {
////                bot.sendMessage(channel, "Feed Retrieved. URL = " + event.getUrlString());
//            } else if (FetcherEvent.EVENT_TYPE_FEED_UNCHANGED.equals(eventType)) {
////                bot.sendMessage(channel, "Feed Unchanged. URL = " + event.getUrlString());
//            }
//        }
//    }
}



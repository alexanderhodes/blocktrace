package me.alexanderhodes.blocktrace.model.blockchain;

import me.alexanderhodes.blocktrace.model.Tracking;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * Created by alexa on 30.09.2017.
 */
@NamedQueries({
        @NamedQuery(name = TrackingID.GET_TRACKINGID_NOTSAVED, query = TrackingID.GET_TRACKINGID_NOTSAVED_QUERY),
        @NamedQuery(name = TrackingID.GET_LATEST_TRACKINGID, query = TrackingID.GET_LATEST_TRACKINGID_QUERY)
})
@Entity
public class TrackingID implements Serializable {

    public static final String GET_TRACKINGID_NOTSAVED = "TrackingId.NotSaved";
    static final String GET_TRACKINGID_NOTSAVED_QUERY = "SELECT t FROM TrackingID t WHERE t.saved = false";

    public static final String GET_LATEST_TRACKINGID = "TrackingId.LatestID";
    static final String GET_LATEST_TRACKINGID_QUERY = "SELECT t FROM TrackingID t ORDER BY t.id DESC";

    @Id
    private long id;
    private boolean saved;
    private String timestamp;

    public TrackingID () {

    }

    public TrackingID (long id, boolean saved) {
        this.id = id;
        this.saved = saved;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{ \"$class\": \"me.alexanderhodes.blocktrace.Track\", \"tracking\": \"" + id +
                "\", \"timestamp\": \"" + timestamp +  "\" }";
    }
}

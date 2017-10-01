package me.alexanderhodes.blocktrace.util;

import me.alexanderhodes.blocktrace.model.Tracking;

import java.util.Comparator;

/**
 * Created by alexa on 29.09.2017.
 */
public class TrackingComparator implements Comparator<Tracking> {

    @Override
    public int compare (Tracking tracking1, Tracking tracking2) {
        int tracking1Id = Integer.getInteger(String.valueOf(tracking1.getId()));
        int tracking2Id = Integer.getInteger(String.valueOf(tracking2.getId()));

        return Integer.valueOf(tracking1Id).compareTo(tracking2Id);
    }

}

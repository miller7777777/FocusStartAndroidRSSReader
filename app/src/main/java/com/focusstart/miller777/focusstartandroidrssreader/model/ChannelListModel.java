package com.focusstart.miller777.focusstartandroidrssreader.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ChannelListModel implements Serializable {

    private List<ChannelModel> channels;

    public ChannelListModel(List<ChannelModel> channels) {
        this.channels = channels;
    }

    public List<ChannelModel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<ChannelModel> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

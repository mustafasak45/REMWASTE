package com.opensource.core.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class GorestMetaPojo {

    private Object meta;
    private GorestDataPojo data;

    public GorestMetaPojo() {
    }

    public GorestMetaPojo(Object meta, GorestDataPojo data) {
        this.meta = meta;
        this.data = data;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public GorestDataPojo getData() {
        return data;
    }

    public void setData(GorestDataPojo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GorestMetaPojo{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}

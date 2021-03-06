/*
 * Copyright 2013 David Tinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.qdb.server.model;

import java.util.Map;

/**
 * A queue output. Outputs push messages coming into a queue somewhere else (e.g. to RabbitMQ).
 */
public class Output extends ModelObject {

    private String queue;
    private String type;
    private String url;
    private boolean enabled;
    private long fromId;
    private long toId;
    private long atId;
    private long from;
    private long to;
    private long at;
    private long limit;
    private int updateIntervalMs;
    private double warnAfter;
    private double errorAfter;
    private String filter;
    private String routingKey;
    private String grep;
    private Map<String, Object> params;

    public Output() {
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public long getAtId() {
        return atId;
    }

    public void setAtId(long atId) {
        this.atId = atId;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public long getAt() {
        return at;
    }

    public void setAt(long at) {
        this.at = at;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public int getUpdateIntervalMs() {
        return updateIntervalMs;
    }

    public void setUpdateIntervalMs(int updateIntervalMs) {
        this.updateIntervalMs = updateIntervalMs;
    }

    public double getWarnAfter() {
        return warnAfter;
    }

    public void setWarnAfter(double warnAfter) {
        this.warnAfter = warnAfter;
    }

    public double getErrorAfter() {
        return errorAfter;
    }

    public void setErrorAfter(double errorAfter) {
        this.errorAfter = errorAfter;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getGrep() {
        return grep;
    }

    public void setGrep(String grep) {
        this.grep = grep;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return super.toString() + ":queue=" + queue + ":type=" + type;
    }

    public Output deepCopy() {
        Output o = (Output)clone();
        if (params != null) o.params = CLONER.deepClone(params);
        return o;
    }
}

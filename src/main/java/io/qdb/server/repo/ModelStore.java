package io.qdb.server.repo;

import com.google.common.eventbus.EventBus;
import io.qdb.server.model.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Keeps model objects in a map.
 */
class ModelStore<T extends ModelObject> {

    private final ConcurrentMap<String, T> map = new ConcurrentHashMap<String, T>();
    private final EventBus eventBus;
    private ModelEvent.Factory<T> eventFactory;

    ModelStore(List<T> list, EventBus eventBus) {
        if (list != null) {
            for (T o : list) {
                if (map.putIfAbsent(o.getId(), o) != null) {
                    throw new IllegalStateException("Duplicate id " + o);
                }
            }
        }
        this.eventBus = eventBus;
    }

    public void setEventFactory(ModelEvent.Factory<T> eventFactory) {
        this.eventFactory = eventFactory;
    }

    @SuppressWarnings("unchecked")
    public T find(String id) {
        T o = map.get(id);
        return o == null ? null : (T)o.clone();
    }

    public T create(T o) {
        if (map.putIfAbsent(o.getId(), o) != null) throw new DuplicateIdException("Duplicate id " + tos(o));
        if (eventFactory != null) eventBus.post(eventFactory.createEvent(ModelEvent.Type.ADDED, o));
        return o;
    }

    public T update(T o) {
        ModelObject existing = map.get(o.getId());
        if (existing == null) throw new OptLockException(tos(o) + " not found");
        if (existing.getVersion() != o.getVersion()) {
            throw new OptLockException(tos(o) + " has incorrect version " + o.getVersion() +
                    " (expected " + existing.getVersion() + ")");
        }
        o.incVersion();
        map.put(o.getId(), o);
        return o;
    }

    public void delete(T o) {
        ModelObject existing = map.get(o.getId());
        if (existing == null) return;
        if (existing.getVersion() != o.getVersion()) {
            throw new OptLockException(tos(o) + " has incorrect version " + o.getVersion() +
                    " (expected " + existing.getVersion() + ")");
        }
        map.remove(o.getId(), o);
    }

    @SuppressWarnings("unchecked")
    public List<T> find(int offset, int limit) throws IOException {
        if (limit < 0) limit = Integer.MAX_VALUE - offset;
        List<T> list = new ArrayList<T>(map.values());
        Collections.sort(list);
        int n = list.size();
        if (offset == 0 && limit >= n) return list;
        return offset >= n ? Collections.EMPTY_LIST : list.subList(offset, Math.min(limit, n));
    }

    public int size() throws IOException {
        return map.size();
    }

    public List<T> values() {
        return new ArrayList<T>(map.values());
    }

    private static String tos(ModelObject o) {
        return o == null ? "null" : o.getClass().getSimpleName() + ":" + o.getId();
    }

}

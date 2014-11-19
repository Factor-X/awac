package eu.factorx.awac.util;

import java.util.*;

public class CUD<T> {

    private List<T> created = new ArrayList<>();
    private List<T> updated = new ArrayList<>();
    private List<T> deleted = new ArrayList<>();

    private CUD(List<T> created, List<T> updated, List<T> deleted) {
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
    }

    public List<T> getCreated() {
        return created;
    }

    public List<T> getUpdated() {
        return updated;
    }

    public List<T> getDeleted() {
        return deleted;
    }


    private static <T extends Keyed> Map<Object, T> toMap(List<T> previous) {

        Map<Object, T> mappedPrevious = new HashMap<>();

        for (T keyed : previous) {
            Object key = keyed.uniqueKey();
            if (key != null) {
                mappedPrevious.put(key, keyed);
            }
        }

        return mappedPrevious;

    }

    public static <T extends Keyed> CUD<T> fromLists(List<T> previous, List<T> current) {
        List<Keyed> created = new ArrayList<>();
        List<Keyed> updated = new ArrayList<>();
        List<Keyed> deleted = new ArrayList<>();

        Map<Object, T> mappedPrevious = toMap(previous);
        Map<Object, T> mappedCurrent = toMap(current);
        List<T> currentNoKey = new ArrayList<>();
        for (T t : current) {
            if (t.uniqueKey() == null) {
                currentNoKey.add(t);
            }
        }


        Set<Object> keys = new HashSet<>();
        keys.addAll(mappedPrevious.keySet());
        keys.addAll(mappedCurrent.keySet());

        for (Object key : keys) {
            Keyed p = mappedPrevious.get(key);
            Keyed c = mappedCurrent.get(key);

            if (p == null) {
                created.add(c);
            } else {
                if (c == null) {
                    deleted.add(p);
                } else {
                    if (!p.equals(c)) {
                        updated.add(c);
                    }
                }
            }
        }

        created.addAll(currentNoKey);

        return new CUD<T>((List<T>) created, (List<T>) updated, (List<T>) deleted);
    }
}

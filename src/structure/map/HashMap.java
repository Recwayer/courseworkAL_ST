package structure.map;

import structure.list.LinkedList;

import java.util.Iterator;

public class HashMap<K,V> implements Map<K,V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private LinkedList<Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        buckets = new LinkedList[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new LinkedList<>();
        }
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        bucket.add(new Entry<>(key, value));
        size++;

        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getValue().equals(value)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void remove(K key) {
        int index = getIndex(key);
        LinkedList<Entry<K, V>> bucket = buckets[index];

        Iterator<Entry<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                size--;
                return;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterable<V> values() {
        return () -> new Iterator<>() {
            int bucketIndex = 0;
            Iterator<Entry<K, V>> entryIterator = buckets[0].iterator();

            @Override
            public boolean hasNext() {
                while ((bucketIndex < buckets.length) && !entryIterator.hasNext()) {
                    bucketIndex++;
                    if (bucketIndex < buckets.length) {
                        entryIterator = buckets[bucketIndex].iterator();
                    }
                }
                return entryIterator.hasNext();
            }

            @Override
            public V next() {
                if (!hasNext()) {
                    return null;
                }
                return entryIterator.next().getValue();
            }
        };
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        LinkedList<Entry<K, V>>[] newBuckets = new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                int newIndex = Math.abs(entry.getKey().hashCode()) % newCapacity;
                newBuckets[newIndex].add(entry);
            }
        }

        buckets = newBuckets;
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}

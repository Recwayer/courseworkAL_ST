package structure.map;

public interface Map<K,V> {
    void put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    boolean containsValue(V value);

    void remove(K key);

    int size();

    boolean isEmpty();

    Iterable<V> values();

}

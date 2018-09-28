package com.lifestorm.learn.datastructure.map;

import java.util.Collection;
import java.util.Set;

/**
 * Created by life_storm on 2018/9/26.
 */
public interface Map<K,V> {

    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    void putAll(Map<? extends K, ? extends  V > m);

    void clear();

    //views
    Set<K> keySet();

    Collection<V> values();

    Set<Map.Entry<K,V>> entrySet();

    interface Entry<K,V>{
        K getKey();

        V getValue();

        V setValue(V value);

        boolean equals(Object o);

        int hashCode();

    }

    boolean equals(Object o);

    int hashCode();

}

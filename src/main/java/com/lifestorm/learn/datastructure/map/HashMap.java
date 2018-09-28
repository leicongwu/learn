package com.lifestorm.learn.datastructure.map;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by life_storm on 2018/9/28.
 */
public class HashMap<K,V>
        extends AbstractMap<K,V>
        implements Map<K,V>,Cloneable ,Serializable {

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }


}

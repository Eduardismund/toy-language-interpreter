package model.adt;

import exceptions.ADTException;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary<K, V> {
    void put(K key, V value) throws ADTException;

    void remove(K key) throws ADTException;

    boolean isDefined(K key);

    V lookup(K key) throws ADTException;

    void update(K key, V value) throws ADTException;

    Map<K, V> getMap();

    void setMap(Map<K, V> map) throws ADTException;

    Collection<V> values();

    MyIDictionary<K, V> deepCopy() throws ADTException;
}

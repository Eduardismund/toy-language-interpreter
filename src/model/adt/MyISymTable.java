package model.adt;

import exceptions.ADTException;

public interface MyISymTable<K, V> {
    void put(K key, V value) throws ADTException;
    void remove(K key) throws ADTException;
    boolean isDefined(K key);
    V lookup(K key) throws ADTException;
    void update(K key, V value) throws ADTException;
}

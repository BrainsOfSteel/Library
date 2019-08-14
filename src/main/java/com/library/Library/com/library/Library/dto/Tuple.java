package com.library.Library.com.library.Library.dto;

public class Tuple<K, V>{
    private K v1;
    private V v2;


    public Tuple(K v1, V v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public K getV1() {
        return v1;
    }

    public void setV1(K v1) {
        this.v1 = v1;
    }

    public V getV2() {
        return v2;
    }

    public void setV2(V v2) {
        this.v2 = v2;
    }
}

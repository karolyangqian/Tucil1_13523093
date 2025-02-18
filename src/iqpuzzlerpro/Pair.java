package iqpuzzlerpro;

import java.util.Objects;

class Pair<K, V> {
    public final K key;
    public final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?> p) {
            return this.key.equals(p.key) && this.value.equals(p.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}

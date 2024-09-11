package com.person98.craftessence.util.collections;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, X> {

    void accept(T t, U u, X x);


    default TriConsumer<T, U, X> andThen(TriConsumer<? super T, ? super U, ? super X> after) {
        Objects.requireNonNull(after);

        return (l, r, x) -> {
            accept(l, r, x);
            after.accept(l, r, x);
        };
    }
}

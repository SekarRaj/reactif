package com.essayer.reactif;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Function;

public class Util<T, U> {
    public Flux<U> mappedFluxFromList(final List<T> list, final Function<T, U> f) {
        return Flux.fromStream(list.stream().map(f));
    }

    public Flux<U> listToFluxThenMap(final List<T> list, final Function<T, U> f) {
        return Flux.fromIterable(list).map(f);
    }
}

record Customer(String fName, String lName) {
}

record EnrichedCustomer(String fullName) {
}


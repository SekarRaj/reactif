package com.essayer.reactif;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@ExtendWith(SpringExtension.class)
class UtilTest {
    private Util<Customer, EnrichedCustomer> util = new Util<>();

    private final Function<Customer, EnrichedCustomer> enrichedCustomerFunction =
            (c) -> new EnrichedCustomer(String.format("%s, %s", c.lName(), c.fName()));


    @Test
    @DisplayName(("Create Flux from mapped list with elements"))
    void canCreateFluxFromList_fluxHasElements() {
        var customers = List.of(
                new Customer("John", "Doe"),
                new Customer("Jane", "Doe")
        );

        Flux<EnrichedCustomer> ecs = util.mappedFluxFromList(customers, enrichedCustomerFunction);

        StepVerifier.create(ecs.hasElements()).expectNext(true).verifyComplete();
    }

    @Test
    @DisplayName(("Create Flux from mapped list with elements with valid elements"))
    void canCreateFluxFromList_firstElementMatches() {
        var customers = List.of(
                new Customer("John", "Doe"),
                new Customer("Jane", "Doe")
        );

        StepVerifier.create(util.mappedFluxFromList(customers, enrichedCustomerFunction).next().map(EnrichedCustomer::fullName))
                .expectNext(customers.get(0).lName() + ", " + customers.get(0).fName())
                .verifyComplete();
    }

    @Test
    @DisplayName(("Create Flux from mapped list with with empty list"))
    void canCreateFluxFromList_onEmptyList() {
        List<Customer> customers = Collections.emptyList();

        Flux<EnrichedCustomer> ecs = util.mappedFluxFromList(customers, enrichedCustomerFunction);

        StepVerifier.create(ecs.hasElements() )
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    @DisplayName(("Create Flux from mapped list with elements"))
    void listToFluxThenMap_fluxHasElements() {
        var customers = List.of(
                new Customer("John", "Doe"),
                new Customer("Jane", "Doe")
        );

        Flux<EnrichedCustomer> ecs = util.listToFluxThenMap(customers, enrichedCustomerFunction);

        StepVerifier.create(ecs.hasElements()).expectNext(true).verifyComplete();
    }

    @Test
    @DisplayName(("Create Flux from mapped list with elements with valid elements"))
    void listToFluxThenMap_firstElementMatches() {
        var customers = List.of(
                new Customer("John", "Doe"),
                new Customer("Jane", "Doe")
        );

        StepVerifier.create(util.listToFluxThenMap(customers, enrichedCustomerFunction).next().map(EnrichedCustomer::fullName))
                .expectNext(customers.get(0).lName() + ", " + customers.get(0).fName())
                .verifyComplete();
    }

    @Test
    @DisplayName(("Create Flux from mapped list with with empty list"))
    void listToFluxThenMap_onEmptyList() {
        List<Customer> customers = Collections.emptyList();

        Flux<EnrichedCustomer> ecs = util.listToFluxThenMap(customers, enrichedCustomerFunction);

        StepVerifier.create(ecs.hasElements() )
                .expectNext(true)
                .verifyComplete();
    }
}
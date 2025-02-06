package com.ecommerce.orders.ecommerceordersystem.util;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.transaction.ReactiveTransactionManager;

@Component
@RequiredArgsConstructor
public class TransactionUtil {
    private final ReactiveTransactionManager transactionManager;
    private final DatabaseClient databaseClient;

    public <T> Mono<T> executeTransaction(Mono<T> mono) {
        return Mono.usingWhen(
                transactionManager.getReactiveTransaction(null),
                transaction -> mono,
                transactionManager::commit,
                (transaction, ex) -> transactionManager.rollback(transaction),
                transactionManager::rollback
        );
    }
}
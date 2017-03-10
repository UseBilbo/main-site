package com.usebilbo.vertx.cluster.api;

import org.apache.ignite.transactions.Transaction;

public interface TransactionManager {
    <T> T doInTransaction(UnitOfWork<T> wu);
    
    public static interface UnitOfWork<T> {
        T run(Transaction transaction) throws Exception;
    }
}

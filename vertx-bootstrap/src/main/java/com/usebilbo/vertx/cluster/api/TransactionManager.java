package com.usebilbo.vertx.cluster.api;

import org.apache.ignite.transactions.Transaction;

import com.google.inject.ImplementedBy;
import com.usebilbo.vertx.cluster.api.impl.TransactionManagerImpl;

@ImplementedBy(TransactionManagerImpl.class)
public interface TransactionManager {
    <T> T doInTransaction(UnitOfWork<T> wu);
    
    public static interface UnitOfWork<T> {
        T run(Transaction transaction) throws Exception;
    }
}

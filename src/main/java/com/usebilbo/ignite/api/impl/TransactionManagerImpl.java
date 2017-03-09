package com.usebilbo.ignite.api.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.transactions.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.ignite.api.IgniteProvider;
import com.usebilbo.ignite.api.TransactionManager;

@Singleton
public class TransactionManagerImpl implements TransactionManager {
    private static final Logger LOG = LogManager.getLogger();
    
    private final IgniteProvider provider;

    @Inject
    public TransactionManagerImpl(IgniteProvider provider) {
        this.provider = provider;
    }

    @Override
    public <T> T doInTransaction(UnitOfWork<T> uw) {
        try(Transaction transaction = provider.get().transactions().txStart()) {
            T result = uw.run(transaction);
            transaction.commit();
            return result;
        } catch (Exception e) {
            LOG.warn("Error during transaction. Rolled back.", e);
        }
        return null;
    }
}

package com.usebilbo.vertx.cluster.api.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ignite.Ignite;
import org.apache.ignite.transactions.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.cluster.api.TransactionManager;

@Singleton
public class TransactionManagerImpl implements TransactionManager {
    private static final Logger LOG = LogManager.getLogger();
    
    private final Provider<Ignite> provider;

    @Inject
    public TransactionManagerImpl(Provider<Ignite> provider) {
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

package com.store.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityTransaction;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "storePU";
    private static EntityManagerFactory emf;

    // Initialisation de l'EntityManagerFactory
    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de l'EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtient un EntityManager
     */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory n'est pas initialisé");
        }
        return emf.createEntityManager();
    }

    /**
     * Ferme l'EntityManagerFactory (à appeler à la fermeture de l'application)
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    /**
     * Exécute une opération dans une transaction
     */
    public static void executeInTransaction(TransactionOperation operation) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            operation.execute(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de l'exécution de la transaction: " + e.getMessage(), e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     * Exécute une opération avec retour dans une transaction
     */
    public static <T> T executeInTransactionWithResult(TransactionOperationWithResult<T> operation) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            T result = operation.execute(em);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de l'exécution de la transaction: " + e.getMessage(), e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    @FunctionalInterface
    public interface TransactionOperation {
        void execute(EntityManager em);
    }

    @FunctionalInterface
    public interface TransactionOperationWithResult<T> {
        T execute(EntityManager em);
    }

    public static boolean isInitialized() {
        return emf != null;
    }


    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
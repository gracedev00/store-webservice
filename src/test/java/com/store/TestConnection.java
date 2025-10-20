package com.store;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConnection {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("storePU");
            EntityManager em = emf.createEntityManager();

            System.out.println("✅ Connexion à PostgreSQL réussie !");

            em.close();
            emf.close();
        } catch (Exception e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
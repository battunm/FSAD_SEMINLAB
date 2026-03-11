package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        insertInvoice(factory);
        viewAllInvoices(factory);
        viewWithParameters(factory, "Paid");

        factory.close();
    }
    public static void insertInvoice(SessionFactory factory) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Invoice inv = new Invoice();
        inv.setName("John Doe");
        inv.setInvoiceDate(new Date());
        inv.setStatus("Paid");
        inv.setAmount(1500.50);

        session.persist(inv);
        tx.commit();
        System.out.println("Invoice saved successfully!");
        session.close();
    }
  
    public static void viewAllInvoices(SessionFactory factory) {
        Session session = factory.openSession();
       
        Query<Invoice> query = session.createQuery("from Invoice", Invoice.class);
        List<Invoice> list = query.getResultList();

        System.out.println("--- All Invoices ---");
        for (Invoice inv : list) {
            System.out.println(inv.getId() + " | " + inv.getName() + " | " + inv.getStatus());
        }
        session.close();
    }
    public static void viewWithParameters(SessionFactory factory, String statusParam) {
        Session session = factory.openSession();
        
        String hql = "from Invoice i where i.status = ?1";
        Query<Invoice> query = session.createQuery(hql, Invoice.class);
        query.setParameter(1, statusParam); 

        List<Invoice> list = query.getResultList();
        System.out.println("--- Invoices with status: " + statusParam + " ---");
        for (Invoice inv : list) {
            System.out.println(inv.getName() + " - " + inv.getAmount());
        }
        session.close();
    }
}

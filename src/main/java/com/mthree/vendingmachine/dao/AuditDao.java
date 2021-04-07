package com.mthree.vendingmachine.dao;

public interface AuditDao {

    void writeAuditEntry(String entry) throws VendingException;

}

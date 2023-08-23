package com.SK.Library.Management.System.Repositories;

import com.SK.Library.Management.System.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import com.SK.Library.Management.System.Enums.*;
import com.SK.Library.Management.System.Models.*;
import java.util.*;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionsByBookAndLibraryCardAndTransactionStatusAndTransactionType(Book book, LibraryCard card, TransactionStatus transactionStatus, TransactionType transactionType);
    List<Transaction> findTransactionByTransactionStatusAndTransactionType(TransactionStatus transactionStatus, TransactionType transactionType);

}

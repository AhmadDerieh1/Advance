package edu.najah.cap.data;

import java.util.ArrayList;
import java.util.List;

import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.Transaction;

public class DeleteTransactions implements Deletion {
    private IPayment paymentService; 
    public DeleteTransactions(IPayment paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void removeData(String userName) {
       try {
            List<Transaction> transactions = paymentService.getTransactions(userName);
            for (Transaction transaction : new ArrayList<>(transactions)) {
                paymentService.removeTransaction(userName, transaction.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // معالجة الاستثناءات المحتملة
        }
    }
    }

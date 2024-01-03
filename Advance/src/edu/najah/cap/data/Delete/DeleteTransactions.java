package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.Transaction;

public class DeleteTransactions implements Deletion {
    private static final Logger logger = Logger.getLogger(DeleteTransactions.class.getName());
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
            logger.info("Successfully removed transactions for user: " + userName);
        } catch (Exception e) {
            // Handle any exceptions that may occur
            logger.warning("Exception occurred while removing transactions for user: " + userName);
            logger.warning(e.getMessage());
        }
    }
}

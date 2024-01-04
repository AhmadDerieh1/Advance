package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.Transaction;

public class DeleteTransactions implements Deletion {
    private static final Logger logger = LoggerSetup.getLogger(); 
    private IPayment paymentService;

    public DeleteTransactions(IPayment paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public boolean removeData(String userName, MergeObject mergeObject) {
        boolean isRemovedSuccessfully = false;
        try {
            List<Transaction> transactions = paymentService.getTransactions(userName);
            for (Transaction transaction : new ArrayList<>(transactions)) {
                paymentService.removeTransaction(userName, transaction.getId());
                mergeObject.getTransactions().remove(transaction);

            }
            mergeObject.setTransactions(new ArrayList<>()); // Clear transactions after successful removal
            logger.info("Successfully removed transactions for user: " + userName);
            isRemovedSuccessfully = true;
        } catch (Exception e) {
            logger.warning("Exception occurred while removing transactions for user: " + userName);
            logger.warning(e.getMessage());
        }
        return isRemovedSuccessfully;
    }
    
}

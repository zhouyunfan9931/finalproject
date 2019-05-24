package pkgUT;

import app.LoanResolver;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCalculate {
    @Test
    public void testCalculate(){
        double la=1111;
        double ir=11;
        int toy=11;
        LocalDate firsttime= LocalDate.of(2019,5,1);
        double additionalPayment=1;

       double expectedTotalPayment=11027.48;
       double expectedTotalInterest=8899.98;
        LoanResolver lr = new LoanResolver();
        ObservableList<LoanResolver.PaymentItem> data
                = lr.CalculatePayment(la,ir,toy,additionalPayment,firsttime);

        assertTrue(lr.getTotalPayments()==11027.48);
        assertTrue(lr.getTotalInterests()==8899.98);
    }
}

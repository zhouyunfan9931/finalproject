package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.time.LocalDate;

public class LoanResolver {
    private final ObservableList<PaymentItem> data = FXCollections.observableArrayList();
    private double totalP;
    private double totalI;

    public static class PaymentItem {
        private final SimpleStringProperty paymentNumber;
        private final SimpleStringProperty date;
        private final SimpleStringProperty payment;
        private final SimpleStringProperty additionalPayment;
        private final SimpleStringProperty interest;
        private final SimpleStringProperty principle;
        private final SimpleStringProperty balance;

        public PaymentItem(String paymentNumber, String date, String payment, String additionalPayment, String interest, String principle, String balance) {
            this.paymentNumber = new SimpleStringProperty(paymentNumber);
            this.date = new SimpleStringProperty(date);
            this.payment = new SimpleStringProperty(payment);
            this.additionalPayment = new SimpleStringProperty(additionalPayment);
            this.interest = new SimpleStringProperty(interest);
            this.principle = new SimpleStringProperty(principle);
            this.balance = new SimpleStringProperty(balance);
        }

        public String getPaymentNumber() {
            return this.paymentNumber.get();
        }

        public void setPaymentNumber(String paymentNumber) {
            this.paymentNumber.set(paymentNumber);
        }

        public String getDate() {
            return this.date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getPayment() {
            return this.payment.get();
        }

        public void setPayment(String payment) {
            this.payment.set(payment);
        }

        public String getAdditionalPayment() {
            return this.additionalPayment.get();
        }

        public void setAdditionalPayment(String additionalPayment) {
            this.additionalPayment.set(additionalPayment);
        }

        public String getInterest() {
            return this.interest.get();
        }

        public void setInterest(String interest) {
            this.interest.set(interest);
        }

        public String getPrinciple() {
            return this.principle.get();
        }

        public void setPrinciple(String principle) {
            this.principle.set(principle);
        }

        public String getBalance() {
            return this.balance.get();
        }

        public void setBalance(String balance) {
            this.balance.set(balance);
        }
    }
    public static double secondTime(double value) {
    	double answer = (double)Math.round(value*100);
    	answer = answer / 100;
        return answer;
    }
    
    public double getTotalPayments() {
        totalP=0;
        int time = data.size()-1;
        for(int i=0;i<time;i++){
            totalP+=Double.parseDouble(data.get(i+1).getPayment());
        }
        totalP=secondTime(totalP);
        return totalP;
    }

    public double getTotalInterests() {
        totalI=0;
        int time = data.size()-1;
        for(int i=1;i<time;i++){
            totalI+=Double.parseDouble(data.get(i+1).getInterest());
        }
        totalI=secondTime(totalI);
        return totalI;
    }

    public ObservableList<PaymentItem> CalculatePayment(double loan,double noy, double iRate,  double additionalPayment, LocalDate date){
        double balance=secondTime(loan-additionalPayment);
        data.add(new PaymentItem(null,null,null,null,null,null,String.format("%.2f",balance)));
        double ratepermonth = iRate/12.00;
		double PMT = secondTime(Math.abs(FinanceLib.pmt(ratepermonth, noy*12.00, loan, 0, false)));
        int order=0;
        while(balance>0){
            double interest=secondTime(balance*ratepermonth);
            double principal;
            double payment;
            if(secondTime(PMT-(balance+interest))>=-0.01){
                principal=balance;
                payment=secondTime(balance+interest);
                balance=0;
            }else{
                principal=secondTime(PMT-interest);
                payment=PMT;
                balance=secondTime(balance-principal);
            }
            order = order + 1;
            data.add(
                    new PaymentItem(Integer.toString(order),date.toString(),String.format("%.2f",payment),null,String.format("%.2f",interest),String.format("%.2f",principal),String.format("%.2f",balance)));
            date=date.plusMonths(1);
        }
        return data;
    }

}

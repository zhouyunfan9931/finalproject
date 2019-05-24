package app.controller;

import app.LoanResolver;
import app.StudentCalc;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	@FXML
	private TextField LoanAmount;

	@FXML
	private TextField InterestRate;

	@FXML
	private TextField NbrOfYears;

	@FXML
	private DatePicker PaymentStartDate;

	@FXML
	private TextField AdditionalPayment;

	@FXML
	private Label lblTotalPayemnts;

	@FXML
	private Label lblTotalInterests;

	@FXML
	private TableView PaymentScheduleList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		double dla = Double.parseDouble(LoanAmount.getText());
		double dir;
		double dnoy = Double.parseDouble(NbrOfYears.getText());
		double dap = Double.parseDouble(AdditionalPayment.getText());
		for(int i=0;i<PaymentScheduleList.getColumns().size();++i){
			TableColumn col=(TableColumn)PaymentScheduleList.getColumns().get(i);
			if(col.getText().equals("Payment num")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("paymentNumber"));
			}else if(col.getText().equals("Due Date")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("date"));
			}else if(col.getText().equals("Payment")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("payment"));
			}else if(col.getText().equals("Add Payment")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("additionalPayment"));
			}else if(col.getText().equals("Interest")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("interest"));
			}else if(col.getText().equals("Principle")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("principle"));
			}else if(col.getText().equals("Balance")){
				col.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("balance"));
			}
		}


		if(InterestRate.getText().endsWith("%")){
			dir=Double.parseDouble(InterestRate.getText().replace("%",""))*0.01;
		}else{
			dir=Double.parseDouble(InterestRate.getText());
		}

		LocalDate date=PaymentStartDate.getValue();
		LoanResolver lr = new LoanResolver();
		ObservableList<LoanResolver.PaymentItem> data
				= lr.CalculatePayment(dla,dir,dnoy,dap,date);
		PaymentScheduleList.setItems(data);
		lblTotalPayemnts.setText(Double.toString(lr.getTotalPayments()));
		lblTotalInterests.setText(Double.toString(lr.getTotalInterests()));
	}
}

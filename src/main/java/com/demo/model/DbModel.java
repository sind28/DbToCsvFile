package com.demo.model;

public class DbModel {
	private int employee_id;
	private String employee_name;
	private String employee_relatives;
	private int transaction_id;

	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmployee_relatives() {
		return employee_relatives;
	}
	public void setEmployee_relatives(String employee_relatives) {
		this.employee_relatives = employee_relatives;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	@Override
	public String toString() {
		return "Employee [employee_id=" + employee_id + ", employee_name=" + employee_name + ", employee_relatives="
				+ employee_relatives + ", transaction_id=" + transaction_id + "]";
	}
		
	}

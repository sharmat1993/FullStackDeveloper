package com.cts;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * This POJO class all the details of the Record Statement.
 * @author Sharma
 *
 */
@XmlType(propOrder = {"accountNumber", "description", "startBalance", "mutation", "endBalance"})
public class Record {

	private long reference;

	private String accountNumber;

	private String description;

	private double startBalance;

	private double mutation;

	private double endBalance;

	@XmlAttribute
	public long getReference() {
		return reference;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the startBalance
	 */
	public double getStartBalance() {
		return startBalance;
	}

	/**
	 * @return the mutation
	 */
	public double getMutation() {
		return mutation;
	}

	/**
	 * @return the endBalance
	 */
	public double getEndBalance() {
		return endBalance;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(long reference) {
		this.reference = reference;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param startBalance the startBalance to set
	 */
	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	/**
	 * @param mutation the mutation to set
	 */
	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	/**
	 * @param endBalance the endBalance to set
	 */
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}



}

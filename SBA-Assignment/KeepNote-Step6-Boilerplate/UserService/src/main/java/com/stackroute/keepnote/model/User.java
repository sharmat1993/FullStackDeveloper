package com.stackroute.keepnote.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * Please note that this class is annotated with @Document annotation
 * @Document identifies a domain object to be persisted to MongoDB.
 *  */
@Document
public class User {
	/*
	 * This class should have five fields (userId,userName,
	 * userPassword,userMobile,userAddedDate). Out of these five fields, the field
	 * userId should be annotated with @Id (This annotation explicitly specifies the
	 * document identifier). This class should also contain the getters and setters
	 * for the fields, along with the no-arg , parameterized constructor and
	 * toString method.The value of userAddedDate should not be accepted from the
	 * user but should be always initialized with the system date.
	 */
	@Id
	private String userId;
	private String userName;
	private Date userAddedDate;
	private String userPassword;
	private String userMobile;

	public User() {
	}

	public User(String string, String string1, String string2, String string3, Date date) {
		this.userId = string;
		this.userName = string1;
		this.userPassword = string2;
		this.userMobile = string3;
		this.userAddedDate = date;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String string) {
		this.userId = string;
	}

	public void setUserName(String string) {
		this.userName = string;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String string) {
		this.userPassword = string;
	}

	public String getUserMobile() {
		return this.userMobile;
	}

	public void setUserMobile(String string) {
		this.userMobile = string;
	}

	public void setUserAddedDate(Date date) {
		this.userAddedDate = date;
	}

	public String getUserName() {
		return userName;
	}

	public Date getUserAddedDate() {
		return userAddedDate;
	}
}
package com.stackroute.keepnote.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/*
 * The class "User" will be acting as the data model for the User Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Table(name = "User")
public class User {

    /*
	 * This class should have five fields (userId,firstName,lastName,
	 * userPassword,userRole,userAddedDate). Out of these five fields, the field
	 * userId should be the primary key. This class should also contain the getters
	 * and setters for the fields, along with the no-arg , parameterized constructor
	 * and toString method.The value of userAddedDate should not be accepted from
	 * the user but should be always initialized with the system date
	 */

	@Id
	@Column(name = "user_id")
    private String userId;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "user_password")
    private String userPassword;
	
	@Column(name = "user_added_date")
	@CreationTimestamp
	private Date userAddedDate;
	
	@Column(name = "user_role")
    private String userRole;
   
	
    public String getUserId() {
    	return this.userId;
    }

    public void setUserId(String  string) {
    	this.userId = string;
    }

    public String getFirstName() {
    	return this.firstName;
    }

    public void setFirstName(String  string) {
    	this.firstName = string; 
    }

    public String getLastName() {
    	return this.lastname;
    }

    public void setLastName(String  string) {
    	this.lastname = string;
    }

    public String getUserPassword() {
    	return this.userPassword;
    }

    public void setUserPassword(String  string) {
    	this.userPassword = string;
    }

    public String getUserRole() {
    	return this.userRole;
    }

    public void setUserRole(String  string) {
    	this.userRole = string;
    }


    public Date getUserAddedDate() {
        return this.userAddedDate;
    }

    public void setUserAddedDate(Date date) {
    	this.userAddedDate = date;
    }

    


}

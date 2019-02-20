package com.stackroute.keepnote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.ReminderService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */

@RestController
public class ReminderController {
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement five functionalities regarding reminder. They are as
	 * following:
	 * 
	 * 1. Create a reminder 2. Delete a reminder 3. Update a reminder 4. Get all
	 * reminders by userId 5. Get a specific reminder by id.
	 * 
	 */
	/*
	 * Autowiring should be implemented for the ReminderService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	ReminderService reminderService;

	@Autowired
	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	/*
	 * Define a handler method which will create a reminder by reading the
	 * Serialized reminder object from request body and save the reminder in
	 * database. Please note that the reminderId has to be unique. This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 201(CREATED - In case of successful creation of the reminder
	 * 2. 409(CONFLICT) - In case of duplicate reminder ID
	 *
	 * This handler method should map to the URL "/api/v1/reminder" using HTTP POST
	 * method".
	 */
	@PostMapping(value = "/api/v1/reminder", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createReminder(@RequestBody Reminder reminder) {
		try {
			reminderService.createReminder(reminder);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (ReminderNotCreatedException e) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will delete a reminder from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the reminder deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the reminder with specified reminderId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid reminderId without {}
	 */
	@DeleteMapping(value = "/api/v1/reminder/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteReminder(@PathVariable(name = "id") String id) {
		try {
			reminderService.deleteReminder(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (ReminderNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will update a specific reminder by reading the
	 * Serialized object from request body and save the updated reminder details in
	 * a database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the reminder updated
	 * successfully. 2. 404(NOT FOUND) - If the reminder with specified reminderId
	 * is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP
	 * PUT method.
	 */
	@PutMapping(value = "/api/v1/reminder/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateReminder(@RequestBody Reminder reminder, @PathVariable(name = "id") String id) {

		try {
			if (reminderService.updateReminder(reminder, id) != null) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		} catch (ReminderNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will show details of a specific reminder. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the reminder found successfully. 2.
	 * 404(NOT FOUND) - If the reminder with specified reminderId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder/{id}" using HTTP
	 * GET method where "id" should be replaced by a valid reminderId without {}
	 */
	@GetMapping(value = "/api/v1/reminder/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> getReminder(@PathVariable(name = "id") String id) {
		try {
			if (reminderService.getReminderById(id) != null) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		} catch (ReminderNotFoundException e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will get us the all reminders. This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 200(OK) - If the reminder found successfully. 2. 404(NOT
	 * FOUND) - If the reminder with specified reminderId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/reminder" using HTTP GET
	 * method
	 */
	@GetMapping(value = "/api/v1/reminder", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> getAllReminders() {
		if (reminderService.getAllReminders() != null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

	}
}
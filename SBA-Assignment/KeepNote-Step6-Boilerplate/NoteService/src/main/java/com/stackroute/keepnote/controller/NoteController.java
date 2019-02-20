package com.stackroute.keepnote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@CrossOrigin
public class NoteController {
	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	NoteService noteService;

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the note created
	 * successfully. 2. 409(CONFLICT) - If the noteId conflicts with any existing
	 * user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST
	 * method
	 */
	@PostMapping(value = "/api/v1/note", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createNote(@RequestBody Note note) {
		if (noteService.createNote(note)) {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will delete a note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid noteId without {}
	 */
	@DeleteMapping(value = "/api/v1/note/{userId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteNote(@PathVariable(name = "userId") String userId,
			@PathVariable(name = "id") int id) {
		try {
			if (noteService.deleteNote(userId, id)) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/api/v1/note/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAllNotes(@PathVariable(name = "userId") String userId) {
		try {
			if (noteService.deleteAllNotes(userId)) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT
	 * method.
	 */
	@PutMapping(value = "/api/v1/note/{userId}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Note> updateNote(@RequestBody Note note, @PathVariable(name = "userId") String userId,
			@PathVariable(name = "id") int id) {
		Note updateNote = null;
		try {
			updateNote = noteService.updateNote(note, id, userId);
			if (updateNote != null) {
				return new ResponseEntity<Note>(updateNote, HttpStatus.OK);
			} else {
				return new ResponseEntity<Note>(updateNote, HttpStatus.NOT_FOUND);
			}
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<Note>(updateNote, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will get us the all notes by a userId. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET
	 * method
	 */
	@GetMapping(value = "/api/v1/note/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Note>> getAllNotes(@PathVariable(name = "userId") String userId) {
		List<Note> notes = null;
		try {
			notes = noteService.getAllNoteByUserId(userId);
			return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<List<Note>>(notes, HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@GetMapping(value = "/api/v1/note/{userId}/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Note> getNote(@PathVariable(name = "userId") String userId,
			@PathVariable(name = "noteId") int noteId) {
		Note note = null;
		try {
			note = noteService.getNoteByNoteId(userId, noteId);
			if (note != null) {
				return new ResponseEntity<Note>(note, HttpStatus.OK);
			} else {
				return new ResponseEntity<Note>(note, HttpStatus.NOT_FOUND);
			}
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<Note>(note, HttpStatus.NOT_FOUND);
		}
	}
}
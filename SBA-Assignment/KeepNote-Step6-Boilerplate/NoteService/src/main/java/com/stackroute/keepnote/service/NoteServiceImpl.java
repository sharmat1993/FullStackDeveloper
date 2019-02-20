package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class NoteServiceImpl implements NoteService {
	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	NoteRepository noteRepository;

	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		Optional<NoteUser> noteUser = noteRepository.findById("Jhon123");
		if (noteUser != null) {
			note.setNoteId(noteUser.get().getNotes().size()+1);
			noteUser.get().getNotes().add(note);
			if (noteRepository.save(noteUser.get()) != null) {
				return true;
			} else {
				return false;
			}
		} else {
			List<Note> notes = new ArrayList<Note>();
			notes.add(note);
			NoteUser myNoteUser = new NoteUser();
			myNoteUser.setUserId("Jhon123");
			myNoteUser.setNotes(notes);
			if (noteRepository.save(myNoteUser) != null) {
				return true;
			} else {
				return false;
			}
		}
	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(String userId, int noteId) {
		try {
			noteRepository.findById(userId).get();
			noteRepository.deleteById(String.valueOf(noteId));
			return true;
		} catch (Exception exception) {
			throw new NullPointerException();
		}
	}

	/* This method should be used to delete all notes with specific userId. */

	public boolean deleteAllNotes(String userId) {
		try {
			noteRepository.deleteById(userId);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int noteId, String userId) throws NoteNotFoundExeption {
		try {
			Optional<NoteUser> noteUser = noteRepository.findById(userId);
			for (Note updateNote : noteUser.get().getNotes()) {
				if (updateNote.getNoteId() == noteId) {
					updateNote.setNoteTitle(note.getNoteTitle());
					updateNote.setNoteContent(note.getNoteContent());
					updateNote.setNoteStatus(note.getNoteStatus());
					updateNote.setNoteCreationDate(note.getNoteCreationDate());
					updateNote.setNoteCreatedBy(note.getNoteCreatedBy());
					updateNote.setCategory(note.getCategory());
					updateNote.setReminders(note.getReminders());
				}
			}
			noteRepository.save(noteUser.get());
			return getNoteByNoteId(userId, noteId);
		} catch (Exception e) {
			throw new NoteNotFoundExeption("");
		}

		/*
		 * if( updatedNoteUser != null) { for(Note updatedNote :
		 * updatedNoteUser.getNotes()) { if(updatedNote.getNoteId() == id) { return
		 * updatedNote; } } } throw new NoteNotFoundExeption("");
		 */
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		try {
			Optional<NoteUser> noteUser = noteRepository.findById(userId);
			for (Note note : noteUser.get().getNotes()) {
				if (note.getNoteId() == noteId) {
					return note;
				}
			}
		} catch (Exception e) {
			throw new NoteNotFoundExeption("");
		}
		throw new NoteNotFoundExeption("");
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		return noteRepository.findById(userId).get().getNotes();
	}
}
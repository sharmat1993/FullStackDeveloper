import { Injectable } from '@angular/core';
import { Note } from '../note';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpClient} from '@angular/common/http';
import { AuthenticationService } from '../services/authentication.service';
import 'rxjs/add/operator/do';

@Injectable()
export class NotesService {

  notes: Note[];
  notesSubject: BehaviorSubject<Array<Note>>;
  getNotesUrl = 'http://localhost:8082/api/v1/note/Jhon123';
  addNoteUrl = 'http://localhost:8082/api/v1/note';

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
    this.notes = [];
    this.notesSubject = new BehaviorSubject<Array<Note>>(this.notes);
  }

  fetchNotesFromServer() {
    console.log('fetchNotesFromServer called');
    const token = this.authenticationService.getBearerToken();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      })
    };
    this.http.get<Array<Note>>(this.getNotesUrl, httpOptions).subscribe(
      response => {
        this.notes = response,
        this.notesSubject.next(this.notes);
        console.log('this.notes');
        console.log(this.notes);
      },
      err => {
        console.log(err);
      }
    );
  }

  getNotes(): BehaviorSubject<Array<Note>> {
    this.fetchNotesFromServer();
    return this.notesSubject;
  }

  addNote(note: Note): Observable<Note> {
    const token = this.authenticationService.getBearerToken();
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`
      })
    };
    return this.http.post<Note>(this.addNoteUrl, note, httpOptions)
      .do(noteResponse => {
        console.log('noteResponse');
        console.log(noteResponse);
          this.notes.push(note);
          this.notesSubject.next(this.notes);
          console.log('this.getNotes()');
          console.log(this.getNotes());
      });
  }

  editNote(note: Note): Observable<Note> {
    const token = this.authenticationService.getBearerToken();
    const httpOptions = {
      headers: new HttpHeaders({'Authorization': `Bearer ${token}` })
    };
    return this.http.put<Note>(`http://localhost:8082/api/v1/note/Jhon123/${note.noteId}`, note, httpOptions)
      .do(editNoteResponse => {
        const editNote = this.notes.find(findNote => findNote.noteId === editNoteResponse.noteId);
        Object.assign(editNote, editNoteResponse);
        this.notesSubject.next(this.notes);
      });
  }

  getNoteById(noteId): Note {
    return this.notes.find(findNote => findNote.noteId === noteId);
  }
}

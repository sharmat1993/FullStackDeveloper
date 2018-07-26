import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { Note } from './note';
import { HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
    'Authorization': 'my-auth-token'
  })
};

@Injectable()
export class NotesService {

  private notesUrl = 'http://localhost:3000/notes';

  constructor(private _http: HttpClient) {
  }

  getNotes(): Observable<Array<Note>> {
    return this._http.get<Array<Note>>(this.notesUrl).do(data => JSON.stringify(data));

  }

  addNote(note: Note): Observable<Note> {
    return this._http.post<Note>(this.notesUrl, JSON.stringify(note), httpOptions);
  }

}

import { Component, OnInit } from '@angular/core';
import { NotesService } from '../services/notes.service';
import { Note } from '../note';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent {

  errMessage: string;
  title: string;
  text: string;
  status: string;
  notes: Array<Note>;
  states: Array<string> = ['not-started', 'started', 'completed'];

  constructor(private notesService: NotesService ) {
    this.notesService.getNotes().subscribe(
      notesData => {
        this.notes = notesData;
        this.errMessage = '';
      },
      getNoteException => {
        this.errMessage = getNoteException.message;
      }
    );
  }

  addNote(): void {
    this.errMessage = '';
    if (this.title !== '' && this.text !== '' && this.title !== undefined && this.text !== undefined) {
      const note = new Note();
      note.noteTitle = this.title;
      note.noteContent = this.text;
      note.noteStatus = this.status;
      this.notesService.addNote(note).subscribe(
        saveNote => {
          this.notesService.getNotes().subscribe(
            notesData => {
              this.notes = notesData;
              this.errMessage = '';
            },
            getNoteException => {
              this.errMessage = getNoteException.message;
            }
          );
        },
        notesError => this.errMessage = notesError.message
      );
    }else {
      this.errMessage = 'Title and Text both are required fields';
    }
  }
}

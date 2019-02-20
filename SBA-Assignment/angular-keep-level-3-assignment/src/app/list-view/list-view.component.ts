import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from '../services/notes.service';

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.css']
})
export class ListViewComponent {

  notStartedNotes: Array<Note>;
  startedNotes: Array<Note>;
  completedNotes: Array<Note>;

  constructor (private notesService: NotesService) {
    this.notesService.getNotes().subscribe(
      notes => {
        this.notStartedNotes = [];
        this.startedNotes = [];
        this.completedNotes = [];
        notes.forEach(note => {
          if (note.noteStatus === 'not-started') {
            this.notStartedNotes.push(note);
          }
          if (note.noteStatus === 'started') {
            this.startedNotes.push(note);
          }
          if (note.noteStatus === 'completed') {
            this.completedNotes.push(note);
          }
        });
      },
      err => console.log(err)
    );
  }
}

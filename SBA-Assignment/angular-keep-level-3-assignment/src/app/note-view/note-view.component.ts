import { Component } from '@angular/core';
import { NotesService } from '../services/notes.service';
import { Note } from '../note';

@Component({
  selector: 'app-note-view',
  templateUrl: './note-view.component.html',
  styleUrls: ['./note-view.component.css']
})
export class NoteViewComponent {

  notes: Array<Note>;

  constructor(private notesService: NotesService ) {
    this.notesService.getNotes().subscribe(
      response => this.notes = response,
      err => console.log(err)
    );
  }
}

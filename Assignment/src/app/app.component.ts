import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from './header/header.component';
import { NotesService } from './notes.service';
import { Note } from './note';
import { error } from 'util';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [ NotesService ]
})

export class AppComponent implements OnInit {

  errMessage: string;
  notes: Array<Note>;
  title: string;
  text: string;

  constructor(private _notesService: NotesService ) {
  }

  ngOnInit(): void {
    this._notesService.getNotes().subscribe(notes => this.notes = notes);
  }

  addNote(): void {
    this.errMessage = '';
    if (this.title !== undefined || this.text !== undefined) {
      const note = new Note();
      note.title = this.title;
      note.text = this.text;
      this._notesService.addNote(note).subscribe(saveNote => this.notes.push(saveNote));
    }else {
      this.errMessage = 'Please provide all the inputs';
    }

  }

}

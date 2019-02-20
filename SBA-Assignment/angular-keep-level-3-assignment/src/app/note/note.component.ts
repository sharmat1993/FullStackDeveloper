import { Component, Input, SimpleChanges } from '@angular/core';
import { NotesService } from '../services/notes.service';
import { Note } from '../note';
import { RouterService } from '../services/router.service';
import { OnChanges } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})
export class NoteComponent implements OnChanges {

  @Input()
  note: Note;

  constructor(private routerService: RouterService) {
  }

  editNote() {
    this.routerService.routeToEditNoteView(this.note.noteId);
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('note');
    console.log(this.note);
  }

}

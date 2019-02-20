import { Category } from './category';
import { Reminder } from './reminder';

export class Note {

  noteId: Number;
  noteTitle: string;
  noteContent: string;
  noteStatus: string;
  category: Category;
  reminders: Array<Reminder>;

  constructor() {
    this.noteTitle = '';
    this.noteContent = '';
    this.noteStatus = '';
  }
}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HttpResponse} from '@angular/common/http';

import { MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { NoteComponent } from './note/note.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NoteTakerComponent } from './note-taker/note-taker.component';
import { NoteViewComponent } from './note-view/note-view.component';
import { ListViewComponent } from './list-view/list-view.component';
import { EditNoteOpenerComponent } from './edit-note-opener/edit-note-opener.component';
import { EditNoteViewComponent } from './edit-note-view/edit-note-view.component';

import { AuthenticationService } from './services/authentication.service';
import { RouterService } from './services/router.service';
import { NotesService } from './services/notes.service';
import { CanActivateRouteGuard } from './can-activate-route.guard';

const appRoutes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [CanActivateRouteGuard],
    children: [
      { path: 'view/noteview', component: NoteViewComponent },
      { path: 'view/listview', component: ListViewComponent },
      { path: 'note/:noteId/edit', component: EditNoteOpenerComponent, outlet: 'noteEditOutlet' },
      { path: '', redirectTo: 'view/noteview', pathMatch: 'full'}
    ]
  },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  declarations: [
    AppComponent, LoginComponent, DashboardComponent, NoteComponent, HeaderComponent,
    NoteTakerComponent, NoteViewComponent, ListViewComponent, EditNoteOpenerComponent,
    EditNoteViewComponent
   ],
  imports: [
    BrowserModule, ReactiveFormsModule, FormsModule, HttpClientModule, BrowserAnimationsModule,
    MatToolbarModule, MatFormFieldModule, MatSelectModule, MatCardModule, MatExpansionModule,
    MatInputModule, MatDialogModule, MatButtonModule, MatIconModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing : true }
    )
  ],
  providers: [ AuthenticationService, RouterService, NotesService, CanActivateRouteGuard ],
  bootstrap: [ AppComponent ],
  entryComponents: [ EditNoteViewComponent ]
})
export class AppModule {
}

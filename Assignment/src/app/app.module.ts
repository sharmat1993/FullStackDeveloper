import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import {MatInputModule} from '@angular/material/input';
import {MatToolbarModule} from '@angular/material/toolbar';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { HttpClient } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ AppComponent, HeaderComponent ],
  imports: [ BrowserModule, MatCardModule, MatExpansionModule, MatInputModule, MatToolbarModule,
             HttpModule, HttpClientModule, BrowserAnimationsModule, FormsModule ],
  providers: [ HttpClient ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }

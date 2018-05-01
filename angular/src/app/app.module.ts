import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CalendarModule } from 'angular-calendar';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import localeFr from '@angular/common/locales/fr';
import { registerLocaleData } from '@angular/common';

import { AppComponent } from './app.component';
import { LeftMenuComponent } from './left-menu/left-menu.component';
import { HomeworksComponent } from './homeworks/homeworks.component';
import { LandingKidsClassComponent } from './landing-kids-class/landing-kids-class.component';
import { AdminComponent } from './admin/admin.component';
import { KidsClassComponent } from './landing-kids-class/kids-class/kids-class.component';
import { CalendarComponent } from './homeworks/calendar/calendar.component';

registerLocaleData(localeFr);

@NgModule({
  declarations: [
    AppComponent,
    LandingKidsClassComponent,
    LeftMenuComponent,
    HomeworksComponent,
    AdminComponent,
    KidsClassComponent,
    CalendarComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    NgbModalModule.forRoot(),
    CalendarModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { LeftMenuComponent } from './left-menu/left-menu.component';
import { HomeworksComponent } from './homeworks/homeworks.component';
import { LandingKidsClassComponent } from './landing-kids-class/landing-kids-class.component';
import { AdminComponent } from './admin/admin.component';
import { KidsClassComponent } from './landing-kids-class/kids-class/kids-class.component';


@NgModule({
  declarations: [
    AppComponent,
    LandingKidsClassComponent,
    LeftMenuComponent,
    HomeworksComponent,
    AdminComponent,
    KidsClassComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

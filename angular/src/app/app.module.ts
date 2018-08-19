import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { AuthModule } from './auth/auth.module';
import { reducers } from './shared/app.reducers';
import { SchoolEffects } from './shared/schools/store/school.effects';
import { AuthEffects } from './auth/store/auth.effects';
import { SharedModule } from './shared/shared.module';
import { AdminModule } from './admin/admin.module';
import { UserAdminEffects } from './admin/user-admin/store/userAdmin.effects';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    AdminModule,
    SharedModule,
    BrowserModule,
    HttpClientModule,
    CoreModule,
    AuthModule,
    StoreModule.forRoot(reducers),
    EffectsModule.forRoot([SchoolEffects, AuthEffects, UserAdminEffects]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

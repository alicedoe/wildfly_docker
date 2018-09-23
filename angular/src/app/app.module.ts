import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { StoreModule, MetaReducer, ActionReducer } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { AuthModule } from './auth/auth.module';
import { SchoolEffects } from './shared/schools/store/school.effects';
import { AuthEffects } from './auth/store/auth.effects';
import { SharedModule } from './shared/shared.module';
import { AdminModule } from './admin/admin.module';
import { reducers } from './shared/app.reducers';
import { UserAdminEffects } from './admin/store/userAdmin.effects';

// console.log all actions
export function debug(reducer: ActionReducer<any>): ActionReducer<any> {
  return function(state, action) {
    console.log('state', state);
    console.log('action', action);

    return reducer(state, action);
  };
}

export const metaReducers: MetaReducer<any>[] = [debug];

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
    // StoreModule.forRoot(reducers),
    StoreModule.forRoot({}, {metaReducers}),
    EffectsModule.forRoot([SchoolEffects, AuthEffects, UserAdminEffects]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

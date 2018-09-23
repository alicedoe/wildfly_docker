import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SigninComponent } from './signin/signin.component';
import { AuthRoutingModule } from './auth-routing.module';
import { StoreModule } from '@ngrx/store';
import { reducers} from './store';

@NgModule({
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule,
    StoreModule.forFeature('auth', reducers)
  ],
  declarations: [SigninComponent]
})
export class AuthModule { }

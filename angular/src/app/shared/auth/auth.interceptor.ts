// import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
// import {Observable} from 'rxjs';
// import {Injectable} from '@angular/core';
// import {Store} from '@ngrx/store';
// import {take, switchMap} from 'rxjs/operators';

// import * as fromApp from '../../store/app.reducers';
// import * as fromAuth from '../../shared/auth/store/auth.reducers';

// @Injectable()
// export class AuthInterceptor implements HttpInterceptor {
//   constructor(private store: Store<fromApp.AppState>) {
//   }

//   intercept(req: HttpRequest<any>, next: HttpHandler) {
//     return this.store.select('auth')
//       .pipe(take(1),
//         switchMap((authState: fromAuth.State) => {
//           const copiedReq = req.clone({params: req.params.set('token', authState.token)});
//           return next.handle(copiedReq);
//         }));
//   }
// }
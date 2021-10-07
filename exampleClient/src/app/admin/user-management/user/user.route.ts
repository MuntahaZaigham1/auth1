
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { UserDetailsComponent, UserListComponent, UserNewComponent } from './';

const routes: Routes = [
	// { path: '', component: UserListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: UserDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: UserNewComponent, canActivate: [ AuthGuard ] },
];

export const userRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
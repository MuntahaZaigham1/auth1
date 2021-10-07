
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { UserpermissionDetailsComponent, UserpermissionListComponent, UserpermissionNewComponent } from './';

const routes: Routes = [
	// { path: '', component: UserpermissionListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: UserpermissionDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: UserpermissionNewComponent, canActivate: [ AuthGuard ] },
];

export const userpermissionRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);

import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { UserpermissionDetailsExtendedComponent, UserpermissionListExtendedComponent , UserpermissionNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: UserpermissionListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: UserpermissionDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: UserpermissionNewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const userpermissionRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);

import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { UserroleDetailsExtendedComponent, UserroleListExtendedComponent , UserroleNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: UserroleListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: UserroleDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: UserroleNewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const userroleRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
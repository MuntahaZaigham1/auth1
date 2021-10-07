
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { T4DetailsComponent, T4ListComponent, T4NewComponent } from './';

const routes: Routes = [
	// { path: '', component: T4ListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: T4DetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: T4NewComponent, canActivate: [ AuthGuard ] },
];

export const t4Route: ModuleWithProviders<any> = RouterModule.forChild(routes);
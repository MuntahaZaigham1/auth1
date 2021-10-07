
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from 'src/app/core/guards/auth.guard';


const routes: Routes = [
	
	{
		path: 'rolepermission',
		loadChildren: () => import('./user-management/rolepermission/rolepermission.module').then(m => m.RolepermissionExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'role',
		loadChildren: () => import('./user-management/role/role.module').then(m => m.RoleExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'userpermission',
		loadChildren: () => import('./user-management/userpermission/userpermission.module').then(m => m.UserpermissionExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'permission',
		loadChildren: () => import('./user-management/permission/permission.module').then(m => m.PermissionExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'userrole',
		loadChildren: () => import('./user-management/userrole/userrole.module').then(m => m.UserroleExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'user',
		loadChildren: () => import('./user-management/user/user.module').then(m => m.UserExtendedModule),
		canActivate: [AuthGuard]
	},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forChild(routes);
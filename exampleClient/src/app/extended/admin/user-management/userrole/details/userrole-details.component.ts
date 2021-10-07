import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { UserroleExtendedService } from '../userrole.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { RoleExtendedService } from 'src/app/extended/admin/user-management/role/role.service';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/user.service';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { UserroleDetailsComponent } from 'src/app/admin/user-management/userrole/index';

@Component({
  selector: 'app-userrole-details',
  templateUrl: './userrole-details.component.html',
  styleUrls: ['./userrole-details.component.scss']
})
export class UserroleDetailsExtendedComponent extends UserroleDetailsComponent implements OnInit {
	title:string='Userrole';
	parentUrl:string='userrole';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public userroleExtendedService: UserroleExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public roleExtendedService: RoleExtendedService,
		public userExtendedService: UserExtendedService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, userroleExtendedService, pickerDialogService, errorService,
		roleExtendedService,
		userExtendedService,
		globalPermissionService,
		);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IUser } from '../iuser';
import { UserService } from '../user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UserNewComponent } from '../new/user-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';


@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent extends BaseListComponent<IUser> implements OnInit {

	title = 'User';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public userService: UserService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, userService, errorService)
  }

	ngOnInit() {
		this.entityName = 'User';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['id', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'emailAddress',
				searchColumn: 'emailAddress',
				label: 'email Address',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'firstName',
				searchColumn: 'firstName',
				label: 'first Name',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'id',
				searchColumn: 'id',
				label: 'id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'isActive',
				searchColumn: 'isActive',
				label: 'is Active',
				sort: true,
				filter: true,
				type: ListColumnType.Boolean
			},
    		{
				column: 'isEmailConfirmed',
				searchColumn: 'isEmailConfirmed',
				label: 'is Email Confirmed',
				sort: true,
				filter: true,
				type: ListColumnType.Boolean
			},
    		{
				column: 'lastName',
				searchColumn: 'lastName',
				label: 'last Name',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'password',
				searchColumn: 'password',
				label: 'password',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'phoneNumber',
				searchColumn: 'phoneNumber',
				label: 'phone Number',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'userName',
				searchColumn: 'userName',
				label: 'user Name',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
		  	{
				column: 'actions',
				label: 'Actions',
				sort: false,
				filter: false,
				type: ListColumnType.String
			}
		];
		this.selectedColumns = this.columns;
		this.displayedColumns = this.columns.map((obj) => { return obj.column });
  	}
  addNew(comp) {
	if(!comp){
		comp = UserNewComponent;
	}
	super.addNew(comp);
  }
  
}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { T4ExtendedService } from '../t-4.service';
import { T4NewExtendedComponent } from '../new/t-4-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { T4ListComponent } from 'src/app/entities/t-4/index';

@Component({
  selector: 'app-t-4-list',
  templateUrl: './t-4-list.component.html',
  styleUrls: ['./t-4-list.component.scss']
})
export class T4ListExtendedComponent extends T4ListComponent implements OnInit {

	title:string = "T4";
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public t4Service: T4ExtendedService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, global, dialog, changeDetectorRefs, pickerDialogService, t4Service, errorService,
		globalPermissionService,
		)
  }

	ngOnInit() {
		super.ngOnInit();
	}
  
	addNew() {
		super.addNew(T4NewExtendedComponent);
	}
  
}

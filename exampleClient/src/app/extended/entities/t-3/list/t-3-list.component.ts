import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { T3ExtendedService } from '../t-3.service';
import { T3NewExtendedComponent } from '../new/t-3-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { T3ListComponent } from 'src/app/entities/t-3/index';

@Component({
  selector: 'app-t-3-list',
  templateUrl: './t-3-list.component.html',
  styleUrls: ['./t-3-list.component.scss']
})
export class T3ListExtendedComponent extends T3ListComponent implements OnInit {

	title:string = "T3";
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public t3Service: T3ExtendedService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, global, dialog, changeDetectorRefs, pickerDialogService, t3Service, errorService,
		globalPermissionService,
		)
  }

	ngOnInit() {
		super.ngOnInit();
	}
  
	addNew() {
		super.addNew(T3NewExtendedComponent);
	}
  
}

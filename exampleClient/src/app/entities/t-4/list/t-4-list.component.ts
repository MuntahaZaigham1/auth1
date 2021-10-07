import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IT4 } from '../it-4';
import { T4Service } from '../t-4.service';
import { Router, ActivatedRoute } from '@angular/router';
import { T4NewComponent } from '../new/t-4-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';


@Component({
  selector: 'app-t-4-list',
  templateUrl: './t-4-list.component.html',
  styleUrls: ['./t-4-list.component.scss']
})
export class T4ListComponent extends BaseListComponent<IT4> implements OnInit {

	title = 'T4';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public t4Service: T4Service,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, t4Service, errorService)
  }

	ngOnInit() {
		this.entityName = 'T4';
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
				column: 'score',
				searchColumn: 'score',
				label: 'score',
				sort: true,
				filter: true,
                       type : ListColumnType.Array,
                       subtype : ListColumnType.Number,
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
		comp = T4NewComponent;
	}
	super.addNew(comp);
  }
  
}

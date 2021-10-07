import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { T3ExtendedService, T3DetailsExtendedComponent, T3ListExtendedComponent } from '../';
import { IT3 } from 'src/app/entities/t-3';
describe('T3DetailsExtendedComponent', () => {
  let component: T3DetailsExtendedComponent;
  let fixture: ComponentFixture<T3DetailsExtendedComponent>;
  let el: HTMLElement;
  
  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          T3DetailsExtendedComponent,
          DetailsComponent
        ],
        imports: [TestingModule],
        providers: [
          T3ExtendedService,
        ],
        schemas: [NO_ERRORS_SCHEMA]  
      }).compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(T3DetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
  describe('Integration Tests', () => {
    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          T3DetailsExtendedComponent,
          T3ListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 't3', component: T3DetailsExtendedComponent },
            { path: 't3/:id', component: T3ListExtendedComponent }
          ])
        ],
        providers: [
          T3ExtendedService
        ]

      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(T3DetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
});

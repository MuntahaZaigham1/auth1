
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IT4 } from './it-4';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class T4Service extends GenericApiService<IT4> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "t4");
  }
  
  
  
}

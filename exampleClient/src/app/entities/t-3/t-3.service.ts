
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IT3 } from './it-3';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class T3Service extends GenericApiService<IT3> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "t3");
  }
  
  
  
}

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DictionaryService {

  protected API_URL = environment.webApiUrl;

  constructor(private http: HttpClient) { }

  searchWord(key: string, wordLimit: number): any {
    const url = `${this.API_URL}/dictionary/search/${key}?wordLimit=${wordLimit}`;
    return this.http.get(url);
  }

  uploadFile(file: any): any {
    const formData = new FormData();
    formData.append('file', file);
    const url = `${this.API_URL}/dictionary/upload`;
    return this.http.post(url, formData);
 }
}

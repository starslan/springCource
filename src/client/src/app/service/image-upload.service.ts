import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";


const IMAGE_API = environment.apiUrl + '/api/image/';

@Injectable({
  providedIn: 'root'
})
export class ImageUploadService {

  constructor(private http: HttpClient) { }

  uploadImageToUser(file: File): Observable<any>{
    const uploadData = new FormData();
    uploadData.append('file', file);

    return this.http.post(IMAGE_API + 'uload' , uploadData);
  }

  uploadImageToPost(file: File, postId: number): Observable<any>{
    const uploadData = new FormData();
    uploadData.append('file', file);

    return this.http.post(IMAGE_API + postId + '/upload', uploadData);
  }

  getProfileImage(): Observable<any>{
    return this.http.get(IMAGE_API + 'profileImage');
  }

  getImageToPost(postId: number):any{
    return this.http.get(IMAGE_API + postId+'/image')
  }
}

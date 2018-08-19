import { Injectable } from '@angular/core';
import { AuthenticationService } from '../user/authentication.service';
import { Http, Response, Headers } from '@angular/http';
import { Post } from './post.model';

import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';


@Injectable()
export class PostDataService {
  private _appUrl = '/API/post';
  private _post;

  constructor(private http: Http, private auth: AuthenticationService) { }

  
  get posts(): Observable<Post[]> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);

    return this.http.get(`${this._appUrl}/`)
      .map(response => response.json().map(item => Post.fromJSON(item)));
  }

  addNewPost(post:any): Observable<Post> {
    let userId;
    this.auth.user$.subscribe(item => userId = item);
    return this.http.post(`${this._appUrl}/${userId}`, post)
      .map(response => response.json()).map(item => Post.fromJSON(item));
  }
}

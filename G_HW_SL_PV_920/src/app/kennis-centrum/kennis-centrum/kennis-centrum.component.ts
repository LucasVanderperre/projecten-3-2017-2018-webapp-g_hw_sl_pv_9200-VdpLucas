import { Component, OnInit } from '@angular/core';
import { Post } from '../post.model';
import { PostDataService } from '../post-data.service';
import { AuthenticationService } from '../../user/authentication.service';

@Component({
  selector: 'app-kennis-centrum',
  templateUrl: './kennis-centrum.component.html',
  styleUrls: ['./kennis-centrum.component.css']
})
export class KennisCentrumComponent implements OnInit {
  private _posts: Post[];
  moderator : String;

  constructor(private _postDataService: PostDataService, private auth: AuthenticationService) {
  }

  ngOnInit() {
    this._postDataService.posts.subscribe(items => this._posts = items);
    this.moderator = this.auth.mod;
  }

  get posts() {
    return this._posts;
  }


 // removeReview(review: Review) {
 //   this._reviewDataService.removeReview(review).subscribe(item =>
  //    this._reviews = this._reviews.filter(val => item.id !== val.id)
  //  );
  //}


}

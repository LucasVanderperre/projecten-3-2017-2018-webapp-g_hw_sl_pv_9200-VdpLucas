import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PostDataService } from '../post-data.service';
import { Post } from '../post.model';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  @Input() public post: Post;
 // @Output() public deletePost = new EventEmitter<Post>();

  constructor(private _postDataService: PostDataService) {
  }
  ngOnInit() {
  }



}

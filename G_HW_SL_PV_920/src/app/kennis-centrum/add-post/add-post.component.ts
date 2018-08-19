import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Post } from '../post.model';
import { PostDataService } from '../post-data.service';
import { AuthenticationService } from '../../user/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {
  @Output() public newPost = new EventEmitter<Post>();
  public post: FormGroup;
  public postR:Post;

  constructor(private fb: FormBuilder, private _postDataService: PostDataService, private _router: Router, private auth: AuthenticationService) { }

  ngOnInit() {
    this.post = this.fb.group({
      titel: "",
      bericht: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit() {
    const post = new Post(this.post.value.titel, this.post.value.bericht, new Date());
    this._postDataService.addNewPost(post).subscribe(post=>this.postR=post);
    this._router.navigate(['centrum']);

  }  

}

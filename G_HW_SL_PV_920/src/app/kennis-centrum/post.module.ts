import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { KennisCentrumComponent } from './kennis-centrum/kennis-centrum.component';
import { PostDataService } from './post-data.service';
import { PostComponent } from './post/post.component';
import { AddPostComponent } from './add-post/add-post.component';
import { AuthGuardService } from '../user/auth-guard.service';
import { ModeratorGuardService } from '../user/moderator-guard.service';



const routes = [
  { path: 'centrum', component: KennisCentrumComponent },
 { path: 'add',  canActivate: [ ModeratorGuardService ], component: AddPostComponent}

  //{ path: ':id', component: ReservationDetailComponent,
  //  resolve: { recipe: ReservationResolver} }
];

@NgModule({
  declarations: [
    KennisCentrumComponent,
    PostComponent,
    AddPostComponent
  ],
  imports: [
    HttpModule,
    CommonModule,
    ReactiveFormsModule,
      NgbModule,
    RouterModule.forChild(routes)
  ],
  providers: [
    PostDataService,
    ModeratorGuardService

  ],
})
export class PostModule { }

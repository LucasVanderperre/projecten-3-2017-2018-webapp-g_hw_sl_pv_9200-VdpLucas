import { TestBed, inject } from '@angular/core/testing';

import { PostDataService } from './post-data.service';
import { HttpModule} from '@angular/http';
import { AuthenticationService } from '../user/authentication.service';


describe('PostDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [PostDataService , AuthenticationService]
    });
  });

  it('should be created', inject([PostDataService], (service: PostDataService) => {
    expect(service).toBeTruthy();
  }));
});

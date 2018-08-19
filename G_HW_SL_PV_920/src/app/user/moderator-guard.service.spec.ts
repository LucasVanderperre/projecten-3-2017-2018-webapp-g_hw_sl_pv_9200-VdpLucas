import { TestBed, inject } from '@angular/core/testing';

import { AuthenticationService } from './authentication.service';
import { HttpModule } from '@angular/http';
import { RouterTestingModule } from '@angular/router/testing';
import { ModeratorGuardService } from './moderator-guard.service';

describe('ModeratorGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule, RouterTestingModule],
      providers: [ModeratorGuardService, AuthenticationService]
    });
  });


  it('should be created', inject([ModeratorGuardService], (service: ModeratorGuardService) => {
    expect(service).toBeTruthy();
  }));
});

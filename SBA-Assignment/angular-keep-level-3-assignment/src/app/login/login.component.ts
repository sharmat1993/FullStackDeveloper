import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { RouterService } from '../services/router.service';
import { User } from '../user';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
    userId = new FormControl();
    userPassword = new FormControl();
    private user: User = new User();
    submitMessage: String;

    constructor(private authenticationService: AuthenticationService, private routerService: RouterService) {
    }

    loginSubmit() {
      this.user.userId = this.userId.value;
      this.user.userPassword = this.userPassword.value;
      this.authenticationService.authenticateUser(this.user).subscribe(
          (response) => {
                this.authenticationService.setBearerToken(response['bearerToken']);
                this.routerService.routeToDashboard();
            },
          (loginError) => {
            if (loginError.error) {
              this.submitMessage = loginError.error.message;
            }else {
              this.submitMessage = loginError.message;
            }
          }
      );
    }
}

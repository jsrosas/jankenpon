import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { User } from '../model/user';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatButtonModule, MatIconModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  isAuthenticated!: boolean;
  user!: User;

  constructor(public auth: AuthService) {
  }

  async ngOnInit() {
    this.isAuthenticated = await this.auth.isAuthenticated();
    this.auth.getUser().subscribe(data => this.user = data);
  }

}

import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card'
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { map, switchMap, of } from 'rxjs';
import { Game } from '../model/Game';
import { Player } from '../model/player';

@Component({
  selector: 'app-game-edit',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    RouterLink,
  ],
  templateUrl: './game-edit.component.html',
  styleUrl: './game-edit.component.css'
})

export class GameEditComponent implements OnInit {

  game!: Game;
  player!: Player;
  feedback: any = {};

  constructor(private route: ActivatedRoute, private router: Router,
    private http: HttpClient) {
  }

  ngOnInit() {
    this.route.params.pipe(
      map(p => p['id']),
      switchMap(id => {
        if (id === 'new') {
          const game: Game = {
            name: '',
          }
          return of(game);
        }
        return this.http.get<Game>(`api/game/${id}`);
      })
    ).subscribe({
      next: game => {
        this.game = game;
        const player = game.players?.find(player => !player.defaultComputer);
        this.player = player ?? {name:'', defaultComputer: false}
        this.feedback = {};
      },
      error: () => {
        this.feedback = {type: 'warning', message: 'Error loading'};
      }
    });
  }

  save() {
    const id = this.game.id;
    this.game.players = [this.player]
    const method = id ? 'put' : 'post';

    this.http[method](`/api/game${id ? '/' + id : ''}`, this.game).subscribe({
      next: () => {
        this.feedback = {type: 'success', message: 'Save was successful!'};
        setTimeout(async () => {
          await this.router.navigate(['/games']);
        }, 1000);
      },
      error: () => {
        this.feedback = {type: 'error', message: 'Error saving'};
      }
    });
  }

  async cancel() {
    await this.router.navigate(['/games']);
  }
}

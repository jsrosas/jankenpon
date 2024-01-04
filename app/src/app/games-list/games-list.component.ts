import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { Game } from '../model/Game';

@Component({
  selector: 'app-games-list',
  standalone: true,
  imports: [RouterLink, MatButtonModule, MatTableModule, MatIconModule, DatePipe, HttpClientModule],
  templateUrl: './games-list.component.html',
  styleUrl: './games-list.component.css'
})
export class GamesListComponent {
  title = 'Game List';
  loading = true;
  games: Game[] = [];
  displayedColumns = ['id', 'player1', 'player2', 'actions'];
  feedback: any = {};

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.loading = true;
    this.http.get<Game[]>('api/games').subscribe((data: Game[]) => {
      this.games = data;
      this.loading = false;
      this.feedback = {};
    });
  }

  delete(game: Game): void {
    if (confirm(`Are you sure you want to delete ${game.name}?`)) {
      this.http.delete(`api/game/${game.id}`).subscribe({
        next: () => {
          this.feedback = {type: 'success', message: 'Delete was successful!'};
          setTimeout(() => {
            this.ngOnInit();
          }, 1000);
        },
        error: () => {
          this.feedback = {type: 'warning', message: 'Error deleting.'};
        }
      });
    }
  }

  protected readonly event = event;
}

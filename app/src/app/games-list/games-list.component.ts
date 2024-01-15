import { CommonModule } from '@angular/common';
import { Component, } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { Observable, take, tap } from 'rxjs';
import { Game } from '../model/Game';
import { GameService } from '../service/game.service';

@Component({
  selector: 'app-games-list',
  standalone: true,
  imports: [
    CommonModule, 
    RouterLink, 
    MatButtonModule, 
    MatTableModule, 
    MatIconModule, 
  ],
  templateUrl: './games-list.component.html',
  styleUrl: './games-list.component.css'
})
export class GamesListComponent {
  title = 'Game List';
  loading = false;
  games: Game[] = [];
  displayedColumns = ['id', 'players', 'actions'];
  feedback: any = {};
  games$ : Observable<Game[]> = this.gameService.getGames().pipe(
    take(1),
    tap(games => this.loading = false)
  );

  constructor(private gameService: GameService ) {
  }

  ngOnInit() {
    this.loading = true;
  }
}

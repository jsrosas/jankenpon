import { Routes } from '@angular/router';
import { GameEditComponent } from './game-edit/game-edit.component';
import { GamesListComponent } from './games-list/games-list.component';
import { HomeComponent } from './home/home.component';
import { PlayComponent } from './play/play.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'games',
    component: GamesListComponent
  },
  {
    path: 'game/:id',
    component: GameEditComponent
  },
  {
    path: 'game/:id/play',
    component: PlayComponent
  },
];

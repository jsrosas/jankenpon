import { Routes } from '@angular/router';
import {HomeComponent} from './home/home.component';
import { GamesListComponent } from './games-list/games-list.component';
import { GameEditComponent } from './game-edit/game-edit.component';

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
];

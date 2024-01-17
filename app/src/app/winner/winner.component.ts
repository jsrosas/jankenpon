import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { Game } from '../model/Game';
import { Match } from '../model/match';
import { Player } from '../model/player';

@Component({
  selector: 'app-winner',
  standalone: true,
  imports: [MatCardModule, NgClass],
  templateUrl: './winner.component.html',
  styleUrl: './winner.component.css'
})
export class WinnerComponent {
  @Input() match?: Match;
  @Input() game?: Game;
  winner?: Player;
  private _winnerClass?: string;

  public get resultLabel() {
    const winner = this.game?.players?.find(player => player.id === this.match?.winnerId);
    return winner ? `Winner is ${winner.name}` : 'It is a Tie';
  }

  public get winnerClass(){
    const winner = this.game?.players?.find(player => player.id === this.match?.winnerId);
    if(this.match?.tie === true){
      this._winnerClass = 'tie';
    }
    if(winner?.defaultComputer){
      this._winnerClass = 'computer'
    }
    if(winner?.defaultComputer === false){
      this._winnerClass = 'player'
    }
    console.log(this._winnerClass);
    return this._winnerClass;
  }

}

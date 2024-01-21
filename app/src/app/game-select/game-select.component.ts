import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonToggleChange, MatButtonToggleModule } from '@angular/material/button-toggle';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faHandBackFist, faL } from '@fortawesome/free-solid-svg-icons';
import { faScroll } from '@fortawesome/free-solid-svg-icons';
import { faScissors } from '@fortawesome/free-solid-svg-icons';
import { Match } from '../model/match';

@Component({
  selector: 'app-game-select',
  standalone: true,
  imports: [
    MatButtonToggleModule,
    FontAwesomeModule,
  ],
  templateUrl: './game-select.component.html',
  styleUrl: './game-select.component.css'
})
export class GameSelectComponent {
  faHandBackFist = faHandBackFist;
  faScroll = faScroll;
  faScissors = faScissors;
  playerSelection!: number;
  @Input() match?: Match;
  @Input() isReadOnly: boolean = false;
  @Output() playerSelectionEvent = new EventEmitter<number>();

  public onChange($event: MatButtonToggleChange){
    this.playerSelectionEvent.emit($event.source.value)
  }
}

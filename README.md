# jankenpon
Jan Ken Pon Game

### Run Application
```
mvn spring-boot:run -Pprod
```
Application should be available at http://localhost:8080

### How It Works
The application depends on Auth0 for authentication, a configuration was provided,
but the application will be disabled after this test.
The application allows the user to generate multiple games.

Each game has multiple matches where the rock paper scissors is played out.
We build statistics based on the matches belonging to the game.

Once the player decides to play the game, he is given the rock, paper, scissors options.
He can send his choice by clicking play.
The back end will calculate the match and respond with the result and the current statistics.

The player is free to send the same selection or change selection to play again.



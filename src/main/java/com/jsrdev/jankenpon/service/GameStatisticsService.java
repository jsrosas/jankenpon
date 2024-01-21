package com.jsrdev.jankenpon.service;

import com.jsrdev.jankenpon.dto.GameStatisticsDTO;
import com.jsrdev.jankenpon.model.Game;

public interface GameStatisticsService {
    GameStatisticsDTO buildStatisticsFromGame(Game game);
}

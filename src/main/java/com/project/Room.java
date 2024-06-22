package com.project;

import com.project.object.Exit;
import com.project.object.GameObject;

public class Room {
  private final String symbol = "  ";

  private Coordinate coordinate;
  private Player player;
  private GameObject gameObject;

  public Room() {
    this.coordinate = new Coordinate(-1, -1);
  }

  public Room(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public void playerEnterRoom(Player player) {
    this.player = player;
    player.setHasWon(gameObject instanceof Exit);
  }

  public void playerLeaveRoom() {
    this.player = null;
  }

  public void setGameObject(GameObject gameObject) {
    this.gameObject = gameObject;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  @Override
  public String toString() {
    if (player != null) {
      return player.toString();
    }
    if (gameObject != null) {
      return gameObject.toString();
    }
    return symbol;
  }

}

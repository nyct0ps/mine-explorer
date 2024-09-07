package com.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.item.Grenade;

public class Player extends Draw {

  private static final String symbol = "♛♛";
  private static final double PERCENTAGE_GRENADE = 0.1;

  private PlayerState state;
  private Board board;
  private List<Grenade> grenades;

  public Player() {
    super(symbol);
    state = PlayerState.PLAYING;
    grenades = new ArrayList<>();
  }

  public void setBoard(Board board) {
    this.board = board;
    setCoordinate(new Coordinate(board.getNbColumn() / 2, board.getNbRow() / 2));
    setupGrenades();
  }

  @Override
  public void setCoordinate(Coordinate coordinate) {
    if (!board.isRoomExist(coordinate))
      return;

    if (this.coordinate != null) {
      board.getRoomByCoordinate(this.coordinate).ifPresent(room -> room.playerLeaveRoom());
    }

    this.coordinate = coordinate;
    board.getRoomByCoordinate(this.coordinate).ifPresent(room -> room.playerEnterRoom(this));
  }

  public void moveToDirection(String direction) {
    if (!canMoveToDirection(direction)) {
      return;
    }

    setCoordinate(getNextCoordinate(direction));
  }

  private boolean canMoveToDirection(String direction) {
    Optional<Room> currentRoomOptional = board.getRoomByCoordinate(this.coordinate);
    if (!currentRoomOptional.isPresent()) {
      return false;
    }
    Room currentRoom = currentRoomOptional.get();

    Wall wallByDirection = currentRoom.getWallByDirection(direction);

    return wallByDirection.isDestroyed();
  }

  public void throwGrenadeInDirection(String direction) {
    int numberOfGrenades = grenades.size();

    if (numberOfGrenades == 0)
      return;

    Grenade grenade = grenades.remove(numberOfGrenades - 1);
    grenade.use(this, direction);
    moveToDirection(direction);
  }

  private void setupGrenades() {
    int totalNumberOfRooms = board.getNbRow() * board.getNbColumn();
    int totalNumberOfGrenades = (int) (totalNumberOfRooms * PERCENTAGE_GRENADE);

    for (int i = 0; i < totalNumberOfGrenades; i++) {
      grenades.add(new Grenade(board));
    }
  }

  public PlayerState getState() {
    return state;
  }

  public void setState(PlayerState state) {
    this.state = state;
  }

  public List<Grenade> getGrenades() {
    return grenades;
  }
}

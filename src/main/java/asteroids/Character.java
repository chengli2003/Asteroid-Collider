/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import static javafx.scene.shape.Shape.intersect;

/**
 *
 * @author troll
 */
public abstract class Character {
    private Polygon character;
    private Point2D movement;
    private boolean isAlive;
    
    public Character(Polygon shape, int x, int y) {
        this.character = shape;
        character.setTranslateX(x);
        character.setTranslateY(y);
        movement = new Point2D(0, 0);
        isAlive = true;
    }
    
    public Polygon getCharacter() {
        return character;
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
    
    public void leftTurn() {
        character.setRotate(character.getRotate() - 5);
    }
    
    public void rightTurn() {
        character.setRotate(character.getRotate() + 5);
    }
    
    public void move() {
        character.setTranslateX(character.getTranslateX() + movement.getX());
        character.setTranslateY(character.getTranslateY() + movement.getY());
        if (character.getTranslateX() < 0) {
            character.setTranslateX(character.getTranslateX() + AsteroidsApplication.WIDTH);
        }
        if (character.getTranslateX() > AsteroidsApplication.WIDTH) {
            character.setTranslateX(character.getTranslateX() - AsteroidsApplication.WIDTH);
        }
        if (character.getTranslateY() < 0) {
            character.setTranslateY(character.getTranslateY() + AsteroidsApplication.HEIGHT);
        }
        if (character.getTranslateY() > AsteroidsApplication.HEIGHT) {
            character.setTranslateY(character.getTranslateY() - AsteroidsApplication.HEIGHT);
        }
    }
    
    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(character.getRotate()));
        double changeY = Math.sin(Math.toRadians(character.getRotate()));
        changeX *= 0.05;
        changeY *= 0.05;
        
        movement = movement.add(changeX, changeY);
    }
    
    public void deccelerate() {
        double changeX = -Math.cos(Math.toRadians(character.getRotate()));
        double changeY = -Math.sin(Math.toRadians(character.getRotate()));
        changeX *= 0.05;
        changeY *= 0.05;
        
        movement = movement.add(changeX, changeY);
    }
    
    public boolean collide (Character other){
        return intersect(character, other.getCharacter()).getBoundsInLocal().getWidth() != -1;
    }
}

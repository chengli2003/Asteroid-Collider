/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author troll
 */
public class AsteroidsApplication extends Application {

    public static int WIDTH = 500;
    public static int HEIGHT = 360;

    public void start(Stage window) {
        //window
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);

        Scene scene = new Scene(pane);

        //text
        Text text = new Text(20, 10, "Points: ");
        pane.getChildren().add(text);

        //ship
        Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);
        pane.getChildren().add(ship.getCharacter());

        //asteroids
        ArrayList<Character> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Asteroid asteroid = new Asteroid(new Random().nextInt(WIDTH), new Random().nextInt(HEIGHT));
            asteroids.add(asteroid);
            pane.getChildren().add(asteroid.getCharacter());
        }

        //setting up keyboard events
        HashMap<KeyCode, Boolean> keys = new HashMap<>();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                keys.put(KeyCode.LEFT, true);
            } else if (event.getCode() == KeyCode.RIGHT) {
                keys.put(KeyCode.RIGHT, true);
            } else if (event.getCode() == KeyCode.UP) {
                keys.put(KeyCode.UP, true);
            } else if (event.getCode() == KeyCode.DOWN) {
                keys.put(KeyCode.DOWN, true);
            } else if (event.getCode() == KeyCode.SPACE) {
                keys.put(KeyCode.SPACE, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                keys.put(KeyCode.LEFT, false);
            } else if (event.getCode() == KeyCode.RIGHT) {
                keys.put(KeyCode.RIGHT, false);
            } else if (event.getCode() == KeyCode.UP) {
                keys.put(KeyCode.UP, false);
            } else if (event.getCode() == KeyCode.DOWN) {
                keys.put(KeyCode.DOWN, false);
            } else if (event.getCode() == KeyCode.SPACE) {
                keys.put(KeyCode.SPACE, false);
            }
        });

        ArrayList<Character> projectiles = new ArrayList<>();
        AtomicInteger points = new AtomicInteger();
        new AnimationTimer() {
            public void handle(long now) {
                //controls for turning, accelerating, deccelerating, and shooting
                if (keys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.leftTurn();
                } else if (keys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.rightTurn();
                } else if (keys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                } else if (keys.getOrDefault(KeyCode.DOWN, false)) {
                    ship.deccelerate();
                } else if (keys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 5) {
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());

                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    pane.getChildren().add(projectile.getCharacter());
                    projectiles.add(projectile);
                }

                //moving ship, asteroid, and projectile
                ship.move();
                asteroids.stream().forEach(asteroid -> {
                    asteroid.move();
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });
                projectiles.stream().forEach(projectile -> projectile.move());

                //removing projectiles and asteroids if they collide
                projectiles.stream().forEach(projectile -> {
                    asteroids.stream().forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            asteroid.setAlive(false);
                            projectile.setAlive(false);
                            text.setText("Points: " + points.addAndGet(1000));
                        }
                    });
                });
                remove(asteroids, pane);
                remove(projectiles, pane);

                //adding asteroids
                if (Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(new Random().nextInt(WIDTH), new Random().nextInt(HEIGHT));
                    if (!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }
            }
        }.start();

        window.setScene(scene);
        window.show();
    }

    public void remove(List<Character> list, Pane pane) {
        list.stream().filter(character -> !character.isAlive()).forEach(character -> pane.getChildren().remove(character.getCharacter()));
        list.removeAll(list.stream().filter(character -> !character.isAlive()).collect(Collectors.toList()));
    }

    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }
}

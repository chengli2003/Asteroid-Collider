/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.Random;
import javafx.scene.shape.Polygon;

/**
 *
 * @author troll
 */
public class Asteroid extends Character {
    private double rotationalMoment;

    public Asteroid(int x, int y) {
        super(new PentagonFactory().create(), x, y);
        Random rand = new Random();
        super.getCharacter().setRotate(rand.nextInt(360));
        for (int i = 0; i < rand.nextInt(11) + 1; i++) {
            super.accelerate();
        }
        rotationalMoment = 0.5 - rand.nextDouble();
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMoment);
    }
}

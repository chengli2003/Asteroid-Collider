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
public class PentagonFactory {

    public Polygon create() {
        Random rand = new Random();
        int size = rand.nextInt(10) + 10;

        double c1 = size * Math.cos(2 * Math.PI / 5);
        double c2 = size * Math.cos(Math.PI / 5);
        double s1 = size * Math.sin(2 * Math.PI / 5);
        double s2 = size * Math.sin(4 * Math.PI / 5);
        Polygon pentagon = new Polygon(0, size, s1, c1, s2, -c2, -s2, -c2, -s1, c1);

        for (int i = 0; i < pentagon.getPoints().size(); i++) {
            int translate = rand.nextInt(4) - 2;
            pentagon.getPoints().set(i, pentagon.getPoints().get(i) + translate);
        }

        return pentagon;
    }
}

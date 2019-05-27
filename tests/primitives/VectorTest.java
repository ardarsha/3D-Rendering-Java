package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void add() {
        try {
            // Test: passing in null as an argument
            assertThrows(NullPointerException.class, () -> new Vector(1, 2, 3).add(null));

            // Test: addition results in zero vector
            assertThrows(ZeroVectorException.class, () -> new Vector(0, 1, 1).add(new Vector(0, -1, -1)));

            Coordinate pos = new Coordinate(7.5), neg = new Coordinate(-3.8);
            Vector testVector0, testVector1;
            for (int i = 0; i < 8; ++i) {
                testVector0 = new Vector(i % 2 == 0 ? pos : neg, i / 2 % 2 == 0 ? pos : neg, i / 4 == 0 ? pos : neg);
                for (int j = 0; j < 8; ++j) {
                    testVector1 = new Vector(j % 2 == 0 ? pos : neg, j / 2 % 2 == 0 ? pos : neg, j / 4 == 0 ? pos : neg);
                    assertEquals(
                            new Vector(
                                    (i % 2 == 0 ? pos : neg).add(j % 2 == 0 ? pos : neg),
                                    (i / 2 % 2 == 0 ? pos : neg).add(j / 2 % 2 == 0 ? pos : neg),
                                    (i / 4 == 0 ? pos : neg).add(j / 4 == 0 ? pos : neg)
                            ), testVector0.add(testVector1));
                }
            }

            for (int i = 0; i < 3; ++i) {
                testVector0 = new Vector(i == 0 ? pos : Coordinate.ZERO, i == 1 ? pos : Coordinate.ZERO, i == 2 ? pos : Coordinate.ZERO);
                for (int j = 0; j < 3; ++j) {
                    testVector1 = new Vector(j == 0 ? pos : Coordinate.ZERO, j == 1 ? pos : Coordinate.ZERO, j == 2 ? pos : Coordinate.ZERO);
                    assertEquals(
                            new Vector(
                                    (i == 0 ? pos : Coordinate.ZERO).add(j == 0 ? pos : Coordinate.ZERO),
                                    (i == 1 ? pos : Coordinate.ZERO).add(j == 1 ? pos : Coordinate.ZERO),
                                    (i == 2 ? pos : Coordinate.ZERO).add(j == 2 ? pos : Coordinate.ZERO)
                            ), testVector0.add(testVector1));
                }
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void subtract() {
        try {
            // Test 1: passing in null as an argument
            assertThrows(NullPointerException.class, () -> new Vector(1, 2, 3).subtract(null));

            // Test 2-66: subtracting all combinations possible of different quarters (includes subtracting identical ones)
            Coordinate pos = new Coordinate(7.5), neg = new Coordinate(-3.8);
            Vector testVector0, testVector1;
            for (int i = 0; i < 8; ++i) {
                testVector0 = new Vector(i % 2 == 0 ? pos : neg, i / 2 % 2 == 0 ? pos : neg, i / 4 == 0 ? pos : neg);
                for (int j = 0; j < 8; ++j) {
                    testVector1 = new Vector(j % 2 == 0 ? pos : neg, j / 2 % 2 == 0 ? pos : neg, j / 4 == 0 ? pos : neg);
                    if (testVector0.equals(testVector1)) {
                        final Vector test0 = new Vector(testVector0);
                        final Vector test1 = new Vector(testVector1);
                        assertThrows(ZeroVectorException.class, () -> test0.subtract(test1));
                    } else {
                        assertEquals(
                                new Vector(
                                        testVector0.getHead().getX().subtract(testVector1.getHead().getX()),
                                        testVector0.getHead().getY().subtract(testVector1.getHead().getY()),
                                        testVector0.getHead().getZ().subtract(testVector1.getHead().getZ())
                                ), testVector0.subtract(testVector1));
                    }
                }
            }

            // Test 68-77: subtracting all combinations on axises
            for (int i = 0; i < 3; ++i) {
                testVector0 = new Vector(i == 0 ? pos : Coordinate.ZERO, i == 1 ? pos : Coordinate.ZERO, i == 2 ? pos : Coordinate.ZERO);
                for (int j = 0; j < 3; ++j) {
                    testVector1 = new Vector(j == 0 ? pos : Coordinate.ZERO, j == 1 ? pos : Coordinate.ZERO, j == 2 ? pos : Coordinate.ZERO);
                    if (testVector0.equals(testVector1)) {
                        final Vector test0 = new Vector(testVector0);
                        final Vector test1 = new Vector(testVector1);
                        assertThrows(ZeroVectorException.class, () -> test0.subtract(test1));
                    } else {
                        assertEquals(
                                new Vector(
                                        (i == 0 ? pos : Coordinate.ZERO).subtract(j == 0 ? pos : Coordinate.ZERO),
                                        (i == 1 ? pos : Coordinate.ZERO).subtract(j == 1 ? pos : Coordinate.ZERO),
                                        (i == 2 ? pos : Coordinate.ZERO).subtract(j == 2 ? pos : Coordinate.ZERO)
                                ), testVector0.subtract(testVector1));
                    }
                }
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void scale() {
        try {
            // Test 1: scale by 0
            assertThrows(ZeroVectorException.class, () -> new Vector(1, 4, 6).scale(0));

            // scale by negative or positive, all types of vectors
            Coordinate cPos = new Coordinate(1.234526382648795627563756);
            Coordinate cNeg = new Coordinate(-3.864567737567695627563756);
            double dPos = 2.78623576827628657385823;
            double dNeg = -2.78623576827628657385823;

            for (int i = 0; i < 8; ++i) {
                Vector test = new Vector(i / 4 == 0 ? cPos : cNeg, i / 2 % 2 == 0 ? cPos : cNeg, i % 2 == 0 ? cPos : cNeg);
                assertEquals(test.scale(dPos), new Vector(
                        test.getHead().getX().scale(dPos),
                        test.getHead().getY().scale(dPos),
                        test.getHead().getZ().scale(dPos)
                ));
                assertEquals(test.scale(dNeg), new Vector(
                        test.getHead().getX().scale(dNeg),
                        test.getHead().getY().scale(dNeg),
                        test.getHead().getZ().scale(dNeg)
                ));
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void dotProduct() {
        fail("Not implemented yet");
    }

    @Test
    void crossProduct() {
        fail("Not implemented yet");
    }

    @Test
    void length() {
        fail("Not implemented yet");
    }

    @Test
    void normalised() {
        try {
            Coordinate cPos = new Coordinate(1.234526382648795627563756);
            Coordinate cNeg = new Coordinate(-3.864567737567695627563756);

            for (int i = 0; i < 8; ++i) {
                Vector test = new Vector(i / 4 == 0 ? cPos : cNeg, i / 2 % 2 == 0 ? cPos : cNeg, i % 2 == 0 ? cPos : cNeg);
                assertEquals(new Coordinate(1), new Coordinate(test.normalised().length()));
                assertThrows(ZeroVectorException.class, () -> test.crossProduct(test.normalised()));
            }
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
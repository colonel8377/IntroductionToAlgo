package UpperEnvelope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpperEnvelope {
    public static void main(String[] args) {
        double[] A = {1,2,8,4,-1,-2,-1,2};
        double[] B = {1,4,-1,-6,10,16,20,2};
        List points = new ArrayList<CartesianCoordinates>();
        for(int i = 0; i < A.length; i++){
            points.add(new CartesianCoordinates(A[i], -B[i]));
        }
        List results = GFG.convexHull(points);
        double slope, intercept;
        List upperVertice = new ArrayList<CartesianCoordinates>();
        for(int i = 0; i < results.size() - 1; i ++){
            slope = CartesianCoordinates.slope((CartesianCoordinates)results.get(i),(CartesianCoordinates)results.get(i + 1));
            intercept = CartesianCoordinates.intercept(slope,(CartesianCoordinates)results.get(i + 1));
            upperVertice.add(new CartesianCoordinates(slope, -intercept));
        }
        upperVertice.forEach(System.out::println);
    }
}

class GFG {

    public static List<CartesianCoordinates> convexHull(List<CartesianCoordinates> p) {
        if (p.isEmpty()) return null;
        p.sort(CartesianCoordinates::compareTo);
        List<CartesianCoordinates> h = new ArrayList<>();

        // lower hull
        for (CartesianCoordinates pt : p) {
            while (h.size() >= 2 && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }

        // upper hull
        int t = h.size() + 1;
        for (int i = p.size() - 1; i >= 0; i--) {
            CartesianCoordinates pt = p.get(i);
            while (h.size() >= t && !ccw(h.get(h.size() - 2), h.get(h.size() - 1), pt)) {
                h.remove(h.size() - 1);
            }
            h.add(pt);
        }
//        h.remove(h.size() - 1);
        return h;
    }

    // ccw returns true if the three points make a counter-clockwise turn
    private static boolean ccw(CartesianCoordinates a, CartesianCoordinates b, CartesianCoordinates c) {
        return ((b.getX() - a.getX()) * (c.getY() - a.getY())) > ((b.getY() - a.getY()) * (c.getX() - a.getX()));
    }
}

class  CartesianCoordinates implements Comparable<CartesianCoordinates>{
    double x;
    double y;
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public CartesianCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "CartesianCoordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(CartesianCoordinates o) {
        return Double.compare(this.x, o.x);
    }
    public static double slope(CartesianCoordinates x, CartesianCoordinates y){
        return (x.getX() - y.getX()) == 0 ? 0.0 : (x.getY() - y.getY()) / (x.getX() - y.getX()) ;
    }
    public static double intercept(double slope, CartesianCoordinates x){
        return x.getY() - slope * x.getX();
    }
}

class LinearFunction implements Comparable<LinearFunction> {
    int slope;
    int intercept;

    @Override
    public String toString() {
        return "LinearFunction{" +
                "slope=" + slope +
                ", intercept=" + intercept +
                '}';
    }

    public int getSlope() {
        return this.slope;
    }

    public void setSlope(int slope) {
        this.slope = slope;
    }

    public int getIntercept() {
        return this.intercept;
    }

    public void setIntercept(int intercept) {
        this.intercept = intercept;
    }


    public LinearFunction(int slope, int intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }
    public double Evaluate(double x)
    {
        return slope * x + intercept;
    }
    public CartesianCoordinates Intersect(LinearFunction other) {
        if (this.slope == other.slope)
            return null;
        double x = (double)(this.intercept - other.intercept) / (double)(other.slope - this.slope);
        return new CartesianCoordinates(x, this.Evaluate(x));
    }

    @Override
    public int compareTo(LinearFunction other) {
        if (this.slope == other.slope && this.intercept == other.intercept)
            return 0;
            //Functions with smaller slope first, if slope is equal the larger intercept goes first
        else if (this.slope < other.slope || (this.slope == other.slope && this.intercept > other.intercept))
            return -1;
        else
            return 1;
    }
}

class Envelope {
    public LinearFunction[] lines;
    public CartesianCoordinates[] intersections;

    public LinearFunction[] getLines() {
        return this.lines;
    }

    public void setLines(LinearFunction[] lines) {
        this.lines = lines;
    }

    public CartesianCoordinates[] getIntersections() {
        return intersections;
    }

    public void setIntersections(CartesianCoordinates[] intersections) {
        this.intersections = intersections;
    }

    public Envelope(LinearFunction[] lines, CartesianCoordinates[] intersections) {
        this.lines = lines;
        this.intersections = intersections;
    }

    @Override
    public String toString() {
        return "Envelope{" +
                "lines=" + Arrays.toString(lines) +
                ", intersections=" + Arrays.toString(intersections) +
                '}';
    }
}

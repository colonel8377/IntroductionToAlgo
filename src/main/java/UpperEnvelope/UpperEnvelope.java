package UpperEnvelope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpperEnvelope {
    public static void main(String[] args) {
        int[] A = {1,2,3,4,5,6,7,8};
        int[] B = {1,2,3,4,5,6,7,8};
        double res = new UpperEnvelope().solution(A, B);
        System.out.println(res);
    }
    private enum EnvelopeKind {
        Upper,
        Lower
    }
    int _leftBorder = -10000000;
    int _rightBorder = 10000000;
    public double solution(int[] A, int[] B) {
        //This is where result will be stored
        double result = Double.MAX_VALUE;

        //Sanity check
        if (A != null && A.length > 0 && B != null && B.length > 0)
        {
            int N = A.length;

            //We will treat every A[K]*X+B as linear function
            LinearFunction[] functions = new LinearFunction[N];
            for (int i = 0; i < N; i++){
                functions[i] = new LinearFunction(A[i], B[i]);
            }
            //We sort the functions by slope and intercept
            Arrays.sort(functions);
            for(int i = 0; i < functions.length; i++){
                System.out.println(functions[i]);
            }
            //We get the upper and lower envelopes for the functions collection
            Envelope upperEnvelope = getEnvelope(functions, EnvelopeKind.Upper);
            System.out.println(upperEnvelope);
            Envelope lowerEnvelope = getEnvelope(functions, EnvelopeKind.Lower);

            int upperEnvelopePointIndex = 1;
            int lowerEnvelopePointIndex = 1;

            //We need to minimaze the distance between envelopes, we will start by going through upper envelope
            while (upperEnvelopePointIndex < upperEnvelope.getIntersections().length)
            {
                double distance = Double.MAX_VALUE;

                //If current intersection in upper envelope lies before the current intersection in lower envelope (or there are no more intersections in lower envelope)
                if (upperEnvelope.getIntersections()[upperEnvelopePointIndex].getX() < lowerEnvelope.getIntersections()[lowerEnvelopePointIndex].getX() || lowerEnvelopePointIndex == lowerEnvelope.getIntersections().length - 1)
                {
                    //The distance is equal to value at current intersection from upper envelope minus the value of current function from lower envelope in this point
                    distance = upperEnvelope.getIntersections()[upperEnvelopePointIndex].getY() - lowerEnvelope.getLines()[lowerEnvelopePointIndex - 1].Evaluate(upperEnvelope.getIntersections()[upperEnvelopePointIndex].getX());
                    //We move to next intersection in upper envelope
                    upperEnvelopePointIndex++;
                }
                //Otherwise (the current intersection in upper envelope lies further than current intersection in lower envelope and there are still other intersection in lower envelope)
                else
                {
                    //The distance is equal to the value of current function from upper envelope in this point minus value at current intersection from lower envelope
                    distance = upperEnvelope.getLines()[upperEnvelopePointIndex - 1].Evaluate(lowerEnvelope.getIntersections()[lowerEnvelopePointIndex].getX()) - lowerEnvelope.getIntersections()[lowerEnvelopePointIndex].getY();

                    //We move to next intersection in lower envelope
                    lowerEnvelopePointIndex++;
                }

                //If new distance is smaller than result which we laready have
                if (distance < result)
                    //We update the result
                    result = distance;
            }
        }
        return result;
    }
    public Envelope getEnvelope(LinearFunction[] functions, EnvelopeKind kind)
    {
        Envelope envelope = null;

        if (functions != null && functions.length > 0)
        {
            int firstFunctionIndex, lastFunctionIndex, functionsIterationStep;
            //While looking for upper envelope we will go from first to last function
            if (kind == EnvelopeKind.Upper)
            {
                firstFunctionIndex = 0;
                lastFunctionIndex = functions.length - 1;
                functionsIterationStep = 1;
            }
            //While looking for lower envelope we will go from last to first function
            else
            {
                firstFunctionIndex = functions.length - 1;
                lastFunctionIndex = 0;
                functionsIterationStep = -1;
            }

            List<LinearFunction> lines = new ArrayList<>();
            List<CartesianCoordinates> points = new ArrayList<>();

            //We set the first line in the envelope
            LinearFunction previousLine = functions[firstFunctionIndex];
            lines.add(previousLine);

            //And calculate "theoretical" furthest to left point
            points.add(new CartesianCoordinates(_leftBorder, previousLine.Evaluate(_leftBorder)));

            //We will look for intersection points between the functions
            for (int i = firstFunctionIndex + functionsIterationStep; (kind == EnvelopeKind.Upper && i <= lastFunctionIndex) || (kind == EnvelopeKind.Lower && i >= lastFunctionIndex); i += functionsIterationStep)
            {
                LinearFunction currentLine = functions[i];
                //Because we have taken intercept into consideration while sorting we can skip parallel lines
                if (currentLine.getSlope() != previousLine.getSlope())
                {
                    //We add an intersection between previous and current line to the points collection
                    points.add(previousLine.Intersect(currentLine));

                    //If the latest intersection point lies before any of the previous one, we need to update those intersections to find the actual last intersection
                    int lastIntersectionIndex = points.size() - 1;
                    while (points.get(lastIntersectionIndex).getX() < points.get(lastIntersectionIndex - 1).getX())
                    {
                        //Calculate the intersection between current on previous line
                        points.set(lastIntersectionIndex - 1, lines.get(lastIntersectionIndex - 2).Intersect(currentLine));

                        //Remove the no longer valid intersection and line
                        points.remove(lastIntersectionIndex);
                        lines.remove(lastIntersectionIndex - 1);

                        lastIntersectionIndex--;
                    }

                    //We add current line to the lines collection
                    previousLine = currentLine;
                    lines.add(previousLine);
                }
            }
            //We calculate "theoretical" furthest to right point
            points.add(new CartesianCoordinates(_rightBorder, previousLine.Evaluate(_rightBorder)));
            envelope = new Envelope(lines.toArray(new LinearFunction[0]), points.toArray(new CartesianCoordinates[0]));
        }
        return envelope;
    }
}

class  CartesianCoordinates {
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

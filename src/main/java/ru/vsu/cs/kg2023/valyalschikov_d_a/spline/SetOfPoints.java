package ru.vsu.cs.kg2023.valyalschikov_d_a.spline;

import java.util.ArrayList;
import java.util.List;

public class SetOfPoints {
    private final List<PointForSpline> firstSet;
    private final List<PointForSpline> secondSet;
    public SetOfPoints() {
        this.firstSet = new ArrayList<>();
        this.secondSet = new ArrayList<>();
    }
    public void addFirstSet(PointForSpline pt){
        this.firstSet.add(pt);
    }
    public void addSecondSet(PointForSpline pt){
        this.secondSet.add(pt);
    }
    public List<PointForSpline> getFirstSet() {
        return firstSet;
    }
    public List<PointForSpline> getSecondSet() {
        return secondSet;
    }
}

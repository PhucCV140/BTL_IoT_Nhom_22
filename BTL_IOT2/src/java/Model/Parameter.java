/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author XUAN CUONG
 */
public class Parameter {
    private int Min, Max, Height, Radius;

    public Parameter() {
    }

    public Parameter(int Min, int Max, int Height, int Radius) {
        this.Min = Min;
        this.Max = Max;
        this.Height = Height;
        this.Radius = Radius;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int Min) {
        this.Min = Min;
    }

    public int getMax() {
        return Max;
    }

    public void setMax(int Max) {
        this.Max = Max;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int Height) {
        this.Height = Height;
    }

    public int getRadius() {
        return Radius;
    }

    public void setRadius(int Radius) {
        this.Radius = Radius;
    }
    
    
}

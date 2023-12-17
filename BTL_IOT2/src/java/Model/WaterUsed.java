/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author XUAN CUONG
 */
public class WaterUsed {
    private String date;
    private double waterused;

    public WaterUsed() {
    }

    public WaterUsed(String date, double waterused) {
        this.date = date;
        this.waterused = waterused;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getWaterused() {
        return waterused;
    }

    public void setWaterused(Double waterused) {
        this.waterused = waterused;
    }
    
}

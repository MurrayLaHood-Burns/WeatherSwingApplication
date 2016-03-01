package gui_prog1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import org.jfree.data.time.*;

/**
 *
 * @author 7047629
 */
public class Day {
    public String [] date;
    public Minute [] time;
    public double [] temperature;
    public double [] humidity;
    public double [] barometer;
    public double [] windspeed;
    public String [] winddirection; 
    public double [] windgust;
    public double [] windchill;
    public double [] heatindex;
    public double [] uvindex;
    public double [] rainfall;
    public boolean [] valid;
    
    public MinMax mtemp = new MinMax();
    public MinMax mwindSpeed = new MinMax();
    public double avTemp = 0;
    public double avWindSpeed = 0;
    public double totalRain = 0;
    public boolean validDay = false;
    public int dayOfWeek = 0;
    
    public Day()
    {
        date = new String[144];
        time  = new  Minute[144];
        temperature = new double[144];
        humidity = new double[144];
        barometer = new double[144];
        windspeed  = new double[144];
        winddirection  = new String[144]; 
        windgust = new double[144];
        windchill = new double[144];
        heatindex = new double[144];
        uvindex = new double[144];
        rainfall = new double[144];
        valid = new boolean[144];
        //System.out.print("Days Created");
        //System.out.println();
    }

    
}

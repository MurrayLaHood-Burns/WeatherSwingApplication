/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_prog1;

/**
 *
 * @author 7047629
 */
public class Month {
    
    public Day [] days;
    
    public MinMax mtemp = new MinMax();
    public MinMax mwindSpeed = new MinMax();
    public double avTemp = 0;
    public double avWindSpeed = 0;
    public double totalRain = 0;
    
    public Month()
    {
        days = new Day [31];
        //System.out.print("Months Created");
        //System.out.println();
        
    }
}

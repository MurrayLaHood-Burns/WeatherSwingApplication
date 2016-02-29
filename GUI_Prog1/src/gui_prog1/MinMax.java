/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui_prog1;
import org.jfree.data.time.*;


/**
 *
 * @author 7047629
 */
public class MinMax {
    
    public double min = 9999;
    public Minute minDay = new Minute(1,1,1,1,1991);
    public Minute maxDay = new Minute(1,1,1,1,1991);
    public double max = -999;
    public Minute minMinute = new Minute();
    public Minute maxMinute = new Minute();
    
    public MinMax()
    {
        
    }
    
    public void setMinMax(double value, Minute vDay)
    {
        if(value < min)
        {
            min = value;
            minDay = vDay;
        }
        if(value > max)
        {
            max = value;
            minDay = vDay;
        }
    }
    
    public void setMinMax(int value)
    {
        if(value < min)
        {
            min = value;
        }
        if(value > max)
        {
            max = value;
        }
    }
}

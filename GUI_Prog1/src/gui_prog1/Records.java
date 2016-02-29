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
public class Records 
{
   
    public Years []year;
    public int leastYear;
    public int size;
    
    public Records(int lowYear, int highYear)
    {
        leastYear= lowYear;
        size = highYear - lowYear+1;
        year = new Years [size];
        System.out.print( "Least Year is: " + leastYear );
        System.out.println();
        System.out.print( "Year amount: " + size );
        System.out.println();
    }
    
    public int yearIndex(int selectYear)
    {
        return (selectYear - leastYear);
    }
    
    public Day[] getYear(int year)
    {
        int dayIndex = 0;
        Day [] rdays = new Day [12*31];
        //rdays[0] = this.year[0].months[0].days[0]; 
      
        for(int j = 0; j < 12; j++)
        {
            for(int k = 0; k < 31; k++)
            {
                rdays[dayIndex] = this.year[this.yearIndex(year)].months[j].days[k];
                dayIndex++;
            }
            //rdays[i] = years[];
        }
        
        return rdays;     
    }
    
    public  Day[] getMonth(int year, int month)
    {
        int dayIndex = 0;
        Day [] rdays = new Day [31];
     
        for(int k = 0; k < 31; k++)
        {
                rdays[dayIndex] = this.year[this.yearIndex(year)].months[month].days[k];
                dayIndex++;
        }
        return rdays;
    }
    
    public Day[] getWeek(int year, int month, int week)
    {
        int dayIndex = 0;
        Day [] rdays = new Day [7];
     
        for(int k = (7*week); k < (7*week)+7; k++)
        {
                rdays[dayIndex] = this.year[this.yearIndex(year)].months[month].days[k];
                dayIndex++;
        }
        return rdays;
    }
    
    public Day getDay(int year, int month, int day)
    {
        return this.year[yearIndex(year)].months[month].days[day];
    }
    
    public double getYearAvTemp(int year)
    {
        return this.year[yearIndex(year)].avTemp;
    }
    
    public double getMonthAvTemp(int year, int month)
    {
        return this.year[yearIndex(year)].months[month].avTemp;
    }
    
    public double getDayAvTemp(int year, int month, int day)
    {
        return this.year[yearIndex(year)].months[month].days[day].avTemp;
    }
    
    public double getYearAvWind(int year)
    {
        return this.year[yearIndex(year)].avWindSpeed;
    }
    
    public double getMonthAvWind(int year, int month)
    {
        return this.year[yearIndex(year)].months[month].avWindSpeed;
    }
    
    public double getDayAvWind(int year, int month, int day)
    {
        return this.year[yearIndex(year)].months[month].days[day].avWindSpeed;
    }
    
    public MinMax getYearMinMaxTemp(int year)
    {
        return this.year[yearIndex(year)].mtemp;
    }
    
    public MinMax getMonthMinMaxTemp(int year, int month)
    {
        return this.year[yearIndex(year)].months[month].mtemp;
    }
    
    public MinMax getDayMinMaxTemp(int year, int month, int day)
    {
        return this.year[yearIndex(year)].months[month].days[day].mtemp;
    }
    
    public MinMax getYearMinMaxWind(int year)
    {
        return this.year[yearIndex(year)].mwindSpeed;
    }
    
    public MinMax getMonthMinMaxWind(int year, int month)
    {
        return this.year[yearIndex(year)].months[month].mwindSpeed;
    }
    
    public MinMax getDayMinMaxWind(int year, int month, int day)
    {
        return this.year[yearIndex(year)].months[month].days[day].mwindSpeed;
    }
    
    public double getYearRainfall(int year)
    {
        return this.year[yearIndex(year)].totalRain;
    }
    
    public double getMonthRainfall(int year, int month)
    {
        return this.year[yearIndex(year)].months[month].totalRain;
    }
    
    public double getDayRainfall(int year, int month, int day)
    {
        return this.year[yearIndex(year)].months[month].days[day].totalRain;
    }
    
}

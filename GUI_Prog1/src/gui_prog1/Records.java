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
    
    public Day[] getWeekR(int year, int month, int week)
    {
        int dayIndex = 0;
        int tmpYear = year;
        int tmpMonth = month;
        int tmpWeek = week;
        int dayGoal = week*7;
        Day [] rdays = new Day [7];
     
       
        rdays[this.year[year].months[month].days[dayGoal].dayOfWeek - 1] = 
                rdays[dayIndex] = this.year[this.yearIndex(year)].months[month].days[dayGoal];
        
        dayIndex = this.year[year].months[month].days[dayGoal].dayOfWeek - 1;
        for(int k = dayGoal - 1; dayIndex > 0; k--)
        {
            if(k < 0);
            {
                k = 30;
                
                if(tmpMonth<0)
                {
                    if(tmpYear<0)
                        return null;
                    else
                        tmpYear -= 1;
                
                    tmpMonth = 11;
                }
                else
                    tmpMonth -=1;
            }
            
            if(this.year[this.yearIndex(year)].months[month].days[dayGoal].validDay)
            {
                rdays[this.year[year].months[month].days[k].dayOfWeek - 1] = this.year[year].months[month].days[k];
                dayIndex--;
            }
            
        }
        
        dayIndex = this.year[year].months[month].days[dayGoal].dayOfWeek + 1;
        for(int k = dayGoal + 1; dayIndex < 8; k++)
        {
            if(k > 30);
            {
                k = 0;
                
                if(tmpMonth>11)
                {
                    if(tmpYear>=this.size)
                        return null;
                    else
                        tmpYear += 1;
                
                    tmpMonth = 0;
                }
                else
                    tmpMonth +=1;
            }
            
            if(this.year[this.yearIndex(year)].months[month].days[dayGoal].validDay)
            {
                rdays[this.year[year].months[month].days[k].dayOfWeek - 1] = this.year[year].months[month].days[k];
                dayIndex++;
            }
            
        }
        
        /*for(int k = (7*week); k < (7*week)+7; k++)
        {
                rdays[dayIndex] = this.year[this.yearIndex(year)].months[month].days[k];
                dayIndex++;
        }
        
         /*if(this.year[year].months[month].days[dayGoal].dayOfWeek != 1)
        {
            dayGoal -= this.year[year].months[month].days[dayGoal].dayOfWeek - 1;
        }
        
        
        
        if(dayGoal < 0);
        {
            if(month<0)
            {
                if(year<0)
                    return null;
                else
                    year -= 1;
                
                month = 11;
            }
            else
                month -=1;
        }*/
        
        return rdays;
    }
    
    
    public Day getDay(int year, int month, int day)
    {
        return this.year[year].months[month].days[day];
    }
    
    public double getYearAvTemp(int year)
    {
        return this.year[year].avTemp;
    }
    
    public double getMonthAvTemp(int year, int month)
    {
        return this.year[year].months[month].avTemp;
    }
    
    public double getDayAvTemp(int year, int month, int day)
    {
        return this.year[year].months[month].days[day].avTemp;
    }
    
    public double getYearAvWind(int year)
    {
        return this.year[year].avWindSpeed;
    }
    
    public double getMonthAvWind(int year, int month)
    {
        return this.year[year].months[month].avWindSpeed;
    }
    
    public double getDayAvWind(int year, int month, int day)
    {
        return this.year[year].months[month].days[day].avWindSpeed;
    }
    
    public MinMax getYearMinMaxTemp(int year)
    {
        return this.year[year].mtemp;
    }
    
    public MinMax getMonthMinMaxTemp(int year, int month)
    {
        return this.year[year].months[month].mtemp;
    }
    
    public MinMax getDayMinMaxTemp(int year, int month, int day)
    {
        return this.year[year].months[month].days[day].mtemp;
    }
    
    public MinMax getYearMinMaxWind(int year)
    {
        return this.year[year].mwindSpeed;
    }
    
    public MinMax getMonthMinMaxWind(int year, int month)
    {
        return this.year[year].months[month].mwindSpeed;
    }
    
    public MinMax getDayMinMaxWind(int year, int month, int day)
    {
        return this.year[year].months[month].days[day].mwindSpeed;
    }
    
    public double getYearRainfall(int year)
    {
        return this.year[year].totalRain;
    }
    
    public double getMonthRainfall(int year, int month)
    {
        return this.year[year].months[month].totalRain;
    }
    
    public double getDayRainfall(int year, int month, int day)
    {
        return this.year[year].months[month].days[day].totalRain;
    }
    
}

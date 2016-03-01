/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui_prog1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * ChartContainer is a JScrollPane that contains six charts (temp, humidity,
 * barometer, windspeed, UV index, and rainfall). The charts must be initialized
 * with the init( Record record ) function for the charts to be filled with data.
 * Also provides functionality for chart clicks, changing (yearly,monthly,weekly,
 * daily) views, and toggling between next and previous datasets.
 * 
 * @author Murray LaHood-Burns
 */
public final class ChartContainer extends JScrollPane implements ChartMouseListener {
    
    /**
     * 
     */
    public ChartContainer()
    {
        
        // panel initialization
        panel = new JPanel();
        panel.setLayout( new BoxLayout(panel, BoxLayout.Y_AXIS) );
        
        // panel size
        panel.setPreferredSize( new Dimension(900,1800));
        
        // chartpanel initialization
        tempPanel = new ChartPanel(tempChart);
        humPanel = new ChartPanel(humChart);
        baroPanel = new ChartPanel(baroChart);
        windPanel = new ChartPanel(windChart);
        uvPanel = new ChartPanel(uvChart);
        rainPanel = new ChartPanel(rainChart);
        
        // add chartpanels to panel
        panel.add(tempPanel);
        panel.add(humPanel);
        panel.add(baroPanel);
        panel.add(windPanel);
        panel.add(uvPanel);
        panel.add(rainPanel);
        
        // scrollpane settings
        setViewportView(panel);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setPreferredSize( new Dimension(930,630));
    }
    
    public void init( Records input_record )
    {
    
        // record initialization
        record = input_record;
        
        // index initialization
        year_i = 0;
        month_i = 0;
        day_i = 0;
        
        // view initialization
        currView = ViewEnum.YEAR;
        
        // chart initialization
        tempChart = createChart( createYearDataset("Temp", ChartEnum.TEMP),
                "Temperature", "Degrees Fahrenheit");
        humChart = createChart( createYearDataset("Hum", ChartEnum.HUM),
                "Humidity", "Percent");
        baroChart = createChart( createYearDataset("Baro", ChartEnum.BARO),
                "Barometric Pressure", "Inches of Mercury");
        windChart = createChart( createYearDataset("Wind", ChartEnum.WIND),
                "Wind Speed", "Miles Per Hour");
        uvChart = createChart( createYearDataset("UV", ChartEnum.UV),
                "UV Index", "UV Radiation Dose");
        rainChart = createChart( createYearDataset("Rain", ChartEnum.RAIN),
                "Rainfall", "Inches");
        
        setChartPanels();
        
        // add chartmouselisteners
        tempPanel.addChartMouseListener(this);
        humPanel.addChartMouseListener(this);
        baroPanel.addChartMouseListener(this);
        windPanel.addChartMouseListener(this);
        uvPanel.addChartMouseListener(this);
        rainPanel.addChartMouseListener(this);
        
        // header settings
        headerView = new JViewport();
        headerLabel = new JLabel(Integer.toString(record.leastYear+year_i), JLabel.CENTER);
        headerLabel.setFont(new Font("Courier New", Font.BOLD, 24));
        headerView.setBackground(Color.WHITE);
        headerView.setView(headerLabel);
        setColumnHeader( headerView );
    }
    
    public int getYear_i(){
        return year_i;
    }
    
    public int getMonth_i(){
        return month_i;
    }
    
    public int getDay_i(){
        return day_i;
    }
    
    /*
    Initial creation of charts
    */
    private JFreeChart createChart( XYDataset dataset, String title, String unitLabel )
    {
        return ChartFactory.createTimeSeriesChart(
                title,
                "Date/Time",
                unitLabel,
                dataset,
                false,
                true,
                false);
    }
    
    /*
    createYearDataset
    */
    private XYDataset createYearDataset( String title, ChartEnum chartNum )
    {
        TimeSeries series = new TimeSeries(title);
        boolean validDay;
        int month, day;
        Years year = record.year[year_i];
        
        for(month = 0; month < 12; month++){
            validDay = true;
            for( day = 0; day < 31 && validDay == true; day++ ){
                if( year.months[month].days[day].validDay)
                {
                    addDayToSeries(series,year.months[month].days[day], chartNum);
                }
                else
                    validDay = false;
            }
        }
        
        return new TimeSeriesCollection(series);
    }
    
    /*
    createMonthDataset
    */
    private XYDataset createMonthDataset( String title, ChartEnum chartNum )
    {
        TimeSeries series = new TimeSeries(title);
        boolean validDay = true;
        int day;
        Month month = record.year[year_i].months[month_i];
        
        for( day = 0; day < 31 && validDay == true; day++ ){
            if( month.days[day].validDay)
            {
                addDayToSeries(series, month.days[day], chartNum);
            }
            else
                validDay = false;
        }
        
        return new TimeSeriesCollection(series);
    }
    
    /*
    createWeekDataset
    */
    private XYDataset createWeekDataset( String title, ChartEnum chartNum )
    {        
        TimeSeries series = new TimeSeries(title);
        boolean inNextMonth = false;
        int day_offset, i;
        Month currMonth = record.year[year_i].months[month_i];
        Month nextMonth = null;
        Day day = currMonth.days[day_i];
        
        // check if next month is within the record
        if(year_i==record.size-1 && month_i == 11)
        {
            // if not, ignore it
        }
        // if it is, set its value
        else
        {
            // if current month is december, set next to jan of next year
            if( month_i == 11 )
                nextMonth = record.year[year_i+1].months[0];
            // else set it to the next month of current year
            else
                nextMonth = record.year[year_i].months[month_i+1];
        }
        
        day_offset = 0;
        for( i = 0; i < 7; i++ )
        {
            
            // check if we went over the current month
            if( day_i+day_offset == 31 || 
                    !day.validDay)
            {
                System.out.println("Week extending to next month");
                // reset day offest
                day_offset = 0;
                // go to next month if valid
                if(nextMonth!=null){
                    inNextMonth = true;
                    day = nextMonth.days[day_offset];
                }
                // else return the current dataset
                else
                    return new TimeSeriesCollection(series);
            }
            
            addDayToSeries(series,day,chartNum);
            
            // get next day of week
            day_offset++;
            if(inNextMonth){
                if( nextMonth!=null)
                    day = nextMonth.days[day_offset];
            }
            else
                day = currMonth.days[day_i+day_offset];
            
            /*
            System.out.print("chartNum:");
            System.out.print(chartNum);
            System.out.print(" | i:");
            System.out.print(i);
            System.out.print(" | day_i:");
            System.out.print(day_i);
            System.out.print(" | day_offset:");
            System.out.print(day_offset);
            System.out.print(" | inNextMonth:");
            System.out.println(inNextMonth);*/
        }
                
        return new TimeSeriesCollection(series);
    }
    
    /*
    createDayDataset
    */
    private XYDataset createDayDataset( String title, ChartEnum chartNum )
    {        
        TimeSeries series = new TimeSeries(title);
        Day day = record.year[year_i].months[month_i].days[day_i];
        
        addDayToSeries(series, day, chartNum);
        
        return new TimeSeriesCollection(series);
    }
    
    /*
    viewYear
    */
    public void viewYear()
    {
        if( currView == ViewEnum.YEAR )
            return;
        
        currView = ViewEnum.YEAR;
        
        tempChart = createChart( createYearDataset("Temp", ChartEnum.TEMP),
                "Temperature", "Degrees Fahrenheit");
        humChart = createChart( createYearDataset("Hum", ChartEnum.HUM),
                "Humidity", "Percent");
        baroChart = createChart( createYearDataset("Baro", ChartEnum.BARO),
                "Barometric Pressure", "Inches of Mercury");
        windChart = createChart( createYearDataset("Wind", ChartEnum.WIND),
                "Wind Speed", "Miles Per Hour");
        uvChart = createChart( createYearDataset("UV", ChartEnum.UV),
                "UV Index", "UV Radiation Dose");
        rainChart = createChart( createYearDataset("Rain", ChartEnum.RAIN),
                "Rainfall", "Inches");
        
        setChartPanels();
        headerLabel.setText(Integer.toString(record.leastYear + year_i));
    }
    
    /*
    viewMonth
    */
    public void viewMonth()
    {
        currView = ViewEnum.MONTH;
        
        tempChart = createChart( createMonthDataset("Temp", ChartEnum.TEMP),
                "Temperature", "Degrees Fahrenheit");
        humChart = createChart( createMonthDataset("Hum", ChartEnum.HUM),
                "Humidity", "Percent");
        baroChart = createChart( createMonthDataset("Baro", ChartEnum.BARO),
                "Barometric Pressure", "Inches of Mercury");
        windChart = createChart( createMonthDataset("Wind", ChartEnum.WIND),
                "Wind Speed", "Miles Per Hour");
        uvChart = createChart( createMonthDataset("UV", ChartEnum.UV),
                "UV Index", "UV Radiation Dose");
        rainChart = createChart( createMonthDataset("Rain", ChartEnum.RAIN),
                "Rainfall", "Inches");
        
        setChartPanels();
        headerLabel.setText( monthName[month_i] + " " +
                Integer.toString(record.leastYear + year_i));
    }
    
    /*
    viewWeek
    */
    public void viewWeek()
    {
        currView = ViewEnum.WEEK;
        
        tempChart = createChart( createWeekDataset("Temp", ChartEnum.TEMP),
                "Temperature", "Degrees Fahrenheit");
        humChart = createChart( createWeekDataset("Hum", ChartEnum.HUM),
                "Humidity", "Percent");
        baroChart = createChart( createWeekDataset("Baro", ChartEnum.BARO),
                "Barometric Pressure", "Inches of Mercury");
        windChart = createChart( createWeekDataset("Wind", ChartEnum.WIND),
                "Wind Speed", "Miles Per Hour");
        uvChart = createChart( createWeekDataset("UV", ChartEnum.UV),
                "UV Index", "UV Radiation Dose");
        rainChart = createChart( createWeekDataset("Rain", ChartEnum.RAIN),
                "Rainfall", "Inches");
        
        setChartPanels();
        
        String weekStart = monthName[month_i] + " " +
                Integer.toString(day_i+1) + ", " +
                Integer.toString(record.leastYear + year_i);
        
        headerLabel.setText( "Week starting on " + weekStart);
    }
    
    /*
    viewDay
    */
    public void viewDay()
    {
        currView = ViewEnum.DAY;
        
        tempChart = createChart( createDayDataset("Temp", ChartEnum.TEMP),
                "Temperature", "Degrees Fahrenheit");
        humChart = createChart( createDayDataset("Hum", ChartEnum.HUM),
                "Humidity", "Percent");
        baroChart = createChart( createDayDataset("Baro", ChartEnum.BARO),
                "Barometric Pressure", "Inches of Mercury");
        windChart = createChart( createDayDataset("Wind", ChartEnum.WIND),
                "Wind Speed", "Miles Per Hour");
        uvChart = createChart( createDayDataset("UV", ChartEnum.UV),
                "UV Index", "UV Radiation Dose");
        rainChart = createChart( createDayDataset("Rain", ChartEnum.RAIN),
                "Rainfall", "Inches");
        
        setChartPanels();
        
        headerLabel.setText( 
                record.year[year_i].months[month_i].days[day_i].time[0].getDay().toString()
        );
    }
    
    /*
    nextDataset
    */
    public void nextDataset()
    {
        if(null != currView)
        switch (currView) {
            case YEAR:
                if(!incrementYear_i())
                    return;
                viewYear();
                break;
            case MONTH:
                if(!incrementMonth_i())
                    return;
                viewMonth();
                break;
            case WEEK:
                // attempt to increment day_i by 7
                for( int i = 0; i < 7; i++ ){
                    if(!incrementDay_i())
                        return;
                }
                viewWeek();
                break;
            case DAY:
                if(!incrementDay_i())
                    return;
                viewDay();
                break;
            default:
                System.out.println("currView corrupted");
                break;
        }
    }
    
    /*
    prevDataset
    */
    public void prevDataset()
    {
        if(null != currView)
        switch (currView) {
            case YEAR:
                if(!decrementYear_i())
                    return;
                viewYear();
                break;
            case MONTH:
                if(!decrementMonth_i())
                    return;
                viewMonth();
                break;
            case WEEK:
                // attempt to decrement day 7 times
                for( int i = 0; i < 7; i++){
                    if(!decrementDay_i())
                        return;
                }
                viewWeek();
                break;
            case DAY:
                if(!decrementDay_i())
                    return;
                viewDay();
                break;
            default:
                System.out.println("currView corrupted");
                break;
        }
    }
    
    /*
    selectedDataset
    */
    public boolean selectedDataset( int year, int month, int day )
    {
        // set input to array indexes
        year = year - record.leastYear;
        month--;
        day--;
        
        // if it is a valid day, change internal indexes
        if(!record.year[year].months[month].days[day].validDay)
            return false;
        
        year_i = year;
        month_i = month;
        day_i = day;
        
        return true;
    }
    
    private void addDayToSeries( TimeSeries series, Day day, ChartEnum chartNum)
    {
        int time;
        Minute currMinute;
        boolean validTime = true;
        for( time = 0; time < 144 && validTime; time++ ){
            if( day.valid[time] )
            {
                currMinute = day.time[time];
                if( null != chartNum)
                switch (chartNum) {
                    case TEMP:
                        series.add( currMinute,
                                day.temperature[time] );
                        break;
                    case HUM:
                        series.add( currMinute,
                                day.humidity[time]);
                        break;
                    case BARO:
                        series.add( currMinute,
                                day.barometer[time]);
                        break;
                    case WIND:
                        series.add( currMinute,
                                day.windspeed[time]);
                        break;
                    case UV:
                        series.add( currMinute,
                                day.uvindex[time]);
                        break;
                    case RAIN:
                        series.add( currMinute,
                                day.rainfall[time]);
                        break;
                    default:
                        System.out.println("Invalid ChartNum(enum)");
                        break;
                }
            }
            else
                validTime = false;
        }
    }
    
    /*
    setChartPanels
    */
    private void setChartPanels()
    {
        tempPanel.setChart(tempChart);
        humPanel.setChart(humChart);
        baroPanel.setChart(baroChart);
        windPanel.setChart(windChart);
        uvPanel.setChart(uvChart);
        rainPanel.setChart(rainChart);
    }
    
    private boolean incrementYear_i(){
        // if end of record, return
        if(year_i == record.size-1)
        {
            System.out.println("Record End");
            return false;
        }
        // else view next year
        year_i++;
        // reset lower indexes
        month_i = 0;
        day_i = 0;
        return true;
    }
    
    private boolean incrementMonth_i(){
        // if last month of year
        if(month_i == 11)
        {
            // go to next year
            if(!incrementYear_i())
                return false;
        }
        // else go to next month
        else
        {
            month_i++;
            // reset lower indexes
            day_i = 0;
        }
        return true;
    }
    
    private boolean incrementDay_i(){
        // increment day index
        day_i++;
        // if day is past current month
        if( day_i == 31 || 
                !record.year[year_i].months[month_i].days[day_i].validDay)
        {
            
            if(!incrementMonth_i())
                return false;
        }
        return true;
    }
    
    private boolean decrementYear_i(){
        // if beginning of record, return
        if(year_i == 0)
        {
            System.out.println("Record Start");
            return false;
        }
        // else view start of prev year
        year_i--;
        month_i = 0;
        day_i = 0;
        return true;
    }
    
    private boolean decrementMonth_i(){
        // if first month of year
        if(month_i == 0)
        {
            // attempt to go to previous year
            if(!decrementYear_i())
                return false;
        }
        // else go to prev month
        else
        {
            month_i--;
            // reset lower indexes
            day_i = 0;
        }
        return true;
    }
    
    private boolean decrementDay_i(){
        // if first day of month
        if(day_i == 0)
        {
           if(!decrementMonth_i())
               return false;

            // go to last valid day of month
            day_i = 30;
            while(!record.year[year_i].months[month_i].days[day_i].validDay)
                day_i--;
        }
        // else go to previous day
        day_i--;
        return true;
    }
    
    private int getTimeIndex(Day day, Minute key, int imin, int imax)
    {
        if(imax<imin)
            return-1;
        else
        {
            int imid = (imin+imax)>>>1;
            Minute currMinute = day.time[imid];
            
            if(currMinute == null || currMinute.compareTo(key) > 0)
                return getTimeIndex(day,key,imin,imid-1);
            else if(currMinute.compareTo(key) < 0)
                return getTimeIndex(day,key,imid+1,imax);
            else
                return imid;
        }
    }
    
    @Override
    public void chartMouseClicked(ChartMouseEvent cme) {
        
        String tooltip = cme.getEntity().getToolTipText();
        String date = tooltip.substring(tooltip.indexOf("(")+1, tooltip.indexOf("M")+1);
        int month = Integer.parseInt(date.substring(0, date.indexOf("/")));
        int day = Integer.parseInt(date.substring(date.indexOf("/")+1, date.lastIndexOf("/")));
        int year = Integer.parseInt(date.substring(date.lastIndexOf("/")+1, date.indexOf(" ")));
        int hour = Integer.parseInt(date.substring(date.indexOf(" ")+1,date.indexOf(":")));
        int min = Integer.parseInt(date.substring(date.indexOf(":")+1,date.lastIndexOf(" ")));
        
        char meridian = date.charAt(date.indexOf("M")-1);
        
        if( meridian == 'P' )
            hour+=12;
        
        System.out.println(month);
        System.out.println(day);
        System.out.println(year);
        System.out.println(hour);
        System.out.println(min);
        
        Minute minute = new Minute(min,hour,day,month,year+2000);
        
        year = year - (record.leastYear - 2000);
        day--;
        month--;
        
        Day currDay = record.year[year].months[month].days[day];
        int index = getTimeIndex( currDay, minute, 0, 143 );
        
        System.out.println(index);
        
        if(index == -1)
            return;
        
        JOptionPane.showMessageDialog(null, date + "\n" +
                "Temperature (F): " + Double.toString(currDay.temperature[index]) + "\n" +
                "Humidity (%): " + Double.toString(currDay.humidity[index]) + "\n" +
                "Barometric Pressure (in.): " + Double.toString(currDay.barometer[index]) + "\n" +
                "Wind Speed (mph): " + Double.toString(currDay.windspeed[index]) + "\n" +
                "Wind Direction: " + currDay.winddirection[index]+ "\n" +
                "Wind Gust (mph): " + Double.toString(currDay.windgust[index]) + "\n" +
                "Wind Chill (F): " + Double.toString(currDay.windchill[index]) + "\n" +
                "Heat Index: " + Double.toString(currDay.heatindex[index]) + "\n" +
                "UV Index " + Double.toString(currDay.uvindex[index]) + "\n" +
                "Rainfall: " + Double.toString(currDay.rainfall[index]) + "\n");
        
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent cme) {
    }
   
    // Variable declarations
    private final JPanel panel;
    
    private Records record;

    private enum ChartEnum{
        TEMP, HUM, BARO, WIND, UV, RAIN
    }
    
    private enum ViewEnum{
        YEAR, MONTH, WEEK, DAY
    }
    
    private ViewEnum currView;
    
    private int year_i;
    private int month_i;
    private int day_i;
    
    private Minute currMin;
    
    private JFreeChart tempChart;
    private JFreeChart humChart;
    private JFreeChart baroChart;
    private JFreeChart windChart;
    private JFreeChart uvChart;
    private JFreeChart rainChart;
    
    private final ChartPanel tempPanel;
    private final ChartPanel humPanel;
    private final ChartPanel baroPanel;
    private final ChartPanel windPanel;
    private final ChartPanel uvPanel;
    private final ChartPanel rainPanel;
    
    private JViewport headerView;
    private JLabel headerLabel;
    
    private final String[] monthName = {
        "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December" };
}
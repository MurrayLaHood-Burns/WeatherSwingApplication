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
//public class ReadXMLFile {
    
    /*

    **** ReadXML2.java - read and dump contents of an XML file ****

Illustrates use of JDOM to parse an XML file.
This version will dive into XML tree structure.

Usage:
javac -cp .:jdom.jar ElementLister.java         (use ; instead of : on Windoze)
java  -cp .:jdom.jar ElementLister file.xml     (use ; instead of : on Windoze)

Based on Java example in Processing XML with Java (Elliotte Harold).
For more info, see e.g. https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html

JMW 160205
*/

import java.awt.Dimension;
import java.io.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jfree.data.time.*;
import java.io.IOException;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ReadXMLFile
{
    public static Records readXMLFile( String directory )
    {
        File folder = new File(directory);
        //File folder = new File("Weather_Records");
        File[] listOfFiles = folder.listFiles();
        int tempDate = 0;
        int tempYear = 0;
        int tempMonth = 0;
        int lowYear = 9999;
        int highYear = 0;
        int yearAmount = 0;
        String str;

            for (int i = 0; i < listOfFiles.length; i++) 
            {
                if (listOfFiles[i].isFile()) 
                {
                    //yearAmount++;
                    str = listOfFiles[i].getName().substring(0,4);
                    tempDate = Integer.parseInt(str);
                    
                    //System.out.print( str );
                    //System.out.println();
                    
                    //str = listOfFiles[i].getName().substring(5,7);
                    //tempMonth = Integer.parseInt(str);
                    //System.out.print( tempMonth );
                    //System.out.println();
                    if(tempDate < lowYear)
                    {
                        lowYear = tempDate;
                    }
                    if(tempDate > highYear)
                    {
                        highYear = tempDate;
                    }
                    
                    
                    //System.out.println("File " + listOfFiles[i].getName());
                } 
            }
            Records temp = new Records(lowYear, highYear);
            
            
            for(int k = 0; k< temp.size; k++)
            {
                temp.year[k] = new Years();
                for(int i = 0; i< 12; i++)
                {
                    temp.year[k].months[i] = new Month(); 
                    for(int j = 0; j< 31; j++)
                    {
                        temp.year[k].months[i].days[j] = new Day();
            
                    }
            
                }
            }
            System.out.println("Amount of Files: " + listOfFiles.length);
            System.out.println();
            for (int i = 0; i < listOfFiles.length; i++) 
            {
                if (listOfFiles[i].isFile()) 
                {
                    
                    
                    //yearAmount++;
                    str = listOfFiles[i].getName().substring(0,4);
                    tempYear = Integer.parseInt(str);
                    
                    System.out.print( tempYear );
                    System.out.println();
                    
                    str = listOfFiles[i].getName().substring(5,7);
                    tempMonth = Integer.parseInt(str);
                    
                    readingXML(temp.year[tempYear - temp.leastYear].months[tempMonth-1], listOfFiles[i].getName());
                    
                    temp.year[tempYear - temp.leastYear].avTemp += temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].avTemp;
                    temp.year[tempYear - temp.leastYear].avWindSpeed += temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].avWindSpeed;
                    
                    
                    
                    
                    temp.year[tempYear - temp.leastYear].mwindSpeed.
                            setMinMax(temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mwindSpeed.max, 
                                    temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mwindSpeed.maxDay);
                    
                    temp.year[tempYear - temp.leastYear].mwindSpeed.
                            setMinMax(temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mwindSpeed.min, 
                                    temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mwindSpeed.minDay);
                    
                    temp.year[tempYear - temp.leastYear].mtemp.
                            setMinMax(temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mtemp.max, 
                                    temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mtemp.maxDay);
                    
                    temp.year[tempYear - temp.leastYear].mtemp.
                            setMinMax(temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mtemp.min, 
                                    temp.year[tempYear - temp.leastYear].
                                    months[tempMonth-1].mtemp.maxDay);
                    
                    temp.year[tempYear - temp.leastYear].totalRain +=
                            temp.year[tempYear - temp.leastYear].
                            months[tempMonth-1].totalRain;
                    
                    //System.out.println("File " + listOfFiles[i].getName());
                } 
            }
            
            for (int i = 0; i < temp.size ; i++) 
            {
                temp.year[i].avTemp /= 12;
                temp.year[i].avWindSpeed /= 12;
            }
            
            System.out.println("Size: " + temp.size);
            
            for(int k = 0; k< temp.size; k++)
            {
                for(int i = 0; i< 12; i++)
                {
                    for(int j = 0; j< 31; j++)
                    {
                        if(temp.year[k].months[i].days[j].valid[0]==true){
                            System.out.println(" Months: "+ (i+1) +" Day: "+ j + " Years: "+k);
                            System.out.println("Date: "+temp.year[k].months[i].days[j].time[0].getDay());
                    /*    System.out.println("Min Temp Day: "+temp.year[k].months[i].days[j].mtemp.min);
                        System.out.println("RainFall Year: "+temp.year[k].months[i].totalRain);
                        System.out.println("Min Temp Month: "+temp.year[k].months[i].mtemp.min);
                        System.out.println("RainFall Year: "+temp.year[k].months[i].totalRain);
                        System.out.println("Min Temp Year: "+temp.year[k].mtemp.min);
                        System.out.println("RainFall Year: "+temp.year[k].totalRain);*/
                            System.out.println("Av Temp Day: "+temp.year[k].months[i].days[j].avTemp);
                            System.out.println("Av Temp Month: "+temp.year[k].months[i].avTemp);
                            System.out.println("Av Temp Year: "+temp.year[k].avTemp);
                            
                            System.out.println("Av Wind Day: "+temp.year[k].months[i].days[j].avWindSpeed);
                            System.out.println("Av Wind Month: "+temp.year[k].months[i].avWindSpeed);
                            System.out.println("Av Wind Year: "+temp.year[k].avWindSpeed);
                            
                            System.out.println("DW Day: "+temp.year[k].months[i].days[j].dayOfWeek);
                            System.out.println();

                        System.out.println();}
                        
                        //for(int indexX = 0; indexX < 96 &&temp.year[2].months[i].days[j].valid; indexX++)
                        //    System.out.println("Date: "+temp.year[2].months[i].days[j].date[indexX]);
            
                    }
            
                }
            }
            
           
            //temp
            
            return temp;
            
    }

    // print XML tags and leaf node values
    public static void listChildren( Element current, int depth )
    {
        System.out.print( current.getName()+"hi" );
	// get children of current node
        List children = current.getChildren();
        Iterator iterator = children.iterator();
        //System.out.print( current.getChildText("time"));
        // print node name and leaf node value, indented one space per level in XML tree
        printSpaces( depth );
        
        if ( !iterator.hasNext() )
            System.out.print( " = " + current.getValue() );
        System.out.println();

        // recursively process each child node
        while ( iterator.hasNext() )
        {
            Element child = ( Element ) iterator.next();
            listChildren( child, depth + 1 );
        }
    }
    
    private static void setDay(Element child, Month temp, int indexX, int indexY)
    {
        /*System.out.print( " = " + child.getChildText("date") );
        System.out.println();
        System.out.print(indexZ + " " + indexY + " " + indexX);
        System.out.println();
        */
        temp.days[indexY].date[indexX] = child.getChildText("date");
        
        
        convertToMinute(child.getChildText("date"), child.getChildText("time"), temp.days[indexY], indexX );
        
        temp.days[indexY].temperature[indexX] = Double.parseDouble(child.getChildText("temperature"));
        temp.days[indexY].humidity[indexX] = Double.parseDouble(child.getChildText("humidity"));
        temp.days[indexY].barometer[indexX] = Double.parseDouble(child.getChildText("barometer") );
        temp.days[indexY].windspeed[indexX] = Double.parseDouble(child.getChildText("windspeed") );
        temp.days[indexY].winddirection[indexX] = child.getChildText("winddirection");
        temp.days[indexY].windgust[indexX] = Double.parseDouble(child.getChildText("windgust") );
        temp.days[indexY].windchill[indexX] = Double.parseDouble(child.getChildText("windchill") );
        temp.days[indexY].heatindex[indexX] = Double.parseDouble(child.getChildText("heatindex") );
        temp.days[indexY].uvindex[indexX] = Double.parseDouble(child.getChildText("uvindex") );
        temp.days[indexY].rainfall[indexX] = Double.parseDouble(child.getChildText("rainfall") );
        
        temp.days[indexY].avTemp += temp.days[indexY].temperature[indexX];
        temp.days[indexY].avWindSpeed += temp.days[indexY].windspeed[indexX];
        temp.days[indexY].totalRain += temp.days[indexY].rainfall[indexX];
        temp.days[indexY].mtemp.setMinMax(temp.days[indexY].temperature[indexX], temp.days[indexY].time[indexX]);
        temp.days[indexY].mwindSpeed.setMinMax(temp.days[indexY].temperature[indexX], temp.days[indexY].time[indexX]);
        temp.days[indexY].valid [indexX] = true;
        temp.days[indexY].validDay = true;
        
    }

    // indent to show hierarchical structure of XML tree
    private static void printSpaces( int n )
    {
        for ( int i = 0; i < n; i++ )
        {
            System.out.print( " " );
        }
    }
    
    public static void readingXML(Month temp, String filename)
    {
        int indexX = 0;
        int indexY = 0;
        int ref_index = 0;
        
        int month = 0;
        int day = 0;    
        int year = 0;
        String str;
        
        //int indexY = 0;
        // read and parse XML document
        SAXBuilder builder = new SAXBuilder();
        try
        {
            Document doc = builder.build( ("data/" + filename) );// parse XML tags
            //Document doc = builder.build( ("Weather_Records/" + filename) );// parse XML tags
            Element root = doc.getRootElement();
            // get root of XML tree
            
            //Element center = root.get
            List children = root.getChildren();
            Iterator iterator = children.iterator();
            Element child = ( Element ) iterator.next();
            
            setDay(child,temp,indexX,indexY);
            child = ( Element ) iterator.next();
            
            //Doesnt run through last value
            while(iterator.hasNext())
            {
                child = ( Element ) iterator.next();
                indexX++;
                str = child.getChildText("date");
                
                
                /*System.out.print("Temp Previous Date: " + temp.months[indexZ].days[indexY].date[indexX-1] 
                        + " XML date: " + str);
                System.out.println();*/
                
                //System.out.println()
                
                if(!Objects.equals(temp.days[indexY].date[indexX-1], str))
                {
                    temp.mwindSpeed.setMinMax(temp.days[indexY].mwindSpeed.max, temp.days[indexY].mwindSpeed.maxDay);
                    temp.mwindSpeed.setMinMax(temp.days[indexY].mwindSpeed.min, temp.days[indexY].mwindSpeed.minDay);
                    temp.mtemp.setMinMax(temp.days[indexY].mtemp.max, temp.days[indexY].mtemp.maxDay);
                    temp.mtemp.setMinMax(temp.days[indexY].mtemp.min, temp.days[indexY].mtemp.minDay);
                    temp.totalRain += temp.days[indexY].totalRain;
                    
                    temp.days[indexY].avTemp /= indexX;
                    temp.days[indexY].avWindSpeed /= indexX;
                    temp.avTemp += temp.days[indexY].avTemp;
                    temp.avWindSpeed += temp.days[indexY].avWindSpeed;
                    
                    ref_index = temp.days[indexY].date[0].indexOf('/');
        
                    month = Integer.parseInt( temp.days[indexY].date[0].substring( ref_index-2, ref_index ));
                    day = Integer.parseInt( temp.days[indexY].date[0].substring( ref_index+1, ref_index+3 ));
        
                    // add the "20" in 2000
                    year = Integer.parseInt( "20" + temp.days[indexY].date[0].substring( ref_index+4, ref_index+6 ));
                    
                    Calendar c = Calendar.getInstance();
                    c.set(year, month, day);
                    temp.days[indexY].dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    
                    indexY++;
                    indexX = 0;
                
                }
                

                
                setDay(child,temp,indexX,indexY);
                //iterator.
                //System.out.print( " = " + child.getValue());
                //System.out.print( " = " + child.getChildText("date") );
                //System.out.println();
//            System.out.print( " = " + child.getChildText("time") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("temperature") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("humidity") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("barometer") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("windspeed") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("winddirection") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("windgust") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("windchill") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("heatindex") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("uvindex") );
//            System.out.println();
//            System.out.print( " = " + child.getChildText("rainfall") );
//            System.out.println();
            
            }
            temp.days[indexY].avTemp /= indexX;
            temp.days[indexY].avWindSpeed /= indexX;
            
            temp.avTemp += temp.days[indexY].avTemp;
            temp.avTemp /= indexY;
            
            temp.avWindSpeed += temp.days[indexY].avWindSpeed;
            temp.avWindSpeed /= indexY;
            ref_index = temp.days[indexY].date[0].indexOf('/');
        
            month = Integer.parseInt( temp.days[indexY].date[0].substring( ref_index-2, ref_index ));
            day = Integer.parseInt( temp.days[indexY].date[0].substring( ref_index+1, ref_index+3 ));
        
            // add the "20" in 2000
            year = Integer.parseInt( "20" + temp.days[indexY].date[0].substring( ref_index+4, ref_index+6 ));
                 
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            temp.days[indexY].dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                
            //child = ( Element ) iterator.next();
            //if ( !iterator.hasNext() )
            //    System.out.print( " = " + child.getValue() );
            //    System.out.println();
            
            
            //listChildren( root, 0 );
            // print info in XML tree
        }
        // JDOMException indicates a well-formedness error
        catch ( JDOMException e )
        {
            //System.out.println( args[0] + " is not well-formed." );
            System.out.println(indexX);
            System.out.println( e.getMessage() );
            
        }
        catch ( IOException e )
        {
            System.out.println( " It no work now. " );
            System.out.println( e );
        }
    }
    /************************************************************************
   Function: convertToMinute
   Author: Murray LaHood-Burns
   Description: Converts date "MM/DD/YY" and time "HH:MM(A|P)" to a 
   * Minute.
   Parameters:
   * [In] date - should be in format "MM/DD/YY"
   * [In] time - should be in format "HH:MM(A|P)", where A is A.M. and
   *                P is P.M.
   * Returns a Minute set to the date and time values.
 ************************************************************************/
    public static /*Minute*/ void convertToMinute( String date, String time, Day data, int indexX )
    {
        int year;
        int month;
        int day;
        int hours;
        int minutes;
        int ref_index = 0;
        
        /*System.out.println("No Space");
        System.out.println(date);
        System.out.println(time);*/
        
        ref_index = date.indexOf('/');
        
        month = Integer.parseInt( date.substring( ref_index-2, ref_index ));
        day = Integer.parseInt( date.substring( ref_index+1, ref_index+3 ));
        
        // add the "20" in 2000
        year = Integer.parseInt( "20" + date.substring( ref_index+4, ref_index+6 ));
        
        ref_index = time.indexOf(':');
        
        if(time.length() == 8)
            hours = Integer.parseInt(time.substring( ref_index-2, ref_index ));
        else
            hours = Integer.parseInt(time.substring( ref_index-1, ref_index));
        
        if(hours == 12)
            hours=0;
        
        // add 12 hours if the hour is in the PM
        if( time.charAt(ref_index+3) == 'P' )
            hours += 12;
        
        minutes = Integer.parseInt(time.substring( ref_index+1, ref_index+3));
        
        data.time[indexX] = new Minute( minutes, hours, day, month, year );
        
        //return new Minute( minutes, hours, day, month, year );
        
//        /return min;
    }
    
}
    


package com.coder.boot;

import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class ApplicationTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );

        long nowTime = 1664207184090L;
        long endTime = 1664121599000L;
        //System.out.println(endTime-nowTime);

        System.out.println(24*60*60*1000);
        //System.out.println(nowTime-1664035200000L%28800000);
        System.out.println(nowTime%86400000+24*60*60*1000-1000);

        //1970-01-01 00:00:00 -28800000
        //1970-01-02 00:00:00  57600000
    }

}

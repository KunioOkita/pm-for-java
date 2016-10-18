/*****************************************************************************
 * Copyright (C)2015, NTT Corporation. All rights reserved.
 *****************************************************************************/

/**
 *
 */
package com.chocokunio.pm;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class PerformanceMeasurerTest {

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * コンストラクタテスト
     */
    @Test
    public void PerformanceMeasurer_constructor_001() {
        String id = "PerformanceMeasurer_constructor_001";
        PerformanceMeasurer pfm = new PerformanceMeasurer(id, PerformanceMeasurer.UNIT_NANOSEC);
        assertThat(pfm.getMid(), is(id));
        assertThat(pfm.getMeasurUnit(), is(PerformanceMeasurer.UNIT_NANOSEC));
    }

    /**
     * コンストラクタテスト
     */
    @Test
    public void PerformanceMeasurer_constructor_002() {
        String id = "PerformanceMeasurer_constructor_001";
        PerformanceMeasurer pfm = new PerformanceMeasurer(id);
        assertThat(pfm.getMid(), is(id));
        assertThat(pfm.getMeasurUnit(), is(PerformanceMeasurer.UNIT_MSEC));
    }

    /**
     * startメソッド
     */
    @Test
    public void PerformanceMeasurer_start_001() {
        String id = "PerformanceMeasurer_start_001";
        PerformanceMeasurer pfm = new PerformanceMeasurer(id);
        pfm.start();
        long localStime = System.currentTimeMillis();
        long stime = pfm.getStartTime();
        assertThat(stime, is(greaterThanOrEqualTo(localStime)));
        pfm.stop();
        pfm.showRecord();
    }

    /**
     * splitメソッド
     * @throws InterruptedException
     */
    @Test
    public void PerformanceMeasurer_split_001() throws InterruptedException {
        String id = "PerformanceMeasurer_split_001";
        PerformanceMeasurer pfm = new PerformanceMeasurer(id);
        pfm.start();
        long localStime = System.currentTimeMillis();
        long stime = pfm.getStartTime();
        assertThat(stime, is(greaterThanOrEqualTo(localStime)));
        pfm.split("1回目");
        Thread.sleep(100);
        pfm.split("2回目");
        pfm.stop();
        List<Record> result = pfm.getRecord();
        assertThat(result.size(), is(3));
        assertThat(result.get(2).getTime(), is(greaterThanOrEqualTo(100L)));
        pfm.showRecord();
    }

    /**
     * splitメソッド
     * @throws InterruptedException
     */
    @Test
    public void PerformanceMeasurer_split_002() throws InterruptedException {
        String id = "PerformanceMeasurer_split_002";
        PerformanceMeasurer pfm = new PerformanceMeasurer(id, PerformanceMeasurer.UNIT_NANOSEC);
        pfm.start();
        long localStime = System.currentTimeMillis();
        long stime = pfm.getStartTime();
        assertThat(stime, is(greaterThanOrEqualTo(localStime)));
        pfm.split("1回目");
        Thread.sleep(101);
        pfm.split("2回目");
        pfm.stop();
        List<Record> result = pfm.getRecord();
        assertThat(result.size(), is(3));
        assertThat(result.get(2).getTime(), is(greaterThanOrEqualTo(100000000L)));
        pfm.showRecord();
    }
}

package com.chocokunio.pm;

import java.io.File;
import java.util.ArrayList;

/**
 * 処理速度測定ライブラリ
 * 
 * @author kunio
 *
 */
public class PerformanceMeasurer {

    /** 単位ミリ秒 */
    public static final int UNIT_MSEC = 1;

    /** 単位ナノ秒 */
    public static final int UNIT_NANOSEC = 2;

    /** 時間の出力 */
    public static final int CONSOLE = 1;

    /** 計測ID */
    private String mid;

    /** 計測単位 */
    private int measurUnit = UNIT_MSEC;

    /** 計測開始時刻 */
    private long startTime = 0;

    /** 計測停止時刻 */
    private long endTime = 0;

    /** 途中計測時刻 */
    private ArrayList<Long> splits = new ArrayList<Long>();

    /** 記録格納マップ */
    private ArrayList<Record> records = new ArrayList<Record>();

    /**
     * コンストラクタ
     * 
     * @param unit
     *            計測単位
     */
    public PerformanceMeasurer(String id, int unit) {
        mid = id;
        measurUnit = unit;
    }

    /**
     * コンストラクタ
     * 
     * @param unit
     *            計測単位
     */
    public PerformanceMeasurer(String id) {
        mid = id;
    }

    /**
     * 計測開始
     */
    public void start() {
        startTime = getNowTime();
    }

    /**
     * 計測開始
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 途中経過時刻を記録する
     * 
     */
    public void split() {
        final long now = getNowTime();
        splits.add(now);
    }

    /**
     * 計測時刻リストを取得する
     * 
     * @return
     */
    public ArrayList<Long> getSplits() {
        return splits;
    }

    /**
     * 計測終了
     */
    public void stop() {
        long t = getNowTime();
        endTime = t;
        splits.add(t);
    }

    /**
     * 計測時間出力
     */
    public void showRecord() {
        createRecord();
        System.out.println(String.format("\n[計測結果 : %s, 計測開始時間 : %d]", mid,
                startTime));
        System.out.println("ID\t時間\t\t経過時間\t\t差分");
        for (int i = 0; i < records.size(); i++) {
            Record r = records.get(i);
            System.out.println(String.format("%s\t%d\t\t%d", r.getId(),
                    r.getClock(), r.getTime()));
        }
    }

    /**
     * 計測時間をCSV形式で取得する。
     */
    public void outputCSV(File file) {

    }

    /**
     * 途中経過時間取得
     * 
     * @return 途中経過時間
     */
    public long getElapsedTime() {
        return calcElapsedTime();
    }

    /**
     * 経過時間計算
     * 
     * @return 経過時間
     */
    private long calcElapsedTime() {
        final long now = getNowTime();
        return now - startTime;
    }

    /**
     * 現在時刻取得
     * 
     * @return
     */
    private long getNowTime() {
        long result = 0;

        switch (measurUnit) {
        case UNIT_NANOSEC:
            result = System.nanoTime();
            break;
        case UNIT_MSEC:
        default:
            result = System.currentTimeMillis();
            break;
        }

        return result;
    }

    /**
     * 計測時間の記録を計算する。
     */
    private void createRecord() {
        for (int i = 0; i < splits.size(); i++) {
            long split = splits.get(i);
            long t = split - startTime;
            Record r = new Record();

            r.setId(String.valueOf(i + 1));
            r.setClock(split);
            r.setTime(t);
            records.add(r);
        }
    }
}

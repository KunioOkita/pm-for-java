package com.chocokunio.pm;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /** 記録タイトル　*/
    private static final String RECORD_TITLE_TPL = "[計測結果 : %s, 計測開始時間(%s) : %d]";

    /** 記録タイトル行 */
    private static final String RECORD_HEAD_LINE = "ID\t計測箇所\t時間\t\t経過時間";

    /** 記録表示行 */
    private static final String RECORD_LINE_TPL = "%s\t%s\t%d\t\t%d";

    private static final String UNIT_MSEC_STR = "msec";

    private static final String UNIT_NSEC_STR = "nsec";

    private String unitName = UNIT_MSEC_STR;

    /** 計測ID */
    private String mid;

    /** 計測単位 */
    private int measurUnit = UNIT_MSEC;

    /** 計測開始時刻 */
    private long startTime = 0;

    /** 計測停止時刻 */
    private long endTime = 0;

    /** 途中計測時刻 */
    private Map<String, Long> splits = new LinkedHashMap<String, Long>();

    /** 記録格納マップ */
    private List<Record> records = new ArrayList<Record>();

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
     * 計測ID取得
     * @return 計測ID
     */
    public String getMid() {
        return this.mid;
    }

    /**
     * 計測単位取得
     * @return 計測単位
     */
    public int getMeasurUnit() {
        return this.measurUnit;
    }

    /**
     * 計測開始
     */
    public void start() {
        startTime = getNowTime();
    }

    /**
     * 計測開始時刻取得
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 計測停止時刻取得
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * 途中経過時刻を記録する
     *
     * @param place 計測箇所
     *
     */
    public void split(String place) {
        final long now = getNowTime();
        if (place == null || place.isEmpty()) {
        	splits.put("", now);
        } else {
        	splits.put(place, now);
        }
    }

    /**
     * 計測時刻リスト取得.
     *
     * @return 計測時刻リスト
     */
    public Map<String, Long> getSplits() {
        return splits;
    }

    /**
     * 計測終了.
     */
    public void stop() {
        endTime = getNowTime();
        splits.put("end", endTime);
        createRecord();
    }

    /**
     * 計測結果リスト取得.
     * @return
     */
    public List<Record> getRecord() {
    	return records;
    }

    /**
     * 計測時間出力
     */
    public void showRecord() {
        System.out.println(String.format(RECORD_TITLE_TPL, mid,
        		unitName, startTime));
        System.out.println(RECORD_HEAD_LINE);
        for (int i = 0; i < records.size(); i++) {
            Record r = records.get(i);
            System.out.println(String.format(RECORD_LINE_TPL, r.getId(),
                    r.getPlace(), r.getClock(), r.getTime()));
        }
    }

    /**
     * 計測時間をCSV形式で取得する。
     */
    public void outputCSV(File file) {
    	// out csv.
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
            unitName = UNIT_NSEC_STR;
            break;
        case UNIT_MSEC:
        default:
            result = System.currentTimeMillis();
            unitName = UNIT_MSEC_STR;
            break;
        }

        return result;
    }

    /**
     * 計測時間の記録を計算する。
     */
    private void createRecord() {
    	int i = 0;
    	for(Map.Entry<String, Long> e : splits.entrySet()) {
            long split = e.getValue();
            long t = split - startTime;

            Record r = new Record();
            r.setId(String.valueOf(i + 1));
            r.setPlace(e.getKey());
            r.setClock(split);
            r.setTime(t);
            records.add(r);

            i++;
    	}
    }
}

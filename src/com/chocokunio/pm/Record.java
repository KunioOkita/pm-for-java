package com.chocokunio.pm;

/**
 * 計測結果格納クラス
 * 
 * @author kunio
 *
 */
public class Record {

    /** ID */
    private String id;
    
    /** 計測場所 */
    private String place;

	/** 時刻 */
    private long clock;

    /** 経過時間 */
    private long time;
    
    /**
     * コンストラクタ
     */
    public Record() {
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            セットする id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
	 * @return place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place セットする place
	 */
	public void setPlace(String place) {
		this.place = place;
	}

    /**
     * @return clock
     */
    public long getClock() {
        return clock;
    }

    /**
     * @param clock
     *            セットする clock
     */
    public void setClock(long clock) {
        this.clock = clock;
    }

    /**
     * @return time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time
     *            セットする time
     */
    public void setTime(long time) {
        this.time = time;
    }
}

package com.chocokunio.pm.sample;

import java.util.Random;

import com.chocokunio.pm.PerformanceMeasurer;

/**
 * 処理時間計測ライブラリサンプルクラス
 * @author kunio
 *
 */
public class Sample {

	public static void main(String[] args) {
		System.out.println("Start Sample app.");
		
		PerformanceMeasurer pm = new PerformanceMeasurer("sample app");
		pm.start();
		
        targetProcess(1000);
		pm.split("place 1");
		pm.stop();
		pm.showRecord();

		PerformanceMeasurer pm2 = new PerformanceMeasurer("pm sample app2",
				PerformanceMeasurer.UNIT_NANOSEC);
		pm2.start();
	
        targetProcess(1000);
		pm.split("place 1");
		
        pm2.stop();
        pm2.showRecord();
		
		System.out.println("End Sample app.");
	}

    private static void targetProcess(int loop) {
        for (int i = 0; i < loop; i++) {
            int total = 0;
            Random rd = new Random();
            total = rd.nextInt();
        }
    }
}

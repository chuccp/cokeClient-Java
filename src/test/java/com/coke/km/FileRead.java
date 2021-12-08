package com.coke.km;

public class FileRead {

	public static void main(String[] args) {

		try {
//			FileInputStream fis = new FileInputStream(new File("D:\\attach\\kankeId.txt"));
//
//			byte[] data = new byte[fis.available()];
//			int size = fis.available();
//			int start = 0;
//			while (true) {
//				int rsize = fis.read(data, start, size);
//				start = start + rsize;
//				size = size - rsize;
//				if (size == 0) {
//					break;
//				}
//			}
//
//			String value = new String(data);
//			System.out.println(":"+value);
			
			String yy = "[{\"typename\":\"film\",\"cp\":\"aqiyi\",\"ids\":\"film_1721055|1.99999;film_1718649|1.99998;film_1668799|1.99997;film_1720372|1.99996;film_1438723|1.99995;film_1785265|1.99994;film_1785289|1.99993;film_1551573|1.99989;film_1540097|1.99988;\",\"timestamp\":\"2021-12-01 16:48:39\"}]";

			
			System.out.println(yy);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

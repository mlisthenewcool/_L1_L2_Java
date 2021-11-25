package app;

public class MonTraitementLongParallele implements Runnable{

	public MonTraitementLongParallele() {
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		for (int i=0; i < Integer.MAX_VALUE; i++) {
			System.out.println(i);
		}
	}
}
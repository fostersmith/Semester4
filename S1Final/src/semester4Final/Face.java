package semester4Final;

import java.awt.Color;
import java.awt.Toolkit;

public abstract class Face {
	protected Color faceC, eyeC, mouthC;
	
	protected int faceSize, eyeSize, mouthStartArc, mouthEndArc;
	
	abstract String makeNoise();
	public Color getFaceC() {
		return faceC;
	}
	public void setFaceC(Color faceC) {
		this.faceC = faceC;
	}

	public Color getEyeC() {
		return eyeC;
	}
	public void setEyeC(Color eyeC) {
		this.eyeC = eyeC;
	}

	public Color getMouthC() {
		return mouthC;
	}
	public void setMouthC(Color mouthC) {
		this.mouthC = mouthC;
	}

	public int getFaceSize() {
		return faceSize;
	}
	public void setFaceSize(int faceSize) {
		this.faceSize = faceSize;
	}

	public int getEyeSize() {
		return eyeSize;
	}
	public void setEyeSize(int eyeSize) {
		this.eyeSize = eyeSize;
	}

	public int getMouthStartArc() {
		return mouthStartArc;
	}
	public void setMouthStartArc(int mouthStartArc) {
		this.mouthStartArc = mouthStartArc;
	}

	public int getMouthEndArc() {
		return mouthEndArc;
	}
	public void setMouthEndArc(int mouthEndArc) {
		this.mouthEndArc = mouthEndArc;
	}
	
	/** starts a new thread which beeps {num} times at 0.5s intervals
	 * @param num - this number of times to beep
	 */
	public void beeps(int num) {
		Thread beepThread = new Thread() {
			@Override
			public void run() {
				for(int i = 0; i < num; ++i) {
					Toolkit.getDefaultToolkit().beep();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		beepThread.start();
	}

}

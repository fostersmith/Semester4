package semester4Final;

import java.awt.Color;
import java.awt.Toolkit;

public class SadFace extends Face {

	public SadFace() {
		setFaceC(Color.RED);
		setEyeC(Color.BLACK);
		setMouthC(Color.WHITE);
		setFaceSize(200);
		setEyeSize(20);
		setMouthStartArc(0);
		setMouthEndArc(180);
	}
	
	@Override
	String makeNoise() {
		Toolkit.getDefaultToolkit().beep();
		return "I AM SAD";
	}

}

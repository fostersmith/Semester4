package semester4Final;

import java.awt.Color;
import java.awt.Toolkit;

public class HappyFace extends Face {

	public HappyFace() {
		setFaceC(Color.YELLOW);
		setEyeC(Color.BLUE);
		setMouthC(Color.PINK);
		setFaceSize(200);
		setEyeSize(30);
		setMouthStartArc(0);
		setMouthEndArc(-180);
	}
	
	@Override
	String makeNoise() {
		beeps(10);
		return "I AM HAPPY";
	}

}

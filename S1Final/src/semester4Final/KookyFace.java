package semester4Final;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

public class KookyFace extends Face {

	public KookyFace() throws InvalidInputException {
		int faceSize = 0, eyeSize = 0;
		boolean gotValidInput = false;
		while(!gotValidInput) {
			try {
				faceSize = Integer.parseInt(JOptionPane.showInputDialog("Enter face size").trim());
				if(faceSize < 200 || faceSize > 500)
					throw new InvalidFaceSizeException();
				gotValidInput = true;
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Must enter an integer");
			}
		}
		
		gotValidInput = false;
		while(!gotValidInput) {
			try {
				eyeSize = Integer.parseInt(JOptionPane.showInputDialog("Enter eye size").trim());
				if(eyeSize < 20 || eyeSize > 100)
					throw new InvalidEyeSizeException();
				gotValidInput = true;
			} catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Must enter an integer");
			}
		}
		
		setFaceSize(faceSize);
		setEyeSize(eyeSize);

		setFaceC(Color.BLUE);
		setEyeC(Color.GREEN);
		setMouthC(Color.CYAN);
		setFaceSize(faceSize);
		setEyeSize(eyeSize);
		setMouthStartArc(10);
		setMouthEndArc(-150);
		

	}
	
	@Override
	String makeNoise() {
		beeps(3);
		return "I AM KOOKY";
	}

}

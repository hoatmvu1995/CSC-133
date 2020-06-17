package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Dialog;

public class HelpCmd extends Command{

	public HelpCmd() {
		super("Help");
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		Dialog.show("Help", "Press: \na = Accelerate\nb = Brake\nl = Turn Left\nr = Turn Right\nf = Food\ng = Collide with Spider"
				+ "\nt = Clock Ticking", "OK", "Cancel");
	}
}

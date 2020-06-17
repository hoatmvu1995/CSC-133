package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class InfoCmd extends Command{
	public InfoCmd() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Dialog.show("About", "Name: Hoat Vu\nCourse: CSC133\nAssignment: 2\n", "OK", "");
	}
}

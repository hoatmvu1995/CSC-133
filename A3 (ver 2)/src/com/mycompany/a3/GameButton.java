package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

public class GameButton extends Button{
	public GameButton(String cmd) {
	    super(cmd);
		    Style allStyles = getAllStyles();
		    allStyles.setBgTransparency(255);
		    allStyles.setBgColor(ColorUtil.rgb(0, 0, 255));
		    allStyles.setFgColor(ColorUtil.rgb(255,255,255));
		    allStyles.setPaddingTop(5);
		    allStyles.setPaddingBottom(5);
		    allStyles.setMargin(3, 3, 3, 3);
		    allStyles.setBorder(Border.createLineBorder(3, ColorUtil.rgb(211, 211, 211)));
		}
}

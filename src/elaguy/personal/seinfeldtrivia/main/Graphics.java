package elaguy.personal.seinfeldtrivia.main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Graphics {
	
	private SeinfeldTrivia seinfeldTrivia;
	
	public Graphics(SeinfeldTrivia seinfeldTrivia) {
		this.seinfeldTrivia = seinfeldTrivia;
	}
	
	public Image getResizedImage(Image img, int width, int height) {
		BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImg.createGraphics();
		
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(img, 0, 0, width, height, null);
		graphics.dispose();
		
		return resizedImg;
	}
}

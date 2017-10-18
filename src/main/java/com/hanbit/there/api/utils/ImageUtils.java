package com.hanbit.there.api.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage; // awt - java에서 이미지나 윈도우 다루는 패키지
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtils {
	
	public static void resize(InputStream inputStream, int width, int height, String targetFilePath) throws IOException{ // thumbnailator 라이브러리 사용
		Thumbnails.of(inputStream).size(width, height).toFile(targetFilePath); // width - 그림이 찌그러지지 않도록 - size 보다는
	}
	
	public static void resize(InputStream inputStream, int width, String targetFilePath) throws IOException{
		Thumbnails.of(inputStream).width(width).toFile(targetFilePath);
	}
	
	/*
	public static void main(String[] args) { // java 소스로 짠다면 - 실행 ctrl + F11
		try {
			BufferedImage image = ImageIO.read(new File("/hanbit2/Germanium.jpg"));
			int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();  // ARGB를 32bit로 표현 - 보통 쓰지만 아닐 수도 있으니
			
			BufferedImage resized = new BufferedImage(100, 100, type);
			Graphics2D g = resized.createGraphics();
			g.drawImage(image, 0, 0, 100, 100, null); // 작게 다시 그리기
			g.dispose(); // 이미지 닫기
			g.setComposite(AlphaComposite.Src); // 단순 줄이면 별로임 - 이미지 자연스럽게 만들자.
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			ImageIO.write(resized, "jpg", new File("/hanbit2/resized.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}

// 유틸 - 기능에 상관없이 여기저기서 사용할 수 있응 프로그램
// 다양한 이미지 크기가 필요할 때 - 포토샵으로 만들지 말고 서버에서 만들어서 보내준다.
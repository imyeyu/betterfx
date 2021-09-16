package net.imyeyu.bfxtest.view.pages;

import javafx.scene.control.TextArea;
import net.imyeyu.betterfx.BetterFX;
import net.imyeyu.betterfx.service.RunAsync;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * CSS 样式
 *
 * 夜雨 创建于 2021-05-16 23:37
 */
public class PageCSS extends TextArea implements BetterFX {

	public PageCSS() {
		new RunAsync<String>() {

			@Override
			public String call() throws Exception {
				String result = "";
				InputStream is = getClass().getResourceAsStream(CSS);
				if (is != null) {
					final StringBuilder sb = new StringBuilder();
					InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
					BufferedReader br = new BufferedReader(isr);
					String input;
					while ((input = br.readLine()) != null) {
						sb.append(input).append("\r\n");
					}
					br.close();
					isr.close();
					is.close();
					if (0 < sb.length()) {
						result = sb.substring(0, sb.length() - 1);
					}
				}
				return result;
			}

			@Override
			public void onFinish(String s) {
				setText(s);
			}

			@Override
			public void onException(Throwable e) {
				setText("无法读取 CSS 文件");
				appendText(e.toString());
			}
		}.start();
		setEditable(false);
	}
}
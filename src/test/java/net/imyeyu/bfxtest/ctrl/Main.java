package net.imyeyu.bfxtest.ctrl;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.imyeyu.betterfx.component.dialog.Alert;
import net.imyeyu.bfxtest.bean.Page;
import net.imyeyu.bfxtest.view.ViewMain;
import net.imyeyu.bfxtest.view.pages.PageAlert;
import net.imyeyu.bfxtest.view.pages.PageBetterFX;
import net.imyeyu.bfxtest.view.pages.PageBgFill;
import net.imyeyu.bfxtest.view.pages.PageBgImage;
import net.imyeyu.bfxtest.view.pages.PageButton;
import net.imyeyu.bfxtest.view.pages.PageByteSpeed;
import net.imyeyu.bfxtest.view.pages.PageCSS;
import net.imyeyu.bfxtest.view.pages.PageComboBox;
import net.imyeyu.bfxtest.view.pages.PageGroup;
import net.imyeyu.bfxtest.view.pages.PageTest;
import net.imyeyu.bfxtest.view.pages.PageTitlePane;
import net.imyeyu.bfxtest.view.pages.PageList;
import net.imyeyu.bfxtest.view.pages.PageNoSelectionModel;
import net.imyeyu.bfxtest.view.pages.PagePopup;
import net.imyeyu.bfxtest.view.pages.PageProgressBar;
import net.imyeyu.bfxtest.view.pages.PageRunAsync;
import net.imyeyu.bfxtest.view.pages.PageRunLater;
import net.imyeyu.bfxtest.view.pages.PageTable;
import net.imyeyu.bfxtest.view.pages.PageTextInput;
import net.imyeyu.bfxtest.view.pages.PageXAnchorPane;
import net.imyeyu.bfxtest.view.pages.PageXBorder;

import java.awt.SplashScreen;

/**
 * 主界面控制器
 *
 * 夜雨 创建于 2021-04-13 09:56
 */
public class Main extends ViewMain {

	@Override
	public void start(Stage stage) {
		super.start(stage);

		TreeItem<Page> betterfx = new TreeItem<>();
		betterfx.setValue(new Page("BetterFX", new PageBetterFX()));

		TreeItem<Page> betterfxcss = new TreeItem<>();
		betterfxcss.setValue(new Page("BetterFX CSS 样式", new PageCSS()));

		TreeItem<Page> extend = new TreeItem<>();
		extend.setValue(new Page("扩展"));
		extend.setExpanded(true);
		{
			final Page[] pages = new Page[] {
				new Page("背景 - 填充", new PageBgFill()),
				new Page("背景 - 图像", new PageBgImage()),
				new Page("空的选择器", new PageNoSelectionModel()),
				new Page("XAnchorPane", new PageXAnchorPane()),
				new Page("XBorder", new PageXBorder()),
				new Page("XTreeView", (page) -> {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("导航栏就是 XTreeView 树形结构，相对于原来的 TreeView，允许有多个根节点。\n\n使用方法和 TreeView 一样");
					alert.show();
					return false;
				}),
				new Page("自由测试", new PageTest()),
			};
			for (int i = 0; i < pages.length; i++) {
				extend.getChildren().add(new TreeItem<>(pages[i]));
			}
		}

		TreeItem<Page> component = new TreeItem<>();
		component.setValue(new Page("组件"));
		component.setExpanded(true);
		{
			final Page[] pages = new Page[] {
				new Page("标题布局", new PageTitlePane()),
				new Page("按钮", new PageButton()),
				new Page("组件组", new PageGroup()),
				new Page("输入", new PageTextInput()),
				new Page("下拉选择", new PageComboBox()),
				new Page("列表", new PageList()),
				new Page("表格", new PageTable()),
				new Page("进度条", new PageProgressBar()),
				new Page("弹窗会话", new PageAlert()),
			};
			for (int i = 0; i < pages.length; i++) {
				component.getChildren().add(new TreeItem<>(pages[i]));
			}
		}

		TreeItem<Page> service = new TreeItem<>();
		service.setValue(new Page("服务"));
		service.setExpanded(true);
		{
			final Page[] pages = new Page[] {
				new Page("字节流速度", new PageByteSpeed()),
				new Page("弹出提示", new PagePopup()),
				new Page("快速构造异步执行", new PageRunAsync()),
				new Page("快速构造稍后执行", new PageRunLater()),
			};
			for (int i = 0; i < pages.length; i++) {
				service.getChildren().add(new TreeItem<>(pages[i]));
			}
		}

		nav.setRoots(betterfx, betterfxcss, extend, component, service);
		root.getItems().add(nav.getRoot().getChildren().get(0).getValue().getNode());
		SplitPane.setResizableWithParent(nav, false);
		nav.getSelectionModel().selectedItemProperty().addListener((obs, o, selectedItem) -> {
			if (selectedItem != null) {
				final Page page = selectedItem.getValue();
				if (page.getOnSelected() != null) {
					if (!page.getOnSelected().handler(page)) { // 切换事件
						return;
					}
				}
				if (selectedItem.getValue().getNode() != null) {
					root.getItems().remove(1, 2);
					root.getItems().add(selectedItem.getValue().getNode());
					root.setDividerPositions(.25, .75);
				}
			}
		});
		nav.getSelectionModel().select(betterfx);

		root.setDividerPositions(.16, .84);
		if (SplashScreen.getSplashScreen() != null) {
			SplashScreen.getSplashScreen().close();
		}
		stage.show();
	}
}
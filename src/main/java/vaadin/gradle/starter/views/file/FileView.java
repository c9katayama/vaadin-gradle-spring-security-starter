package vaadin.gradle.starter.views.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import jakarta.annotation.security.PermitAll;
import vaadin.gradle.starter.views.MainLayout;

@PageTitle("File")
@Route(value = "file", layout = MainLayout.class)
@PermitAll
public class FileView extends VerticalLayout {

	File dir = new File("upload");
	private Upload upload;
	private Grid<File> grid;

	public FileView() {
		setMargin(true);
		dir.mkdirs();
		Div label = new Div("PNG file upload");
		// Upload部分
		MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
		upload = new Upload(buffer);
		// PNGファイルのみupload可能
		upload.setAcceptedFileTypes("application/png", ".png");
		upload.addSucceededListener(event -> {
			String fileName = event.getFileName();
			InputStream inputStream = buffer.getInputStream(fileName);
			saveFile(fileName, inputStream);
		});
		// ファイル一覧
		grid = new Grid<>(File.class, false);
		grid.setWidth("80%");
		grid.addColumn("name").setAutoWidth(true);
		// PNGファイルの表示
		grid.addComponentColumn((imageFile) -> {
			StreamResource streamResource = new StreamResource(imageFile.getName(), () -> createInputStream(imageFile));
			Anchor link = new Anchor(streamResource, "show");
			link.setTarget("_blank");
			return link;
		}).setAutoWidth(true).setFlexGrow(0);
		// PNGファイルのダウンロード
		grid.addComponentColumn((imageFile) -> {
			StreamResource streamResource = new StreamResource(imageFile.getName(), () -> createInputStream(imageFile));
			Anchor link = new Anchor(streamResource, "download");
			link.getElement().setAttribute("download", true);
			return link;
		}).setAutoWidth(true).setFlexGrow(0);
		initFileList();

		add(label, upload, grid);
	}

	private void initFileList() {
		File[] listFiles = dir.listFiles((f) -> f.getName().endsWith(".png"));
		if (listFiles != null) {
			grid.setItems(Arrays.asList(listFiles));
		}
	}

	private void saveFile(String fileName, InputStream inputStream) {
		try {
			File file = new File(dir, fileName);
			Files.write(file.toPath(), IOUtils.toByteArray(inputStream));
			System.out.println("save: " + file.getAbsolutePath());
			initFileList();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream createInputStream(File file) {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}

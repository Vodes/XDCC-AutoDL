package pw.vodes.xdccdl.download;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.util.FileUtil;

public class DownloadAbleManager {
	
	private CopyOnWriteArrayList<DownloadAble> downloadables = new CopyOnWriteArrayList<>();
	private File downloadableFile = new File(XDCCDL.getInstance().directory, "downloadables.txt");
	
	public void init() {
		if(!downloadableFile.exists()) {
			try {
				downloadableFile.createNewFile();
			} catch (IOException e) {}
			return;
		}
		List<String> fileReadout = FileUtil.readFileToLines(downloadableFile);
		if(fileReadout.isEmpty()) {
			addSample();
			return;
		}
		for(String s : fileReadout) {
			if(!s.contains(";;")) {
				continue;
			}
			String[] parts = s.split(";;");
			downloadables.add(new DownloadAble(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3])));
		}
	}
	
	public void addSample() {
		downloadables.add(new DownloadAble("SampleDLAble", "SampleBot", "SampleAnime, (1080p)", true));
		save();
	}
	
	public void save() {
		List<String> lines = new ArrayList<>();
		for(DownloadAble dla : getDownloadables()) {
			lines.add(dla.getName() + ";;" + dla.getBot() + ";;" + dla.getContainments() + ";;" + dla.isEnabled());
		}
		if(!lines.isEmpty()) {
			FileUtil.writeLinesToFile(downloadableFile, lines, false);
		}
	}
	
	public CopyOnWriteArrayList<DownloadAble> getDownloadables() {
		return downloadables;
	}

}

package com.sqli.liferay.imex.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.sqli.liferay.imex.core.service.Exporter;
import com.sqli.liferay.imex.core.service.Importer;
import com.sqli.liferay.imex.util.xml.ZipUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class ImExAutoDeployListener implements AutoDeployListener {
	
	private final static String[] DATADIR_EXTENSIONS = {
		"", 
		".imex"
	};
	private final static String[] ZIPFILE_EXTENSIONS = {
		".zip", 
		".imex.zip",
	};
	
	private ThreadLocal<File> temp = new ThreadLocal<File>();
	
	public void deploy(AutoDeploymentContext autoDeploymentContext) throws AutoDeployException {
		
		this.deploy(autoDeploymentContext.getFile(), autoDeploymentContext.getContext());
		
	}
	
	public void deploy(File file, String context) throws AutoDeployException {
		
		Properties config = new Properties();
		try {
			FileReader fileReader = new FileReader(file);
			config.load(fileReader);
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new AutoDeployException(e);
		} catch (IOException e) {
			throw new AutoDeployException(e);
		}
		
		String fileName = file.getName().toLowerCase();
		if (fileName.endsWith(".import.properties")) {
			
			LOG.info("Import started for " + file);
			
			createTempFolder();
		
			try {
				File data = getImportDataDir(file);
				processImport(config, data);
			} catch (Exception e) {
				throw new AutoDeployException(e);
			} finally {
				deleteTempFolder();
			}
			
			LOG.info("Import finished for " + file);
			
		} else if(fileName.endsWith(".export.properties")){
			
			LOG.info("Export started for " + file);
			
			File data = getExportDataDir(file);
			
			processExport(data, file);
			
			ZipUtils zipUtils = new ZipUtils(LOG);
			try {
				zipUtils.zipFiles(new File(data.getParent(), data.getName() + ".imex.zip"), data);
			} catch (IOException e) {
				throw new AutoDeployException();
			}
			
			LOG.info("Export finished for " + file);
		}
		
		try {
			FileUtil.copyFile(file, new File(file.getParentFile(),file.getName() + ".done"));
		} catch (IOException e) {
			LOG.error(e,e);
			throw new AutoDeployException();
		}
	}


	private void createTempFolder() {
		File tempFolder = temp.get();
		if (tempFolder != null) {
			FileUtil.deltree(tempFolder);
		} else {
			String basepath = System.getProperty("java.io.tmpdir");
			tempFolder = new File(basepath + "/" + UUID.randomUUID().toString());
			
			LOG.info("java.io.temp.dir=" + basepath);
			LOG.info("ImEx temp folder: " + tempFolder);
		} 
		tempFolder.mkdirs();
		tempFolder.deleteOnExit();
	}
	
	private void deleteTempFolder() {
		File tempFolder = temp.get();
		if (tempFolder != null && tempFolder.exists()) {
			FileUtil.deltree(tempFolder);
		}
		
	}

	private void processImport(Properties config, File data) throws AutoDeployException {
		try {
			new Importer().doImport(data, config);
			//ImExLocalServiceUtil.doImport(data);
		} catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}
	
	private void processExport(File data, File config) throws AutoDeployException {

		if (data.exists()) {
			FileUtil.deltree(data);
		}
		boolean success = data.mkdirs();
		if (!success) {
			throw new AutoDeployException("Failed to create directory " + data);
		}
		try {
			Properties props = new Properties();
			props.load(new FileReader(config));
			//ImExLocalServiceUtil.doExport(data, config);
			new Exporter().doExport(data, props);
		} catch (Exception e) {
			throw new AutoDeployException(e);
		}
		
	}

	private File getImportDataDir(File configFile) throws Exception {
		File parent = configFile.getParentFile();
		String basename = configFile.getName();
		basename = basename.substring(0, basename.indexOf('.'));
		
		File dataDir = null;
		for (String ext : DATADIR_EXTENSIONS) {
			String name = basename + ext;
			dataDir = new File(parent, name);
			if (dataDir.exists() && dataDir.isDirectory()) {
				break;
			} else {
				dataDir = null;
			}
		}
		
		if (dataDir == null) {
			File zipFile = null;
			for (String ext : ZIPFILE_EXTENSIONS) {
				String name = basename + ext;
				zipFile = new File(parent, name);
				if (zipFile.exists() && zipFile.isFile()) {
					break;
				} else {
					zipFile = null;
				}
			}
			
			if (zipFile == null) {
				throw new AutoDeployException("No data found to import for config file " + configFile);
			} else {
				dataDir = new File(this.temp.get(), zipFile.getName());
				ZipUtils zipUtils = new ZipUtils(LOG);
				zipUtils.unzipArchive(zipFile, dataDir);
			}
		}
		
		return dataDir;
	}
	
	private File getExportDataDir(File configFile) {
		File parent = configFile.getParentFile();
		String basename = configFile.getName();
		basename = basename.substring(0, basename.indexOf('.'));
		return new File(parent, basename);
	}
	
	private final static Log LOG = LogFactoryUtil.getLog(ImExAutoDeployListener.class);

}

package org.mineap.nndd.server.model;

import java.io.File;

public class NNDDFile
{

	private long id = -1;
	private String dirpath = null;
	private File file = null;

	/**
	 * @param id
	 * @param path
	 */
	public NNDDFile(long id, String path)
	{
		
		this.id = id;
		this.dirpath = path;
		this.file = new File(path);
		
	}
	
	/**
	 * @param id
	 * @param dir
	 */
	public NNDDFile(long id, File dir)
	{
		
		this.id = id;
		this.file = dir;
		this.dirpath = file.getAbsolutePath();
		
	}
	
	/**
	 * @return
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getDirpath()
	{
		return dirpath;
	}

	/**
	 * @param dirpath
	 */
	public void setDirpath(String dirpath)
	{
		this.dirpath = dirpath;
		this.file = new File(dirpath);
	}

	/**
	 * @return
	 */
	public File getFile()
	{
		return file;
	}

	/**
	 * @param file
	 */
	public void setFile(File file)
	{
		this.file = file;
		this.dirpath = file.getAbsolutePath();
	}

}

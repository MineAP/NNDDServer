/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.videostream;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.mineap.nndd.server.model.NNDDVideo;

/**
 *
 * @author shiraminekeisuke
 */
public class NNDDVideoUtil
{

	/**
	 * 
	 * @param video
	 * @return
	 * @throws URISyntaxException
	 */
	public static File getFile(NNDDVideo video) throws URISyntaxException
	{

		String path = video.getUri();

		URI uri = new URI(video.getUri());

		File file = new File(uri);

		return file;

	}
}

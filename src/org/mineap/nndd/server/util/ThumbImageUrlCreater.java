/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.util;

/**
 *
 * @author shiraminekeisuke
 */
public class ThumbImageUrlCreater
{

	public static String create(String videoId)
	{
		double num = Math.random();
		num = (num * 10) % 3 + 1;

		String thumbVideoId = videoId;
		if (videoId.length() > 3)
		{
			thumbVideoId = thumbVideoId.substring(2);
		}

		String thumbUrl = "http://tn-skr" + (int) num + ".smilevideo.jp/smile?i=" + thumbVideoId;

		return thumbUrl;
	}
}

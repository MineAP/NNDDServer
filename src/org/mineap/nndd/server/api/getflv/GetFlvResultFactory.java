/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.api.getflv;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mineap.nndd.server.cache.NnddVideoCacheManager;
import org.mineap.nndd.server.dao.NNDDVideoDao;
import org.mineap.nndd.server.model.NNDDVideo;

/**
 * 
 * @author shiraminekeisuke
 */
public class GetFlvResultFactory
{

	private NnddVideoCacheManager cacheManager = NnddVideoCacheManager
			.getInstance();

	public static String SERVER_IP_ADDRESS = Messages
			.getString("GetFlvResultFactory.SERVER_IP_ADDRESS");

	public static String VIDEO_URL_PREFIX = "http://" + SERVER_IP_ADDRESS
			+ ":8080/nnddserver/videostream/";

	public static String MSG_URL_PREFIX = "http://" + SERVER_IP_ADDRESS
			+ ":8080/nnddserver/api/getmsg";

	public GetFlvResultFactory()
	{
	}

	/**
	 * 
	 * @param videoId
	 * @return
	 */
	public String createResult(String videoId)
	{

		NNDDVideo video = cacheManager.getNNDDVideo(videoId);

		if (video == null)
		{
			NNDDVideoDao dao = new NNDDVideoDao();
			try
			{
				video = dao.getByVideoKey(videoId);
			} catch (SQLException ex)
			{
				Logger.getLogger(GetFlvResultFactory.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		StringBuilder builder = new StringBuilder();

		if (video != null)
		{

			// �X���b�hID
			builder.append("thread_id=");
			builder.append(video.getKey());

			// ����̒���
			builder.append("&l=");
			builder.append(video.getTime());

			// �����URL
			builder.append("&url=");
			builder.append(GetFlvResultFactory.VIDEO_URL_PREFIX).append(
					video.getKey());

			// smile-video�̓���y�[�W�ւ̃����N
			builder.append("&link=null");

			// ���b�Z�[�W�T�[�o�[
			builder.append("&ms=");
			builder.append(GetFlvResultFactory.MSG_URL_PREFIX);

			// ���[�U�[ID
			builder.append("&user_id=you");

			// �v���~�A��������ǂ���
			builder.append("&is_premium=0");

			// �j�b�N�l�[��
			builder.append("&nickname=you");

			// ���e����
			builder.append("&time=0");

			// �����������ǂ���
			builder.append("&done=true");

		} else
		{
			builder.append("error=invalid_v1");
			builder.append("&done=true");
		}

		return builder.toString();

	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mineap.nndd.server.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mineap.nndd.server.model.NNDDVideo;

/**
 * {@link NNDDVideo} �̃L���b�V�����Ǘ�����N���X�B���̃N���X�̓V���O���g���p�^�[���ł��B
 * 
 * @author shiraminekeisuke
 */
public class NnddVideoCacheManager
{

	/**
	 * �B��� {@link NnddVideoCacheManager} �B
	 */
	private static NnddVideoCacheManager manager = new NnddVideoCacheManager();
	
	/**
	 * �r�f�IID��\�����镶������L�[�� {@link NNDDVideo} �̃L���b�V���I�u�W�F�N�g���i�[����X���b�h�Z�[�t�� {@link HashMap} �B
	 */
	private ConcurrentHashMap<String, NNDDVideoCache> cacheMap = new ConcurrentHashMap<String, NNDDVideoCache>(
			100);

	/**
	 * �R���X�g���N�^�B�V���O���g���p�^�[���Ȃ̂ŕs���B
	 */
	private NnddVideoCacheManager()
	{
		
		// �L���b�V���Ǘ��X���b�h���N��
		CacheControlleThread thread = new CacheControlleThread();
		thread.start();
		
	}

	/**
	 * �B��� {@link NnddVideoCacheManager} ��Ԃ��܂��B
	 * 
	 * @return
	 */
	public static NnddVideoCacheManager getInstance()
	{
		return manager;
	}

	/**
	 * �r�f�IID�ɑΉ����� {@link NNDDVideo} �I�u�W�F�N�g���L���b�V���ɑ��݂��邩�ǂ����`�F�b�N���A ���݂���΂���
	 * {@link NNDDVideo} �I�u�W�F�N�g��Ԃ��܂��B
	 * 
	 * @param videoId
	 * @return
	 */
	public NNDDVideo getNNDDVideo(String videoId)
	{

		NNDDVideoCache cache = cacheMap.get(videoId);

		if (cache == null)
		{
			return null;
		}
		return cache.getNNDDVideo();
	}

	/**
	 * �L���b�V���Ɏw�肳�ꂽ {@link NNDDVideo} �I�u�W�F�N�g��ǉ����܂��B
	 * 
	 * @param video
	 */
	public void setNNDDVideo(NNDDVideo video)
	{

		cacheMap.put(video.getKey(), new NNDDVideoCache(video));

	}

	/**
	 * �L���b�V���̎������Ǘ����邽�߂̓��� {@link Thread} �N���X�B
	 * 
	 * @author shiraminekeisuke
	 * 
	 */
	private class CacheControlleThread extends Thread
	{

		@Override
		public void run()
		{

			while (true)
			{
				try
				{
					Thread.sleep(10000);
				} catch (InterruptedException ex)
				{
					Logger.getLogger(NnddVideoCacheManager.class.getName())
							.log(Level.SEVERE, null, ex);
				}

				long diff = 100 * 60 * 10;

				Date date = new Date();

				if (cacheMap.size() <= 100)
				{
					continue;
				}

				for (Iterator<Map.Entry<String, NNDDVideoCache>> it = cacheMap
						.entrySet().iterator(); it.hasNext();)
				{
					Map.Entry<String, NNDDVideoCache> entry = it.next();

					if (date.getTime()
							- entry.getValue().getCacheDate().getTime() > diff)
					{
						it.remove();
					}

					if (cacheMap.size() <= 100)
					{
						break;
					}
				}
			}

		}
	}

	/**
	 * {@link NNDDVideo} �ƃL���b�V���ւ̓o�^���t���Ǘ����邽�߂̃N���X�B
	 * 
	 * @author shiraminekeisuke
	 * 
	 */
	private class NNDDVideoCache
	{

		private Date cacheDate = new Date();
		private NNDDVideo video = null;

		public NNDDVideoCache(NNDDVideo video)
		{
			this.video = video;
		}

		/**
		 * 
		 * @return
		 */
		public NNDDVideo getNNDDVideo()
		{
			return this.video;
		}

		/**
		 * 
		 * @return
		 */
		public Date getCacheDate()
		{
			return new Date(cacheDate.getTime());
		}
	}
}

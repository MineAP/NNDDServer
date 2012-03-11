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
 * {@link NNDDVideo} のキャッシュを管理するクラス。このクラスはシングルトンパターンです。
 * 
 * @author shiraminekeisuke
 */
public class NnddVideoCacheManager
{

	/**
	 * 唯一の {@link NnddVideoCacheManager} 。
	 */
	private static NnddVideoCacheManager manager = new NnddVideoCacheManager();
	
	/**
	 * ビデオIDを表現する文字列をキーに {@link NNDDVideo} のキャッシュオブジェクトを格納するスレッドセーフな {@link HashMap} 。
	 */
	private ConcurrentHashMap<String, NNDDVideoCache> cacheMap = new ConcurrentHashMap<String, NNDDVideoCache>(
			100);

	/**
	 * コンストラクタ。シングルトンパターンなので不可視。
	 */
	private NnddVideoCacheManager()
	{
		
		// キャッシュ管理スレッドを起動
		CacheControlleThread thread = new CacheControlleThread();
		thread.start();
		
	}

	/**
	 * 唯一の {@link NnddVideoCacheManager} を返します。
	 * 
	 * @return
	 */
	public static NnddVideoCacheManager getInstance()
	{
		return manager;
	}

	/**
	 * ビデオIDに対応する {@link NNDDVideo} オブジェクトがキャッシュに存在するかどうかチェックし、 存在すればその
	 * {@link NNDDVideo} オブジェクトを返します。
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
	 * キャッシュに指定された {@link NNDDVideo} オブジェクトを追加します。
	 * 
	 * @param video
	 */
	public void setNNDDVideo(NNDDVideo video)
	{

		cacheMap.put(video.getKey(), new NNDDVideoCache(video));

	}

	/**
	 * キャッシュの寿命を管理するための内部 {@link Thread} クラス。
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
	 * {@link NNDDVideo} とキャッシュへの登録日付を管理するためのクラス。
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

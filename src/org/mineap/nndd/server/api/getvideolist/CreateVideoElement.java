package org.mineap.nndd.server.api.getvideolist;

import org.mineap.nndd.server.model.NNDDVideo;
import org.mineap.nndd.server.util.ThumbImageUrlCreater;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * @author shiraminekeisuke
 * 
 */
public class CreateVideoElement
{

	/**
	 * @param document
	 * @param video
	 * @param count
	 * @return
	 */
	public Element createElement(Document document, NNDDVideo video, int count)
	{

		Element item = document.createElement("item");

		// 番号(num)
		Element num = document.createElement("num");
		Text textNode = document.createTextNode(Integer.toString(count));
		num.appendChild(textNode);
		item.appendChild(num);

		// サムネイル画像URL(thumbImgUrl)
		Element thumbImgUrl = document.createElement("thumbImgUrl");

		String url = null;
		String videoId = video.getKey();
		if (videoId != null)
		{
			url = ThumbImageUrlCreater.create(videoId);
		}
		textNode = document.createTextNode(url);

		thumbImgUrl.appendChild(textNode);
		item.appendChild(thumbImgUrl);

		// 動画名(title)
		Element item_title = document.createElement("title");
		textNode = document.createTextNode(video.getVideoName());
		item_title.appendChild(textNode);
		item.appendChild(item_title);

		// 再生回数(playCount)
		Element playCount = document.createElement("playCount");
		textNode = document.createTextNode(Long.toString(video.getPlayCount()));
		playCount.appendChild(textNode);
		item.appendChild(playCount);

		// DL日
		Element dlDate = document.createElement("dlDate");
		textNode = document.createTextNode(video.getCreationDate().toString());
		dlDate.appendChild(textNode);
		item.appendChild(dlDate);
		
		// 最終再生日(lastPlayDate)
		Element lastPlayDate = document.createElement("lastPlayDate");
		textNode = document.createTextNode(video.getLastPlayDate().toString());
		lastPlayDate.appendChild(textNode);
		item.appendChild(lastPlayDate);
		
		// 動画ID
		Element guid = document.createElement("guid");
		textNode = document.createTextNode(video.getKey());
		guid.appendChild(textNode);
		item.appendChild(guid);

		return item;
	}
}

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

		// �ԍ�(num)
		Element num = document.createElement("num");
		Text textNode = document.createTextNode(Integer.toString(count));
		num.appendChild(textNode);
		item.appendChild(num);

		// �T���l�C���摜URL(thumbImgUrl)
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

		// ���於(title)
		Element item_title = document.createElement("title");
		textNode = document.createTextNode(video.getVideoName());
		item_title.appendChild(textNode);
		item.appendChild(item_title);

		// �Đ���(playCount)
		Element playCount = document.createElement("playCount");
		textNode = document.createTextNode(Long.toString(video.getPlayCount()));
		playCount.appendChild(textNode);
		item.appendChild(playCount);

		// DL��
		Element dlDate = document.createElement("dlDate");
		textNode = document.createTextNode(video.getCreationDate().toString());
		dlDate.appendChild(textNode);
		item.appendChild(dlDate);
		
		// �ŏI�Đ���(lastPlayDate)
		Element lastPlayDate = document.createElement("lastPlayDate");
		textNode = document.createTextNode(video.getLastPlayDate().toString());
		lastPlayDate.appendChild(textNode);
		item.appendChild(lastPlayDate);
		
		// ����ID
		Element guid = document.createElement("guid");
		textNode = document.createTextNode(video.getKey());
		guid.appendChild(textNode);
		item.appendChild(guid);

		return item;
	}
}

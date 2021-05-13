package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hlt
 * @since 2019-12-25
 */

//插入的聊天记录
public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

	public String user_id;

	public String senduser_id;

	public ChatMsg(String user_id, String senduser_id,  String name,String text, String date) {
		this.user_id = user_id;
		this.senduser_id = senduser_id;
		this.name = name;
		this.text = text;
		this.date = date;
	}

	public String text;

	public String name;

	public String date;

	public String img;

	public boolean mine;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}




	@Override
	public String toString() {
		return "ChatMsg{" +
				"user_id='" + user_id + '\'' +
				", senduser_id='" + senduser_id + '\'' +
				", text='" + text + '\'' +
				", date='" + date + '\'' +
				", mine=" + mine +
				'}';
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSenduser_id() {
		return senduser_id;
	}

	public void setSenduser_id(String senduser_id) {
		this.senduser_id = senduser_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public ChatMsg(String user_id, String senduser_id, String text, String date, boolean mine) {
		this.user_id = user_id;
		this.senduser_id = senduser_id;
		this.text = text;
		this.date = date;
		this.mine = mine;
	}

	public ChatMsg(String user_id, String senduser_id, String text, String date) {
		this.user_id = user_id;
		this.senduser_id = senduser_id;
		this.text = text;
		this.date = date;
	}



}

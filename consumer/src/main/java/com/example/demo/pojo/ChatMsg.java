package com.example.demo.pojo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hlt
 * @since 2019-12-25
 */

public class ChatMsg implements Serializable {

    private static final long serialVersionUID = 1L;

	public String user_id;

	public String senduser_id;

	public String text;

	public String date;

	public boolean mine;

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

package com.ssitcloud.common.entity;

import java.util.Objects;

public class TimeEntity {
	private String clazz;
	private String method;
	private long time;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public TimeEntity clone() {
		TimeEntity t = new TimeEntity();
		t.setClazz(clazz);
		t.setMethod(method);
		t.setTime(time);
		return t;
	}

	@Override
	public String toString() {
		return String.format("pt:(%s.%s=%d)", clazz, method, time);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.clazz);
		hash = 79 * hash + Objects.hashCode(this.method);
		hash = 79 * hash + Objects.hashCode(this.time);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TimeEntity other = (TimeEntity) obj;
		if (!Objects.equals(this.clazz, other.clazz)) {
			return false;
		}
		if (!Objects.equals(this.method, other.method)) {
			return false;
		}
		if (this.time != other.time) {
			return false;
		}
		return true;
	}

}

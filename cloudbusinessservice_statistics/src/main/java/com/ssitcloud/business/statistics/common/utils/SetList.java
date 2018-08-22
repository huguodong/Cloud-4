package com.ssitcloud.business.statistics.common.utils;

import java.util.LinkedList;

/**
 * 有序不重复Set
 * 
 * @author bob
 * 
 * @param <T>
 */
public class SetList<T> extends LinkedList<T> {
	private static final long serialVersionUID = -8875525579080906094L;

	@Override
	public boolean add(T object) {
		if (size() == 0) {
			return super.add(object);
		} else {
			int count = 0;
			for (T t : this) {
				if (t.equals(object)) {
					count++;
					break;
				}
			}
			if (count == 0) {
				return super.add(object);
			} else {
				return false;
			}
		}
	}

	public static <T> boolean isContained(SetList<T> set, T t) {
		for (T t1 : set) {
			if (t.equals(t1)) {
				return true;
			}
		}
		return false;
	}
}

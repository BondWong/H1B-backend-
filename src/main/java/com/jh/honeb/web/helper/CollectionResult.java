package com.jh.honeb.web.helper;

import java.util.List;

public final class CollectionResult<T> {
	private List<T> elements;
	private int pageNum;
	private int pageSize;
	private boolean hasNext;

	public CollectionResult(List<T> elements, int pageNum, int pageSize, boolean hasNext) {
		this.elements = elements;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.hasNext = hasNext;
	}

	public List<T> getElements() {
		return elements;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isHasNext() {
		return hasNext;
	}

}

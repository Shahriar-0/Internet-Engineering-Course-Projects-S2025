package application.pagination;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Page<T> {

	private final List<T> list;
	private final int pageNumber;
	private final int pageSize;
	private final int totalPageNumber;
	private final int totalDataSize;

	public Page(List<T> data, int pageNumber, int pageSize) {
		if (data == null)
			throw new IllegalArgumentException("Data cannot be null");
		if (pageNumber <= 0)
			throw new IllegalArgumentException("Page number must be greater than 0");
		if (pageSize <= 0)
			throw new IllegalArgumentException("Page size must be greater than 0");

		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalDataSize = data.size();
		this.totalPageNumber = (int) Math.ceil((double) totalDataSize / pageSize);

		int startIndex = (pageNumber - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, totalDataSize);
		this.list = (startIndex < totalDataSize) ? data.subList(startIndex, endIndex) : List.of();
	}
}

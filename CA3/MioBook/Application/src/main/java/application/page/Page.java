package application.page;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalDataSize = data.size();
		this.totalPageNumber = (int) Math.ceil((double) totalDataSize / pageSize);

		int startIndex = (pageNumber - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize - 1, totalDataSize - 1);
		this.list = (startIndex < totalDataSize) ? data.subList(startIndex, endIndex) : List.of();
	}
}

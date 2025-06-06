package webapi.views.page;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageView<T> (
    Integer pageNumber,
    Integer pageSize,
    Integer totalPageNumber,
    Long totalDataSize,
    List<T> list
) {
    public PageView(Page<T> page) {
        this(
            page.getNumber() + 1,
            page.getSize(),
            page.getTotalPages(),
            page.getTotalElements(),
            page.getContent()
        );
    }
}

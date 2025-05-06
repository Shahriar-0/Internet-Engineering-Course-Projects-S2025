package infra.mappers;

import domain.entities.book.Review;
import infra.daos.ReviewDao;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper implements IMapper<Review, ReviewDao> {

    private final CustomerMapper customerMapper;
    private final BookMapper bookMapper;

    public Review mapWithCustomer(ReviewDao dao) {
        Review review = toDomain(dao);
        review.setCustomer(customerMapper.toDomain(dao.getCustomer()));
        return review;
    }

    @Override
    public Review toDomain(ReviewDao dao) {
        return Review.builder()
            .id(dao.getId())
            .rating(dao.getRating())
            .comment(dao.getComment())
            .dateTime(dao.getDateTime())
            .book(bookMapper.toDomain(dao.getBook()))
            .customer(customerMapper.toDomain(dao.getCustomer()))
            .build();
    }

    @Override
    public ReviewDao toDao(Review entity) {
        ReviewDao dao = new ReviewDao();
        dao.setId(entity.getId());
        dao.setRating(entity.getRating());
        dao.setComment(entity.getComment());
        dao.setDateTime(entity.getDateTime());
        dao.setBook(bookMapper.toDao(entity.getBook()));
        dao.setCustomer(customerMapper.toDao(entity.getCustomer()));
        return dao;
    }

    @Override
    public void update(Review entity, ReviewDao dao) {
        dao.setId(entity.getId());
        dao.setRating(entity.getRating());
        dao.setComment(entity.getComment());
        dao.setDateTime(entity.getDateTime());
    }
}

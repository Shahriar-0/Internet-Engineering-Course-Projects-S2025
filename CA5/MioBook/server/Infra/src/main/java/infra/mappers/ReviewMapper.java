package infra.mappers;

import domain.entities.book.Review;
import infra.daos.ReviewDao;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements IMapper<Review, ReviewDao> {

    public Review mapWithCustomer(ReviewDao dao, CustomerMapper customerMapper) {
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
            .build();
    }

    @Override
    public ReviewDao toDao(Review entity) {
        ReviewDao dao = new ReviewDao();
        dao.setId(entity.getId());
        dao.setRating(entity.getRating());
        dao.setComment(entity.getComment());
        dao.setDateTime(entity.getDateTime());
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

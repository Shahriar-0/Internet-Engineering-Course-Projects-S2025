package application.services;

import application.dtos.*;
import application.repositories.IAuthorRepository;
import domain.entities.*;
import domain.valueobjects.*;

import java.time.LocalDate;

public class UtilService {

	public static User createUser(AddUserDto dto) {
		User.Role role = User.Role.valueOf(dto.role().toUpperCase());
		if (role == null)
			throw new IllegalArgumentException("Invalid role: " + dto.role());

		if (role == User.Role.CUSTOMER)
			return Customer
				.builder()
				.key(dto.username())
				.address(dto.address())
				.password(dto.password())
				.email(dto.email())
				.role(role)
				.credit(0)
				.build();
		else
			return Admin
				.builder()
				.key(dto.username())
				.address(dto.address())
				.password(dto.password())
				.email(dto.email())
				.role(role)
				.build();
	}

	public static Author createAuthor(AddAuthorDto dto) {
		return Author
			.builder()
			.key(dto.name())
			.penName(dto.penName())
			.nationality(dto.nationality())
			.born(LocalDate.parse(dto.born()))
			.died(dto.died() == null ? null : LocalDate.parse(dto.died()))
			.build();
	}

	public static Book createBook(AddBookDto dto, IAuthorRepository authorRepository) {
		return Book
			.builder()
			.key(dto.title())
			.author(authorRepository.get(dto.author()).getData())
			.publisher(dto.publisher())
			.year(dto.year())
			.price(dto.price())
			.synopsis(dto.synopsis())
			.content(new BookContent(dto.content()))
			.genres(dto.genres())
			.build();
	}

	public static Review createReview(AddReviewDto dto) {
		return new Review(dto.rating(), dto.comment());
	}
}

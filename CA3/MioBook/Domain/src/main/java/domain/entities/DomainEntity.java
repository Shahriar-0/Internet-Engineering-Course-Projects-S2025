package domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class DomainEntity<KT> {

	protected KT key;

	public DomainEntity(KT key) {
		this.key = key;
	}

	public final Boolean isKeyEqual(KT otherKey) {
		return key.equals(otherKey);
	}
}

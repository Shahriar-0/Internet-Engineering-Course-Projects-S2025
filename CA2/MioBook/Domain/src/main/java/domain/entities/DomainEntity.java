package domain.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class DomainEntity<KT> {

	private KT key;

	public DomainEntity(KT key) {
		this.key = key;
	}

	public final boolean isKeyEqual(KT otherKey) {
		return key.equals(otherKey);
	}
}

package domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class DomainEntity<KT> {

	protected KT key;

	public final Boolean isKeyEqual(KT otherKey) {
		return key.equals(otherKey);
	}
}

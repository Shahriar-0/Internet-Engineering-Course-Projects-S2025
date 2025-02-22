package domain.entities;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AlakiEntity extends DomainEntity<Integer> {
    private String name;
    public AlakiEntity(Integer key, String name) {
        super(key);
        this.name = name;
    }
}

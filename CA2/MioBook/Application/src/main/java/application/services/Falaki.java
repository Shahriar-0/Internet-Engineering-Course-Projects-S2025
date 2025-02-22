package application.services;

import domain.entities.AlakiEntity;
import org.springframework.stereotype.Service;

@Service
public class Falaki {
    private final AlakiService alakiService;
    public Falaki(AlakiService alakiService) {
        this.alakiService = alakiService;
    }

    public void salam() {
        AlakiEntity alakiEntity = alakiService.getAlakiEntity();
        System.out.println(alakiEntity.getKey());
        System.out.println(alakiEntity.getName());
    }
}

package infra.services;

import application.services.AlakiService;
import domain.entities.AlakiEntity;
import org.springframework.stereotype.Service;

@Service
public class AlakiServiceImpl implements AlakiService {
    public AlakiEntity getAlakiEntity() {
        return new AlakiEntity(12, "shahnam");
    }
}

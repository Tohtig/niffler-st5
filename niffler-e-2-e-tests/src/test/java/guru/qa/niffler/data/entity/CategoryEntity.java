package guru.qa.niffler.data.entity;

import guru.qa.niffler.model.CategoryJson;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class CategoryEntity implements Serializable {

    private UUID id;
    private String category;
    private String username;

    public static CategoryEntity fromJson(CategoryJson json) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(json.id());
        entity.setCategory(json.category());
        entity.setUsername(json.username());
        return entity;
    }
}

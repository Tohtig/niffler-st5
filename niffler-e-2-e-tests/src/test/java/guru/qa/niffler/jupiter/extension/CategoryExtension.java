package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.CategoryApi;
import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.data.repository.SpendRepositoryJdbc;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class CategoryExtension implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoryExtension.class);

    private final SpendRepository spendRepository = new SpendRepositoryJdbc();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateCategory.class
        ).ifPresent(
                cat -> {
                    CategoryEntity category = new CategoryEntity();
                    category.setCategory(cat.category());
                    category.setUsername(cat.username());

                    category = spendRepository.createCategory(category);

                    extensionContext.getStore(NAMESPACE).put(
                            extensionContext.getUniqueId(), CategoryJson.fromEntity(category));
                }
        );
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        CategoryJson categoryJson = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
        spendRepository.deleteCategory(CategoryEntity.fromJson(categoryJson));
    }
}
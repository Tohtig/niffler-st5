package guru.qa.niffler.jupiter.extension;


import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.model.UserJson.simpleUser;

// Любой тест проходит через него
public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

     private static final Map<User.Selector, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put(User.Selector.WITH_FRIENDS, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("barsik", "12345"))
        ));
        USERS.put(User.Selector.INVITATION_SEND, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("duck", "12345"))
        ));
        USERS.put(User.Selector.INVITATION_RECEIVED, new ConcurrentLinkedQueue<>(
                List.of(simpleUser("barsik", "12345"))
        ));
    }

@Override
public void beforeEach(ExtensionContext context) throws Exception {

    //получение тестового метода @Test
    Method testMethod = context.getRequiredTestMethod();

    //получение beforeEach методов
    List<Method> beforeEachMethods = Arrays.stream(
                    context.getRequiredTestClass().getDeclaredMethods())
            .filter(i -> i.isAnnotationPresent(BeforeEach.class)).toList();

    //Общий список методов, которые требуется обработать
    List<Method> methods = new ArrayList<>();
    methods.add(testMethod);
    methods.addAll(beforeEachMethods);

    //Общий список параметров, которые требуется обработать
    List<Parameter> parameters = methods.stream()
            .flatMap(m -> Arrays.stream(m.getParameters()))
            .filter(p -> p.isAnnotationPresent(User.class))
            .toList();

    //Мапа, где хранятся пользователи и их типы для сохранения в стор
    Map<User.Selector, UserJson> users = new HashMap<>();

    // Обработка полученных параметров
    for (Parameter parameter : parameters) {
        User.Selector selector = parameter.getAnnotation(User.class).selector();
        if (users.containsKey(selector)) continue;
        UserJson userForTest = null;
        Queue<UserJson> queue = USERS.get(selector);
        while (userForTest == null) {
            userForTest = queue.poll();
        }
        users.put(selector, userForTest);
    }

    Allure.getLifecycle().updateTestCase(testCase -> {
        testCase.setStart(new Date().getTime());
    });
    context.getStore(NAMESPACE).put(context.getUniqueId(), users);
}

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Map<User.Selector, UserJson> userFromTest = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);
        userFromTest.forEach((selector, userJson) -> USERS.get(selector).add(userJson));

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        boolean isUserJson = parameter.getType().isAssignableFrom(UserJson.class);
        boolean hasUserAnnotation = parameter.isAnnotationPresent(User.class);

        return isUserJson && hasUserAnnotation;
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User.Selector selector = parameterContext.findAnnotation(User.class).get().selector();
        return (UserJson) extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class).get(selector);
    }
}